import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Connect {
    //this is a class to establish Java Database connectivity
    Connection con;//connection object represents connection to the database
    Statement stmt;//statement object is used to send SQL queries to the database
    public Connect() {
        //mySql is an external entity and might run into errors, but exception handling is important
        try {
            //create connection with database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocerytracker", "root", "moumita");
            //create statement
            stmt = con.createStatement();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
