import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

	public ResultSet DoDB(String[] temp, String dbURL) throws ClassNotFoundException, SQLException {
		String[] result = temp;
		String dbUrl=dbURL;
		// sql 준비
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;

		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection(
				dbURL, "root", "kgu123");
		//"jdbc:mysql://localhost:3306/ds?characterEncoding=UTF-8&serverTimezone=UTC", "root", "kgu123"
		// "jdbc:mysql://192.168.17.128:3306/ds2?&serverTimezone=UTC", "root",
		// "kgu123");
		// "jdbc:mysql://192.168.17.129:3306/ds2?&serverTimezone=UTC", "root",
		// "kgu123");
		st = connection.createStatement();
		String sql;

		// sql 실행
		sql = "DELETE FROM CONTENT";
		st.execute(sql);

		for (String word : result) {
			sql = "INSERT INTO CONTENT(word)" + " VALUE (\"" + word + "\");";
			st.execute(sql);
		}
		
		sql = "SELECT COUNT(word), word FROM CONTENT GROUP BY word ORDER BY Count(word) DESC;";
		rs = st.executeQuery(sql);
		

		return rs;
	}
}