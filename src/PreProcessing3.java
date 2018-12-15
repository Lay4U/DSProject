import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PreProcessing3 extends JFrame {
	public String[] result;
	public String[] result2;
	public String[] result3;
	private static ResultSet rs;
	JTextField url_J, dbNum_J;
	static String input_url =null;
	static String dbnum;
	protected static JTextField textField;
	protected static JTextArea result_J;

	public PreProcessing3() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 프레임 완전하게 끄기
		this.setSize(400, 500);
		this.setVisible(true);

		// Layout 배치설정자
		this.setLayout(new GridLayout(5, 2));
		add(new JLabel("   Distributed System"));

		JPanel panel1 = new JPanel();
		panel1.add(new JLabel("  url(default is wiki DS):"));
		url_J = new JTextField(20);
		panel1.add(url_J);

		JPanel panel2 = new JPanel();
		panel2.add(new JLabel("  db개수 :"));
		dbNum_J = new JTextField(3);
		panel2.add(dbNum_J);

		this.add(panel1);
		add(panel2);
		JButton button = new JButton("시작");
		add(button);
		JPanel panel3 = new JPanel();
		result_J = new JTextArea();
		panel3.add(result_J);
		
		this.setVisible(true);
		// 버튼 리스너 연결
		button.addActionListener(new Listener(this));



	}

	class Listener implements ActionListener {
		JFrame frame;

		public Listener(JFrame f) {
			frame = f;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// 버튼을 누르면 이쪽으로 제어가 이동
			System.out.println(arg0.getActionCommand());
			input_url = url_J.getText();
			dbnum = dbNum_J.getText();

			// 다이얼로그
			// JOptionPane.showMessageDialog(frame, n+a);
		}
	}

	public static void actionPerformed(ActionEvent evt, String result1, String result2) {
		String text = result1+result2;
		result_J.append(text);
		textField.selectAll();
		result_J.setCaretPosition(result_J.getDocument().getLength());
	}

	public static void main(String[] args) throws Exception {
//		PreProcessing3 pp = new PreProcessing3();
        JFrame frame = new JFrame();
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = frame.getContentPane();
        container.setLayout(new FlowLayout());

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(150, 25));

        JLabel label = new JLabel("Input will appear here");

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                System.out.println("Input: " + input);

                label.setText(input);
            }
        });



		URL url = new URL(input_url);
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

		int resultSize = result.length;
		int dbcnt = Integer.parseInt(dbnum);
		// ArrayList<String[]> setResult = new ArrayList<>();
		String[] result1 = new String[resultSize / dbcnt];
		String[] result2 = new String[resultSize / dbcnt];
		String[] result3 = new String[resultSize / dbcnt];
		String[] result4 = new String[resultSize / dbcnt];
		System.out.println("total word count :" + count);
		ResultSet rs;

		for (int i = 0; i < resultSize / dbcnt; i++) {
			result1[i] = result[i];
		}
//		
		for (int i = 0; i < resultSize / dbcnt; i++) {
			result2[i] = result[i];
		}
		for (int i = 0; i < resultSize / dbcnt; i++) {
			result3[i] = result[i];
		}
		for (int i = 0; i < resultSize / dbcnt; i++) {
			result4[i] = result[i];
		}

		ArrayList<Integer> getSqlcnt = new ArrayList<>();
		ArrayList<String> getSqlstr = new ArrayList<>();
		String dburl1 = "jdbc:mysql://localhost:3306/ds?&serverTimezone=UTC";
		String dburl2 = "jdbc:mysql://192.168.17.128:3306/ds2?&serverTimezone=UTC";
		String dburl3 = "jdbc:mysql://192.168.17.129:3306/ds2?&serverTimezone=UTC";
		String dburl4 = "jdbc:mysql://192.168.17.130:3306/ds2?&serverTimezone=UTC";

		if (dbcnt == 1) {
			System.out.println("1 of db connect");
			getsql(getSqlcnt, getSqlstr, result1, dburl1);
		} else if (dbcnt == 2) {
			System.out.println("2 of db connect");
			getsql(getSqlcnt, getSqlstr, result1, dburl1);
			getsql(getSqlcnt, getSqlstr, result2, dburl2);
		} else if (dbcnt == 4) {
			System.out.println("4 of db connect");
			getsql(getSqlcnt, getSqlstr, result1, dburl1);
			getsql(getSqlcnt, getSqlstr, result2, dburl2);
			getsql(getSqlcnt, getSqlstr, result3, dburl3);
			getsql(getSqlcnt, getSqlstr, result4, dburl4);
		} else {
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

	public static void getsql(ArrayList<Integer> getSqlcnt, ArrayList<String> getSqlstr, String[] result, String dbUrl)
			throws ClassNotFoundException, SQLException {
		// sql 결과받기
		DB mydb = new DB();
		rs = mydb.DoDB(result, dbUrl);
		ActionEvent tmp = null;
		while (rs.next()) {
			actionPerformed(tmp, String.valueOf(rs.getInt(1)), rs.getString(2));
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