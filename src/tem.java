

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class tem extends JFrame{
	
	JTextField name,age;//Ŭ���� ������ ����
	
	public tem(){
		//new JFrame();������ ���ڽ��̴ϱ� ���� ����
		
		this.setDefaultCloseOperation(
				JFrame.EXIT_ON_CLOSE);//������ �����ϰ� ����
		this.setSize(300,200);
		this.setVisible(true);
		
		//Layout ��ġ������
		this.setLayout(new GridLayout(5,2));
		add(new JLabel("   !"));
		
		JPanel panel1 = new JPanel();
		panel1.add(new JLabel("  �̸� :"));
		name=new JTextField(20);
		panel1.add(name);
		
		JPanel panel2 = new JPanel();
		panel2.add(new JLabel("  ���� :"));
		age =new JTextField(3);
		panel2.add(age);
		
		this.add(panel1);
		add(panel2);
		JButton button = new JButton("Ȯ��");
		add(button);
		this.setVisible(true);
		//��ư ������ ����
		button.addActionListener(new Listener(this));
	}
	
	class Listener implements ActionListener{
		JFrame frame;
		public Listener(JFrame f){
			frame =f;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//��ư�� ������ �������� ��� �̵�
			System.out.println(arg0.getActionCommand());
			String n =name.getText();
			System.out.println(n);
			String a =age.getText();
			System.out.println(a);
			
			//���̾�α�
			JOptionPane.showMessageDialog(frame, n+a);
		}
	}
	public static void main(String[] args) {
		new tem();
	}
}