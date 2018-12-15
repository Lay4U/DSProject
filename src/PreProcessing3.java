import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PreProcessing3 {
	public String[] result;
	public String[] result2;
	public String[] result3;
	private static ResultSet rs;

	public static void main(String[] args) throws Exception {
		URL url = new URL("https://en.wikipedia.org/wiki/Distributed_computing");
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		String inputLine = "";
		String input = "";

		while ((inputLine = in.readLine()) != null)
			input += inputLine;

		Document jsoupDoc = Jsoup.parse(input);
		int count = Jsoup.parse(input).text().split(" ").length;

		String temp = jsoupDoc.text().replaceAll("\\.", "").replaceAll("\"", "").replaceAll(",", "").replaceAll(":", "")
				.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(";", "").replaceAll("\\[(.*?)\\]", "")
				.replaceAll("짰", "").replaceAll("-", "");
		String[] result = temp.split(" ");
		
		int resultSize=result.length;
		int dbcnt=4;
		//ArrayList<String[]> setResult = new ArrayList<>();
		String[] result1 = new String[resultSize/dbcnt];
		String[] result2 = new String[resultSize/dbcnt];
		String[] result3 = new String[resultSize/dbcnt];
		String[] result4 = new String[resultSize/dbcnt];
		System.out.println("total word count :" + count);
		ResultSet rs;

		

		for (int i = 0; i < resultSize/dbcnt; i++) {
			result1[i] = result[i];
		}
//		
		for (int i = 0; i < resultSize/dbcnt ; i++) {
			result2[i] = result[i];
		}
		for (int i = 0; i < resultSize/dbcnt ; i++) {
			result3[i] = result[i];
		}
		for (int i = 0; i < resultSize/dbcnt ; i++) {
			result4[i] = result[i];
		}

		
		ArrayList<Integer> getSqlcnt = new ArrayList<>();
		ArrayList<String> getSqlstr = new ArrayList<>(); 
		String dburl1="jdbc:mysql://localhost:3306/ds?&serverTimezone=UTC";
		String dburl2="jdbc:mysql://192.168.17.128:3306/ds2?&serverTimezone=UTC";
		String dburl3="jdbc:mysql://192.168.17.129:3306/ds2?&serverTimezone=UTC";
		String dburl4="jdbc:mysql://192.168.17.130:3306/ds2?&serverTimezone=UTC";
		
		
		if(dbcnt==1)
		{
			System.out.println("1 of db connect");
			getsql(getSqlcnt, getSqlstr, result1, dburl1);
		}
		else if(dbcnt==2)
		{
			System.out.println("2 of db connect");
			getsql(getSqlcnt, getSqlstr, result1, dburl1);
			getsql(getSqlcnt, getSqlstr, result2, dburl2);
		}
		else if(dbcnt==4)
		{
			System.out.println("4 of db connect");
			getsql(getSqlcnt, getSqlstr, result1, dburl1);
			getsql(getSqlcnt, getSqlstr, result2, dburl2);
			getsql(getSqlcnt, getSqlstr, result3, dburl3);
			getsql(getSqlcnt, getSqlstr, result4, dburl4);
		}
		else
		{
			System.out.println("error please check validation");
		}

		int sum = 0;
		for (Integer tmp : getSqlcnt) {
			sum += tmp;
		}
		System.out.println(sum);
		System.out.println("Finished");

		in.close();

	}

	public static void getsql(ArrayList<Integer> getSqlcnt, ArrayList<String> getSqlstr, String[] result, String dbUrl) throws ClassNotFoundException, SQLException
	{
		//sql 결과받기
		DB mydb = new DB();
		rs = mydb.DoDB(result, dbUrl);
		
		while (rs.next()) {
			System.out.println(rs.getInt(1) + "\t" + rs.getString(2));
			getSqlcnt.add(rs.getInt(1));
			getSqlstr.add(rs.getString(2));
		}
	}

}

//System.out.println("finised insert value");
//String sql = "SELECT COUNT(word), word FROM content GROUP BY word ORDER BY word;";
//rs = st.executeQuery(sql);
//System.out.println(rs);

//int rowCnt = 0;
//rs = st.executeQuery("SELECT COUNT(*) FROM content");
//if(rs.next()) rowCnt = rs.getInt(1);
//System.out.println("Total counts : " + rowCnt);