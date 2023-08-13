package samples.db;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectDB implements Closeable {
    private Connection db;
    private SQLException exception;
    private static ConnectDB instance;
    public ConnectDB(Connection conn, SQLException exception) {
        this.db = conn;
        this.exception = exception;
    }
    public ConnectDB(Connection conn){ this(conn,null); }
    public static void main(String[] args) {
        try(var conn = getConnection()){
            System.out.println("Connected successfully.");
        }
        var conn = getConnection();
        if(conn.isError()) System.err.println(conn.exception);
        conn.close();
    }
    public static ConnectDB getConnection() {
        if(instance != null) return instance;
        Connection conn = null;
        SQLException exception = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:myDbFile.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            //System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            exception = e;
        }
        return instance = new ConnectDB(conn,exception);
    }
    @Override
    public void close() {
        if(db != null){
            try {
                db.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        instance = null;
    }
    public SQLException getException(){ return exception; }
    public boolean isError(){ return exception != null; }
    public int executeUpdate(String sql) {
        try(var stmt = db.createStatement()){
            exception = null;
            return stmt.executeUpdate(sql);
        }catch(SQLException ex){
            exception = ex;
        }
        return 0;
    }
    public int executeUpdate(String sql, Object ...parameters){
        try (var stmt = db.prepareStatement(sql)) {
            Object object;
            for (int i=0;i<parameters.length;i++) {
                object = parameters[i];
                if(object instanceof Integer) stmt.setInt(i+1, (Integer)object);
                else if(object instanceof Double) stmt.setDouble(i+1,(Double)object);
                else stmt.setString(i+1,object.toString());
            }
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public ResultSet execute(String sql, Object ...parameters){
        try {
            var stmt = db.prepareStatement(sql);
            exception = null;
            Object object;
            for (int i=0;i<parameters.length;i++) {
                object = parameters[i];
                if(object instanceof Integer) stmt.setInt(i+1, (Integer)object);
                else if(object instanceof Double) stmt.setDouble(i+1,(Double)object);
                else stmt.setString(i+1,object.toString());
            }
            return stmt.executeQuery();
        } catch (SQLException e) {
            exception = e;
            e.printStackTrace();
        }
        return null;
    }
    public Connection getDb(){ return db; }
}