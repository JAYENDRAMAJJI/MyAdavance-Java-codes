import java.sql.*;

public class TransactionExample {
    // CHANGE THESE to match your setup
    private static final String URL  = "jdbc:mysql://localhost:3306/tx_demo?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "your_password";

    public static void main(String[] args) {
        // Example: Transfer 200.00 from Alice (id=1) to Bob (id=2)
        transferMoney(1, 2, 200.00);
    }

    public static void transferMoney(int fromId, int toId, double amount) {
        Connection con = null;
        PreparedStatement withdrawStmt = null;
        PreparedStatement depositStmt = null;
        Savepoint afterWithdraw = null;
        try {
            // 1) Get connection
            con = DriverManager.getConnection(URL, USER, PASS);

            // 2) Optional: set isolation level (example: READ COMMITTED)
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            // 3) Turn off auto-commit -> start transaction
            con.setAutoCommit(false);

            // 4) Prepare statements
            String withdrawSql = "UPDATE account SET balance = balance - ? WHERE id = ? AND balance >= ?";
            String depositSql  = "UPDATE account SET balance = balance + ? WHERE id = ?";

            withdrawStmt = con.prepareStatement(withdrawSql);
            depositStmt  = con.prepareStatement(depositSql);

            // 5) Do withdraw
            withdrawStmt.setDouble(1, amount);
            withdrawStmt.setInt(2, fromId);
            withdrawStmt.setDouble(3, amount);
            int rows1 = withdrawStmt.executeUpdate();

            if (rows1 == 0) {
                // Not enough balance or no such account
                throw new SQLException("Withdraw failed: insufficient funds or account missing.");
            }

            // 6) Create savepoint after successful withdraw
            afterWithdraw = con.setSavepoint("after_withdraw");

            // 7) Do deposit
            depositStmt.setDouble(1, amount);
            depositStmt.setInt(2, toId);
            int rows2 = depositStmt.executeUpdate();

            if (rows2 == 0) {
                // deposit failed (account missing)
                // rollback to savepoint so withdraw undone
                con.rollback(afterWithdraw);
                throw new SQLException("Deposit failed: destination account missing.");
            }

            // 8) All good -> commit
            con.commit();
            System.out.println("Transfer successful: " + amount + " from id=" + fromId + " to id=" + toId);

        } catch (SQLException e) {
            // 9) On error, rollback entire transaction if possible
            System.err.println("Transaction error: " + e.getMessage());
            if (con != null) {
                try {
                    con.rollback(); // full rollback
                    System.out.println("Transaction rolled back.");
                } catch (SQLException ex) {
                    System.err.println("Rollback failed: " + ex.getMessage());
                }
            }
        } finally {
            // 10) Clean up resources and restore autocommit
            try {
                if (withdrawStmt != null) withdrawStmt.close();
                if (depositStmt  != null) depositStmt.close();
                if (con != null) {
                    con.setAutoCommit(true); // restore default
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("Cleanup failed: " + e.getMessage());
            }
        }
    }
}
