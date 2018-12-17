import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class DB {

	public ResultSet DoDB(String[] result, String dbURL) throws ClassNotFoundException, SQLException {

		String dbUrl = dbURL;
		// sql 준비
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;

		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection(dbURL, "root", "kgu123");

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
	public ResultSet DoResult(ArrayList<Integer> getSqlcnt, ArrayList<String> getSqlstr) throws ClassNotFoundException, SQLException
	{
		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ds?&serverTimezone=UTC", "root", "kgu123");
		String sql;
		st = connection.createStatement();
		sql = "use ds;";
		st.execute(sql);
		sql = "DELETE FROM result";
		st.execute(sql);
		
	    Iterator<String> iter = getSqlstr.iterator();
	    Iterator<Integer> iter2 = getSqlcnt.iterator();
	    while(iter.hasNext()) {
	    	String str = iter.next();
	    	int cnt = iter2.next();
	    	//INSERT INTO result(cnt, str) VALUE (1,"the");
	    	sql = "INSERT INTO result(cnt, str)" + " VALUE ("+cnt+","+"\""+ str + "\");";
	    	st.execute(sql);
	    }
	    //SELECT SUM(cnt) AS cnt, str FROM result GROUP BY str ORDER BY SUM(cnt) DESC;
	    sql = "SELECT SUM(cnt) AS cnt, str FROM result GROUP BY str ORDER BY SUM(cnt) DESC;;";
	    rs = st.executeQuery(sql);
	    
	    return rs;
	}
}