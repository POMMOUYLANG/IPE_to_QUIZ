package samples.db;

import java.sql.Statement;

public class CreateProductTable {
    public static void main(String[] args) {
        ConnectDB conn = ConnectDB.getConnection();
        if(conn.isError()){
            System.err.println(conn.getException());
            System.exit(-1);
        }
        try (Statement stmt = conn.getDb().createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS products");
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS products
                (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT,
                    price DECIMAL
                )
            """);
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();
    }
}
