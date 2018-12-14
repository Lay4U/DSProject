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

public class PreProcessing2 {
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
        
        

        System.out.println("total word count :" + count);
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ds?characterEncoding=UTF-8&serverTimezone=UTC", "root", "kgu123");
        st = connection.createStatement();

//sql 삽입        
//        String sql;
//        for (String word : result) {
//            sql = "INSERT INTO CONTENT(word)" + " VALUE (\"" + word + "\");";
//            st.execute(sql);
//        }
        
        
//        System.out.println("finised insert value");
//        String sql = "SELECT COUNT(word), word FROM content GROUP BY word ORDER BY word;";
//        rs = st.executeQuery(sql);
//        System.out.println(rs);
        
//        int rowCnt = 0;
//        rs = st.executeQuery("SELECT COUNT(*) FROM content");
//        if(rs.next()) rowCnt = rs.getInt(1);
//        System.out.println("Total counts : " + rowCnt);
        String sql = "SELECT COUNT(word), word FROM content GROUP BY word ORDER BY Count(word) DESC;";
        rs = st.executeQuery(sql);
        ArrayList<Integer> getSqlcnt = new ArrayList<>();
        ArrayList<String> getSqlstr = new ArrayList<>();
        while(rs.next()) 
        {
            System.out.println(rs.getInt(1)+"\t"+rs.getString(2));
            getSqlcnt.add(rs.getInt(1));
            getSqlstr.add(rs.getString(2));
        }
        
        int sum=0;
        for (Integer tmp : getSqlcnt) {
            sum+=tmp;
        }
        System.out.println(sum);
        st.close();
        connection.close();
        in.close();
        
    }

}