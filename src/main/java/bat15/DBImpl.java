package bat15;

import bat15.security.Security;

import javax.enterprise.context.ApplicationScoped;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@ApplicationScoped
public class DBImpl implements DB {


    private static String contructJDBCURL(String host, String port, String database) {
        return "jdbc:postgresql://" + host + ":" + port + "/" + database;
    }


    private Connection getConnectionFromProperties(Properties properties) throws SQLException {
        final String url = contructJDBCURL(
                properties.getProperty("db_host"),
                properties.getProperty("db_port"),
                properties.getProperty("db_name"));
        final String user = properties.getProperty("db_user");
        final String password = properties.containsKey("db_password") ?
                properties.getProperty("db_password") :
                Security.getPasswordFromFile(properties.getProperty("db_passfile"));

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

    private Properties getDefaultLocalProperties() {
        Properties p = new Properties();
        p.setProperty("db_host", "localhost");
        p.setProperty("db_port", "5432");
        p.setProperty("db_name", "iopt");
        p.setProperty("db_user", "postgres");
        p.setProperty("db_password", "postgres");
        return p;
    }

    public String getFirstScript() {
        String firstScript = "";

        try {

            Class.forName("org.postgresql.Driver");

            Properties properties = getDefaultLocalProperties();

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
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        }

        return firstScript;
    }

    public List<ScriptEntity> getScripts() {
        List<ScriptEntity> scripts = new ArrayList<>();

        String query = "SELECT id, name, value, id_property FROM script;";

        try {

            Class.forName("org.postgresql.Driver");

            Properties properties = getDefaultLocalProperties();

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
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        }
        return scripts;
    }

    @Override
    public boolean saveScript(String id, String value) {

        try {

            Class.forName("org.postgresql.Driver");
            Properties properties = getDefaultLocalProperties();


            try (Connection conn = getConnectionFromProperties(properties);
                 PreparedStatement s = conn.prepareStatement("UPDATE script SET value= ? WHERE id= ?")) {

                s.setString(1, value);
                s.setLong(2, Long.parseLong(id));

                s.executeUpdate();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        }
        return false;
    }

    @Override
    public ModelEntity getModelByScript(String scriptId) {


        final ModelEntity model = new ModelEntity();

        String preQuery = "SELECT " +
                "model.id as model_id " +
                "FROM model, object, property, script " +
                "WHERE " +
                "  script.id_property = property.id " +
                "AND property.id_object = object.id " +
                "AND object.id_model = model.id " +
                "AND script.id = ?;";

        String query =
                "SELECT" +
                        "  model.id AS model_id," +
                        "  model.name AS model_name," +
                        "  object.id AS object_id," +
                        "  object.name AS object_name," +
                        "  property.id AS property_id," +
                        "  property.name AS property_name," +
                        "  property.value AS property_value" +
                        "  FROM model, object, property" +
                        "  WHERE" +
                        "  property.id_object = object.id" +
                        "  AND object.id_model = model.id" +
                        "  AND model.id = ?;";

        try {

            Class.forName("org.postgresql.Driver");

            Properties properties = getDefaultLocalProperties();

            try (Connection conn = getConnectionFromProperties(properties);
                 PreparedStatement preStmt = conn.prepareStatement(preQuery);
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                preStmt.setLong(1, Long.parseLong(scriptId));
                ResultSet modelResult = preStmt.executeQuery();
                modelResult.next();
                final String modelId = modelResult.getString("model_id");
                modelResult.close();


                stmt.setLong(1, Long.parseLong(modelId));
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {

                    model.setId(rs.getString("model_id"));
                    model.setName(rs.getString("model_name"));

                    final ObjectEntity obj = new ObjectEntity();
                    obj.setId(rs.getString("object_id"));
                    obj.setName(rs.getString("object_name"));

                    Optional oo = model
                            .getObjects()
                            .stream()
                            .filter(o -> o.getId().equals(obj.getId()))
                            .findFirst();

                    if (!oo.isPresent())
                        model.getObjects().add(obj);

                    final PropertyEntity prop = new PropertyEntity();
                    prop.setId("property_id");
                    prop.setName("property_name");
                    prop.setValue("property_value");

                    model.getObjects()
                            .stream()
                            .filter(o -> o.getId().equals(obj.getId()))
                            .findFirst()
                            .ifPresent(o -> o.getProperties().add(prop));

                }
                rs.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        }
        return model;
    }

    @Override
    public void saveModel(ModelEntity model) {

    }


}
