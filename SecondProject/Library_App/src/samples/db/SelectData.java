package samples.db;

import java.sql.ResultSet;

public class SelectData {
    public static void main(String[] args) {
        try(ResultSet rs = ConnectDB.getConnection().execute("SELECT * FROM products")){
            while(rs.next()){
                System.out.printf("%3d%-10s%05f\n", 
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getDouble(3));
            }
        }catch(Exception ex){ex.printStackTrace();}   
    }
}
