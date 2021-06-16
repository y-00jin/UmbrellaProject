package main.umMain;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import main.DB;

public class LoginForm extends JFrame implements ActionListener {

	private JPanel p0, p1, p3, pbase;
	private JLabel lbl_ID, lbl_PW;
	private JTextField tf_ID;
	private JPasswordField tf_PW;
	private JButton btn_login, btn_exit;

	public LoginForm() { // 생성자
		setUndecorated(true); // 타일트바 없애기
		setTitle("관리자 로그인");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dispose();
		setSize(900, 700);
		setLocationRelativeTo(this);
		// setResizable(false); // 프레임 창 사이즈 고정

		pbase = new JPanel();
		pbase.setLayout(new BorderLayout());
		pbase.setBorder(new LineBorder(Color.GRAY, 1)); // 패널 테두리
	    add(pbase);
		
		// 이미지 레이블 생성
		p0 = new JPanel();
		p0.setLayout(new GridLayout(1, 1, 0, 0));
		p0.setBackground(Color.WHITE);
		p0.setBorder(BorderFactory.createEmptyBorder(50, 300, 0, 300));
		pbase.add(p0, BorderLayout.NORTH);

		ImageIcon icon = new ImageIcon("libs/loginImg.png");
		JLabel imageLabel = new JLabel(icon);
		imageLabel.setHorizontalAlignment(JLabel.CENTER);
		p0.add(imageLabel);

		p1 = new JPanel();
		p1.setBackground(Color.WHITE);
		p1.setLayout(new GridLayout(6, 1, 0, 20));

		p1.setBorder(BorderFactory.createEmptyBorder(100, 300, 100, 300));
		pbase.add(p1, BorderLayout.CENTER);

		lbl_ID = new JLabel("ID ");
		lbl_ID.setFont(new Font("HY헤드라인M", Font.BOLD, 20));
		p1.add(lbl_ID);

		tf_ID = new JTextField(); // 창의 열 개수 10
		p1.add(tf_ID);

		lbl_PW = new JLabel("PW ");
		lbl_PW.setFont(new Font("HY헤드라인M", Font.BOLD, 20));
		p1.add(lbl_PW);

		tf_PW = new JPasswordField();
		tf_PW.setEchoChar('*'); // 텍스트필드에 입력한 값 안보이고 *로 표시됨
		tf_PW.addActionListener(this);
		p1.add(tf_PW);

		btn_login = new JButton("로그인");
		btn_login.setBackground(new Color(0xB2CCFF));
		btn_login.setFont(new Font("HY헤드라인M", Font.PLAIN, 15));

		btn_login.addActionListener(this);
		p1.add(btn_login);

		btn_exit = new JButton("종료");
		btn_exit.setBackground(new Color(0xB2CCFF));
		btn_exit.setFont(new Font("HY헤드라인M", Font.PLAIN, 15));
		btn_exit.addActionListener(this);
		p1.add(btn_exit);

		setVisible(true);
	}

	public static void main(String[] args) {
		DB.init();
		new LoginForm();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btn_exit) {
			dispose();
		} else if (obj == btn_login || obj == tf_PW) {
			if (!tf_ID.getText().equals("") && !tf_PW.getText().equals("")) {
				if (tf_ID.getText().equals("admin") && tf_PW.getText().equals("1234")) {
					JOptionPane.showMessageDialog( // 메시지창 출력
							this, "환영합니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);

					new UmbrellaMain("우산 대여 프로그램", 900, 600); // 메인 프로그램 불려옴
					dispose();
				} 
			}

			else {
				JOptionPane.showMessageDialog( // 메시지창 출력
						this, "아이디 또는 비밀번호를 확인하세요.", "메시지", JOptionPane.ERROR_MESSAGE);
				tf_ID.setText(""); // 실패했으니까 빈칸으로 만들어줌
				tf_PW.setText("");
				tf_ID.requestFocus(); // ID 바로 다시 칠 수 있게 포커스 이동해줌
			}
		}
	}
}
