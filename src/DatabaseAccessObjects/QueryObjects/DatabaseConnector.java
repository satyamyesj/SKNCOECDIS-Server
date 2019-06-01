package DatabaseAccessObjects.QueryObjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public String url = null;
   public String connectionType = null;
     public String connectionPassword = null;
    public Connection connector;

    public void setConnectionAttributes(String url, String userName, String password){
       this.url=url;
        this.connectionType=userName;
        this.connectionPassword=password;

    }

     public boolean connect()  {
        boolean success;
        try {
            this.connector = DriverManager.getConnection(this.url, this.connectionType, this.connectionPassword);
            if(!this.connector.isClosed()) {
                success = true;
            }
            else{
                success=false;
            }
        }catch (SQLException e){
            e.printStackTrace();
            success=false;
        }
        return success;
    }

    public void disconnect() throws SQLException {
        this.connector.close();
    }
}
