
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class te extends JFrame {

	JTextField input_url_J, input_dbnum_J;// 클래스 변수로 선언
	JTextArea result_J;
	String input_url;
	String input_dbnum;

	public te() {
		// new JFrame();생략됨 나자신이니까 쓸수 없음

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 프레임 완전하게 끄기
		this.setSize(300, 200);
		this.setVisible(true);

		// Layout 배치설정자
		this.setLayout(new GridLayout(6, 2));
		add(new JLabel("   !"));

		JPanel panel1 = new JPanel();
		panel1.add(new JLabel("  url :"));
		input_url_J = new JTextField(20);
		panel1.add(input_url_J);

		JPanel panel2 = new JPanel();
		panel2.add(new JLabel("  number of db :"));
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

	class Listener implements ActionListener {
		JFrame frame;

		public Listener(JFrame f) {
			frame = f;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// 버튼을 누르면 이쪽으로 제어가 이동
			// System.out.println(arg0.getActionCommand());
			if (arg0.getActionCommand() == "Submit") {
				input_url = input_url_J.getText();
				input_dbnum = input_dbnum_J.getText();
				try {
					DS ds = new DS(input_url, input_dbnum);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(arg0.getActionCommand() == "Start!")
			{
				try {
					DS.doDS();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			// 다이얼로그
			// JOptionPane.showMessageDialog(frame, n + a);
		}
	}

	public static void main(String[] args) {
		new te();
	}
}