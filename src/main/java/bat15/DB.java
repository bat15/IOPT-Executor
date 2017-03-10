package bat15;

import java.sql.*;

/**
 * Created by xoton on 11.03.2017.
 */
public class DB {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/iopt"; //"jdbc:mysql://localhost/STUDENTS";

    //  Database credentials
    static final String USER = "postgres";
    static final String PASS = "postgres";

    public static String getFirstScript()
    {
        String firstScript = "";

        try {
            Class.forName("org.postgresql.Driver");


            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement()) {

                ResultSet rs = stmt.executeQuery("SELECT * FROM scripts");

                if (rs.next()) {
                    //Retrieve by column name
                    int id = rs.getInt("id");
                    firstScript = rs.getString("content");

                    //Display values
                    System.out.print("ID: " + id);
                    System.out.print(", Content: " + firstScript);
                }
                rs.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException ce)
        {
            ce.printStackTrace();
        }

        return firstScript;
    }


}
