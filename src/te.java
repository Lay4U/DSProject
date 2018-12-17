
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class te extends JFrame {

	JTextField input_url_J, input_dbnum_J;// 클래스 변수로 선언
	JTextArea result_J;
	String input_url;
	String input_dbnum;

	public te() {
		// new JFrame();생략됨 나자신이니까 쓸수 없음
		this.setTitle("Distribute Computing System");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 프레임 완전하게 끄기
		this.setSize(500, 300);
		this.setVisible(true);

		// Layout 배치설정자
		this.setLayout(new GridLayout(6, 2));
		add(new JLabel("   Dsitributed System"));

		JPanel panel1 = new JPanel();
		panel1.add(new JLabel("  url :"));
		input_url_J = new JTextField(20);
		input_url_J.setText("https://en.wikipedia.org/wiki/Distributed_computing");
//		input_url_J.setText("https://www.naver.com");
		panel1.add(input_url_J);

		JPanel panel2 = new JPanel();
		panel2.add(new JLabel("  number of db (1, 2, 4) :"));
		input_dbnum_J = new JTextField(3);
		panel2.add(input_dbnum_J);

		this.add(panel1);
		add(panel2);

		JPanel panel3 = new JPanel();
		JButton button = new JButton("Submit");
		panel3.add(button);
		add(panel3);

		JPanel panel4 = new JPanel();
		JButton button2 = new JButton("Start!");
		panel4.add(button2);
		add(panel4);

		this.setVisible(true);

		// 버튼 리스너 연결
		button.addActionListener(new Listener(this));
		button2.addActionListener(new Listener(this));
	}

	public static void DisplayResult(ArrayList<Integer> getSqlcnt, ArrayList<String> getSqlstr) // constructor of the
																								// DisplayGuiHelp object
																								// that has the list
	// passed to it on creation
	{
		
	    JFrame f = new JFrame("Text Field View");
	    JTextArea tf = new JTextArea(32, 10);
	    ArrayList<String> result_t = new ArrayList<String>();
		Stream.concat(getSqlcnt.stream(), getSqlcnt.stream()).forEachOrdered(str -> {
		result_t.add(getSqlcnt + "\t" + getSqlstr);
	});
		String result = "";
		for (String s : result_t) {
			result += s + "\n";
		}
	    tf.setText(result);
	    f.getContentPane().add(tf);
	    f.pack();
	    f.setVisible(true);
//        final JFrame theFrame = new JFrame();
//        theFrame.setTitle("Stack exchange help");
//        theFrame.setSize(500, 500);
//        theFrame.setLocation(550, 400);
//
//		JPanel mainPanel = new JPanel();
//
//		JTextArea theText = new JTextArea(5, 25); // create the text area
////		Iterator<Integer> i1 = getSqlcnt.iterator();
////		Iterator<String> i2 = getSqlstr.iterator();
////		while (((Iterator<Integer>) getSqlcnt).hasNext() && ((Iterator<Integer>) getSqlstr).hasNext()) {
////			theText.append(getSqlcnt + "\t" + getSqlstr);
////		}
//		Stream.concat(getSqlcnt.stream(), getSqlcnt.stream()).forEachOrdered(str -> {
//			theText.append(getSqlcnt + "\t" + getSqlstr);
//		});
////        for(String cnt, str : getSqlcnt, getSqlstr)
////        {
////            theText.append(text + "\n"); //append the contents of the array list to the text area
////
////        }
//		mainPanel.add(theText); // add the text area to the panel
//
//		theFrame.getContentPane().add(mainPanel); // add the panel to the frame
//		theFrame.pack();
//		theFrame.setVisible(true);

	}

	class Listener implements ActionListener {
		JFrame frame;

		public Listener(JFrame f) {
			frame = f;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {

			
			System.out.println(arg0.getActionCommand());
			if (arg0.getActionCommand() == "Submit") {	//입력받고 ds 생성
				input_url = input_url_J.getText();
				input_dbnum = input_dbnum_J.getText();
				try {
					DS ds = new DS(input_url, input_dbnum);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (arg0.getActionCommand() == "Start!") {
				System.out.println(arg0.getActionCommand());	//ds 실행
				try {
					DS.doDS();
					// JOptionPane.showMessageDialog(frame, "a");
					// labelMessage.setText(message );
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			// 다이얼로그

		}
	}

	public static void main(String[] args) {
		new te();
	}

	public static void DisplayResult(int sum, double time,ArrayList<Integer> getSqlcnt, ArrayList<String> getSqlstr) {
		// TODO Auto-generated method stub
		String ad = "";
	    JFrame f = new JFrame("Result View");	  
	    GridLayout layout = new GridLayout(3,1);	    
	    f.setLayout(layout);
	    JLabel tf = new JLabel();
	    JLabel l = new JLabel();
	    JLabel l2 = new JLabel();
	    f.setPreferredSize(new Dimension(500,500));
	    
	    l.setText("실행 시간 : " + time + " second") ;
	    tf.setText("단어의 총 개수 : " +String.valueOf(sum) );	

//	    Iterator<String> iter = tree.keySet().iterator();			        
	    Iterator<String> iter = getSqlstr.iterator();
	    Iterator<Integer> iter2 = getSqlcnt.iterator();
	    ad += "<html> 단어&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;횟수<br/>";
	    while(iter.hasNext()) {
	    	String str = iter.next();
	    	String cnt = iter2.next().toString();
	    	ad += str +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+cnt+"<br/>";	
	    }

	    ad += "</html>";
	    l2.setText(ad);
	    JScrollPane scroll = new JScrollPane();
	    scroll.setPreferredSize(new Dimension(500,600));
	    scroll.setViewportView(l2);
	    tf.setPreferredSize(new Dimension(500,50));
	    l.setPreferredSize(new Dimension(500,50));
	   
	    f.getContentPane().add(tf);
	    f.getContentPane().add(l);
	    f.getContentPane().add(scroll);
	    
	    //f.add(l2);
	    f.pack();
	    f.setVisible(true);
	}
}