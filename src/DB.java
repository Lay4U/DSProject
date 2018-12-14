import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    public static void main(String[] args) {
        PreProcessing pp = null;
        
        String[] result = pp.getResult();
        Connection connection = null;
        Statement st = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ds?characterEncoding=UTF-8&serverTimezone=UTC", "root", "kgu123");
            st = connection.createStatement();

            String sql;
            for (String word : result) {
                sql = "INSERT INTO CONTENT(word)" + " VALUE (\"" + word + "\");";
                st.executeUpdate(sql);
            }
            System.out.println("finised insert value");
            st.close();
            connection.close();
        } catch (SQLException se1) {
            se1.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (st != null)
                    st.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

}