package bat15;

import bat15.security.Security;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Local
@Stateless
public class DB {

    /* always null */
    @Resource(lookup = "IOPT-Server")
    private Properties properties;


    private static String contructJDBCURL(String host, String port, String database) {
        return "jdbc:postgresql://" + host + ":" + port + "/" + database;
    }


    private Connection getConnectionFromProperties(Properties properties) throws SQLException {
        final String url = contructJDBCURL(
                properties.getProperty("db_host"),
                properties.getProperty("db_port"),
                properties.getProperty("db_name"));
        final String user = properties.getProperty("db_user");
        final String password = Security.getPasswordFromFile(properties.getProperty("db_passfile"));
        return DriverManager.getConnection(url, user, password);
    }

    private Properties getDefaultProperties() {
        Properties p = new Properties();
        p.setProperty("db_host", "193.32.20.242");
        p.setProperty("db_port", "5432");
        p.setProperty("db_name", "learn");
        p.setProperty("db_user", "dbmanager");
        p.setProperty("db_passfile", "/etc/security/passwords/iopt/db_password.pswd");
        return p;
    }

    public String getFirstScript()
    {
        String firstScript = "";

        try {

            Class.forName("org.postgresql.Driver");

            Properties properties = this.properties != null ?
                    this.properties: getDefaultProperties();

            try (Connection conn = getConnectionFromProperties(properties);
                 Statement stmt = conn.createStatement()) {

                ResultSet rs = stmt.executeQuery("SELECT * FROM script");

                if (rs.next()) {
                    //Retrieve by column name
                    int id = rs.getInt("id");
                    firstScript = rs.getString("value");

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

    public List<ScriptEntity> getScripts()
    {
        List<ScriptEntity> scripts = new ArrayList<>();

        String query = "SELECT id, name, value, id_property FROM script;";

        try {

            Class.forName("org.postgresql.Driver");

            Properties properties = this.properties != null ?
                    this.properties: getDefaultProperties();

            try (Connection conn = getConnectionFromProperties(properties);
                 Statement stmt = conn.createStatement()) {

                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {

                    ScriptEntity entity = new ScriptEntity();
                    entity.setId(rs.getString("id"));
                    entity.setName(rs.getString("name"));
                    entity.setValue(rs.getString("value"));
                    entity.setIdProperty(rs.getString("id_property"));

                    scripts.add(entity);

                }
                rs.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException ce)
        {
            ce.printStackTrace();
        }
        return scripts;
    }


}
