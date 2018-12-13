import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.safety.Whitelist;

import java.io.*;

public class PreProcessing {
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
				.replaceAll("®", "").replaceAll("-", "");
		
		 String[] result = temp.split(" ");

		for (String word : result) {
			out.println(word);
		}

		System.out.println("total word count :" + count);
		Connection connection = null;
		Statement st = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/ds?characterEncoding=UTF-8&serverTimezone=UTC", "root", "kgu123");
		st = connection.createStatement();

		String sql;
		for (String word : result) {
			sql = "INSERT INTO CONTENT(word)" + " VALUE (\"" + word + "\");";
			st.execute(sql);
		}
		System.out.println("finised insert value");
		st.close();
		connection.close();
		in.close();
		
	}
	
	
}