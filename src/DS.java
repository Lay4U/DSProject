import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DS {
	public static String[] result;
	public static String[] result1;
	public static String[] result2;
	public static String[] result3;
	public static String[] result4;

	private static ResultSet rs;
	public static int resultSize, dbcnt;

	static long start, end;
	static double time;

	public DS(String input_url, String input_dbnum) throws IOException {

		String url_add = input_url;
		int dbnum = Integer.parseInt(input_dbnum);
		String inputLine = "";
		String input = "";

		try {
			URL url = new URL(url_add);
			URLConnection conn = url.openConnection();
			conn.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			while ((inputLine = in.readLine()) != null)
				input += inputLine;
		} catch (MalformedURLException e) {
			// the URL is not in a valid form
			System.out.println(e);
		} catch (IOException e) {
			// the connection couldn't be established
			System.out.println(e);
		}

		Document jsoupDoc = Jsoup.parse(input);
		String temp = jsoupDoc.text().replaceAll("\\.", "").replaceAll("\"", "").replaceAll(",", "").replaceAll(":", "")
				.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(";", "").replaceAll("\\[(.*?)\\]", "")
				.replaceAll("®", "").replaceAll("-", "").toLowerCase();
		result = temp.split(" ");

		resultSize = result.length;

		dbcnt = dbnum;
	}

	public static void main(String[] args) throws Exception {
		new te();

		// ArrayList<String[]> setResult = new ArrayList<>();

	}

	public static void doDS() throws ClassNotFoundException, SQLException {
		start = System.nanoTime();
		String[] result1 = new String[resultSize / dbcnt];
		String[] result2 = new String[resultSize / dbcnt];
		String[] result3 = new String[resultSize / dbcnt];
		String[] result4 = new String[resultSize / dbcnt];

		ResultSet rs;
		int divide = resultSize / dbcnt;
		int dist_size = (resultSize / dbcnt);

		if (dbcnt == 1) {
			for (int i = 0; i < divide; i++) {
				result1[i] = result[i];
			}
		}
		if (dbcnt == 2) {
			for (int i = 0; i < divide; i++) {
				result1[i] = result[i];
			}
			for (int i = 0; i < divide; i++) {
				result2[i] = result[i + divide * 1];
			}
		}
		if (dbcnt == 4) {
			for (int i = 0; i < divide; i++) {
				result1[i] = result[i];
			}
			for (int i = 0; i < divide; i++) {
				result2[i] = result[i + divide * 1];
			}
			for (int i = 0; i < divide; i++) {
				result3[i] = result[i + divide * 2];
			}
			for (int i = 0; i < divide; i++) {
				result4[i] = result[i + divide * 3];
			}
		}

		ArrayList<Integer> getSqlcnt = new ArrayList<>();
		ArrayList<String> getSqlstr = new ArrayList<>();
		ArrayList<String> getSqlresult = new ArrayList<>();
		String dburl1 = "jdbc:mysql://localhost:3306/ds?&serverTimezone=UTC";
		String dburl2 = "jdbc:mysql://192.168.17.128:3306/ds2?&serverTimezone=UTC";
		String dburl3 = "jdbc:mysql://192.168.17.129:3306/ds2?&serverTimezone=UTC";
		String dburl4 = "jdbc:mysql://192.168.17.130:3306/ds2?&serverTimezone=UTC";

		
		 ExecutorService executorService = Executors.newFixedThreadPool(
	                Runtime.getRuntime().availableProcessors()
	        );
	        
	        System.out.println("[작업 처리 요청]");
	        
	        Runnable run = new Runnable() {
	            @Override
	            public void run() {
	        		if (dbcnt == 1) {
	        			System.out.println("1 of db connect");
	        			try {
							getsql(getSqlcnt, getSqlstr, result1, dburl1);
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        		} else if (dbcnt == 2) {
	        			System.out.println("2 of db connect");
	        			try {
							getsql(getSqlcnt, getSqlstr, result1, dburl1);
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        			try {
							getsql(getSqlcnt, getSqlstr, result2, dburl2);
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        		} else if (dbcnt == 4) {
	        			System.out.println("4 of db connect");
	        			try {
							getsql(getSqlcnt, getSqlstr, result1, dburl1);
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        			try {
							getsql(getSqlcnt, getSqlstr, result2, dburl2);
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        			try {
							getsql(getSqlcnt, getSqlstr, result3, dburl3);
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        			try {
							getsql(getSqlcnt, getSqlstr, result4, dburl4);
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        		} else {
	        			System.out.println("error please check validation");
	        		}
	            }
	        };
	        
	        Future future = executorService.submit(run);
	        
	        try {
	            future.get();
	            System.out.println("[작업 처리 완료]");
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } catch (ExecutionException e) {
	            e.printStackTrace();
	        }
	        executorService.shutdown();
	    
	

		
		
		end = System.nanoTime();

		DB mydb = new DB();
		rs = mydb.DoResult(getSqlcnt, getSqlstr);
//		ArrayList<String> getSqlstr_result = new ArrayList<>();
//		ArrayList<Integer> getSqlcnt_result = new ArrayList<>();
		getSqlcnt.clear();
		getSqlstr.clear();

		while (rs.next()) {
			System.out.println(rs.getInt(1) + "\t" + rs.getString(2));
			// te.DisplayResult(rs.getInt(1),rs.getString(2));
			getSqlcnt.add(rs.getInt(1));
			getSqlstr.add(rs.getString(2));
		}

		int sum = 0;
		for (Integer tmp : getSqlcnt) {
			sum += tmp;
		}

		te.DisplayResult(sum, (end - start) / 1000000000.0, getSqlcnt, getSqlstr);
//		getSqlcnt.addAll(getSqlstr);
//		te.DisplayResult(getSqlcnt, getSqlstr);
		// listToSend.add(getSqlcnt);

		System.out.println(sum);
		System.out.println("Finished");
	}

	public static void getsql(ArrayList<Integer> getSqlcnt, ArrayList<String> getSqlstr, String[] result, String dbUrl)
			throws ClassNotFoundException, SQLException {
		// sql 결과받기
		DB mydb = new DB();
		rs = mydb.DoDB(result, dbUrl);

		while (rs.next()) {
			System.out.println(rs.getInt(1) + "\t" + rs.getString(2));
			// te.DisplayResult(rs.getInt(1),rs.getString(2));
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