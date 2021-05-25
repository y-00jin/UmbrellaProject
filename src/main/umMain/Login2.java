package main.umMain;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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

import main.umRental.Rental_DB;

public class Login2 extends JFrame implements ActionListener {

	private JPanel p0, p1, p3;
	private JLabel lbl_ID, lbl_PW;
	private JTextField tf_ID, tf_PW;
	private JButton btn_login, btn_exit;

	public Login2() { // 생성자
		setTitle("관리자 로그인");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dispose();
		setSize(900, 700);		
		setLocationRelativeTo(this);
		//setResizable(false); // 프레임 창 사이즈 고정

		// 이미지 레이블 생성
		p0 = new JPanel();
		p0.setLayout(new GridLayout(1, 1, 0, 0));
		p0.setBackground(Color.WHITE);
		p0.setBorder(BorderFactory.createEmptyBorder(30, 300, 0, 300));
		add(p0, BorderLayout.NORTH);
		
		ImageIcon icon = new ImageIcon("libs/user.png");
		JLabel imageLabel = new JLabel(icon);
		imageLabel.setHorizontalAlignment(JLabel.CENTER);
		p0.add(imageLabel);
//		
		p1 = new JPanel();
		p1.setBackground(Color.WHITE);
		p1.setLayout(new GridLayout(6, 1, 0, 20));

	//	p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
		p1.setBorder(BorderFactory.createEmptyBorder(0, 300, 30, 300));
		add(p1, BorderLayout.CENTER);
		
		lbl_ID = new JLabel("ID ");
		lbl_ID.setFont(new Font("", Font.BOLD, 20));
		p1.add(lbl_ID);
		
		tf_ID = new JTextField(); // 창의 열 개수 10
		p1.add(tf_ID);
		
		lbl_PW = new JLabel("PW ");
		lbl_PW.setFont(new Font("", Font.BOLD, 20));
		p1.add(lbl_PW);
		
		tf_PW = new JTextField(); // 창의 열 개수 10
		p1.add(tf_PW);
		
//		p3 = new JPanel();
//		p3.setBackground(Color.WHITE);
//		p3.setLayout(new GridLayout(2, 1, 0, 30));
//	//	p1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
//		p3.setBorder(BorderFactory.createEmptyBorder(30, 300, 30, 300));
//		add(p3, BorderLayout.SOUTH);
		
		btn_login = new JButton("로그인");
		btn_login.setBackground(new Color(0xD4F4FA));
		btn_login.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 15));
		
		btn_login.addActionListener(this);
		p1.add(btn_login);
		
		btn_exit = new JButton("종료");
		btn_exit.setBackground(new Color(0xD4F4FA));
		btn_exit.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 15));
		btn_exit.addActionListener(this);
		p1.add(btn_exit);
		
		setVisible(true);
	}

	public static void main(String[] args) {
		new Login2();
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
