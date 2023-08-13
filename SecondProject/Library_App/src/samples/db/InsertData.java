package samples.db;

public class InsertData {
    public static void main(String[] args) {
        CreateProductTable.main(args);
        try (ConnectDB conn = ConnectDB.getConnection();) {
            int ret = conn.executeUpdate("INSERT INTO products(name,price) VALUES(?,?)", "Grape",5.5);
            System.out.println(ret);
            ret = conn.executeUpdate("INSERT INTO products(name,price) VALUES(?,?)", "Banana",0.5);
            System.out.println(ret);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
