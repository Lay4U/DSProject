import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PreProcessing3 {
	public String[] result;

	public static void main(String[] args) throws Exception {
		PrintWriter out = new PrintWriter("filename.txt");

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
		System.out.println("total word count :" + count);
		ResultSet rs;
		Statement st;


		
		//sql 결과받기
		DB mydb = new DB();
		rs = mydb.DoDB(result, "jdbc:mysql://localhost:3306/ds?characterEncoding=UTF-8&serverTimezone=UTC");
		ArrayList<Integer> getSqlcnt = new ArrayList<>();
		ArrayList<String> getSqlstr = new ArrayList<>();  
		while (rs.next()) {
			System.out.println(rs.getInt(1) + "\t" + rs.getString(2));
			getSqlcnt.add(rs.getInt(1));
			getSqlstr.add(rs.getString(2));
		}

		int sum = 0;
		for (Integer tmp : getSqlcnt) {
			sum += tmp;
		}
		System.out.println(sum);

		in.close();

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