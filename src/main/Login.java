package main;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.umrental.Rental_DB;

public class Login extends JFrame implements ActionListener {

	private JPanel p0, p1;
	private JLabel lbl_ID, lbl_PW;
	private JTextField tf_ID, tf_PW;
	private JButton btn_login, btn_exit;

	public Login() { // 생성자
		setTitle("관리자 로그인");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dispose();
		setSize(600, 200);		
		setLocationRelativeTo(this);
		//setResizable(false); // 프레임 창 사이즈 고정

		// 이미지 레이블 생성
		p0 = new JPanel();
		p0.setLayout(new BorderLayout());
		p0.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(p0, BorderLayout.WEST);
		
		ImageIcon icon = new ImageIcon("libs/LoginImg.jpg"); //이미지아이콘 객체를 생성
		
		Image img = icon.getImage(); //변경할이미지 = 변경할아이콘.getImage(); 이미지아이콘을 이미지로 변환
		Image changeImg = img.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
		//추출된 이미지의 크기를 조절하여 새로운 이미지 객체 생성
		ImageIcon changeIcon = new ImageIcon(changeImg);
		//새로운 이미지로 이미지아이콘 객체를 생성
		JLabel imageLabel = new JLabel(changeIcon);
		//JLabel imageLabel = new JLabel(icon);
		p0.add(imageLabel);
		
		p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
		p1.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
		add(p1);
		
		
		lbl_ID = new JLabel("ID: ");
		p1.add(lbl_ID);
		
		tf_ID = new JTextField(10); // 창의 열 개수 10
		p1.add(tf_ID);
		
		lbl_PW = new JLabel("PW: ");
		p1.add(lbl_PW);
		
		tf_PW = new JTextField(10); // 창의 열 개수 10
		p1.add(tf_PW);
		
		btn_login = new JButton("로그인");
		btn_login.addActionListener(this);
		p1.add(btn_login);
		
		btn_exit = new JButton("종료");
		btn_exit.addActionListener(this);
		p1.add(btn_exit);
		
		setVisible(true);
	}

	public static void main(String[] args) {
		new Login();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btn_exit) {
			dispose();
		} else if(obj == btn_login) {
			if(tf_ID.getText().equals("admin") && tf_PW.getText().equals("1234")) {
				JOptionPane.showMessageDialog( // 메시지창 출력
						this, "환영합니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
				
				new Rental_DB("대여", 900, 600); //메인 없는 관계로 이단 대여로 지정
			}
			
			else{
				JOptionPane.showMessageDialog( // 메시지창 출력
						this, "아이디 또는 비밀번호를 확인하세요.", "메시지", JOptionPane.ERROR_MESSAGE);
			}
		} 
	}
}
