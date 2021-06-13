package main.umRental;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import main.DB;
import main.style.BtnFont;

public class Rental_ModifyBtn extends JFrame implements ActionListener {
	private JButton btn_ok, btn_cancel;
	private JTextField tf_Umbcode, tf_Code;
	private JPanel pBase, pTop, pCenter, pBottom;
	private Rental rental;

	public Rental_ModifyBtn(String title, int width, int height, Rental rental) {
		this.rental = rental;
		setUndecorated(true); // 타이틀바 없애기
		this.setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dispose();

		pBase = new JPanel();
		pBase.setLayout(new BorderLayout());
		pBase.setBorder(new LineBorder(Color.GRAY, 2)); // 패널 테두리
		add(pBase);

		setTop();
		setCenter();
		setBottom();

		// 레이아웃
		setLayout(new BorderLayout());
	}

	private void setTop() {
		pTop = new JPanel();
		pTop.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15)); // 패널 flowlayout, vgap
		pTop.setBackground(new Color(0xB2CCFF));
		pBase.add(pTop, BorderLayout.NORTH);

		JLabel lbl1 = new JLabel("수 정");
		lbl1.setForeground(Color.BLACK); // JLabel글자 색 변경
		lbl1.setFont(new Font("HY헤드라인M", Font.PLAIN, 15));
		pTop.add(lbl1);
	}

	private void setCenter() {
		pCenter = new JPanel();
		pCenter.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));
		pCenter.setBackground(Color.WHITE);
		pCenter.setLayout(new GridLayout(2, 2, 0, 40));
		pBase.add(pCenter, BorderLayout.CENTER);

		JLabel lbl_Code = new JLabel("학번 :");
		lbl_Code.setFont(new Font("HY헤드라인M", Font.PLAIN, 15));
		pCenter.add(lbl_Code);

		tf_Code = new JTextField();
		pCenter.add(tf_Code);

		JLabel lbl_UmbCode = new JLabel("우산번호 :");
		lbl_UmbCode.setFont(new Font("HY헤드라인M", Font.PLAIN, 15));
		pCenter.add(lbl_UmbCode);

		tf_Umbcode = new JTextField();
		pCenter.add(tf_Umbcode);
	}

	public JTextField getTf_Umbcode() {
		return tf_Umbcode;
	}

	public JTextField getTf_Code() {
		return tf_Code;
	}

	private void setBottom() {
		pBottom = new JPanel();
		pBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		pBottom.setBackground(Color.WHITE);
		pBase.add(pBottom, BorderLayout.SOUTH);

		btn_ok = new JButton("수정");
		btn_ok.addActionListener(this);
		BtnFont.BtnStyle(btn_ok);
		pBottom.add(btn_ok);

		btn_cancel = new JButton("취소");
		btn_cancel.addActionListener(this);
		BtnFont.BtnStyle(btn_cancel);
		pBottom.add(btn_cancel);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		//new Rental_ModifyBtn("수정", 300, 300);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btn_ok) {
			String code = tf_Code.getText();
			String umcode = tf_Umbcode.getText();
			String getId = rental.getRentalId();
			
//미안하다,,, 미래의 김민솔 중복 힘내라	
			
			
			if (tf_Code.getText() == "1"/* 사실 1은 아니고 만약 중복된다면 */) {
				JOptionPane.showMessageDialog( // 메시지창 출력
						this, "중복된 아이디가 있습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
			} else {
				String sql = "UPDATE RENTAL SET UMBRELLAID = '" + umcode + "'," + "STUDENTID = '" + code + "' WHERE RENTALID = '" + getId + "'";
				ResultSet rs = DB.getResultSet(sql); // 쿼리 넘기기
				DB.executeQuery(sql); // DB 내용 수정

				dispose(); //현재 수정창 끄기
				JOptionPane.showMessageDialog( // 메시지창 출력
						this, "처리가 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
				
				// 새로고침
				rental.getpCenter().removeAll(); // 패널 지우기
				rental.setTable(); // 테이블 호출
				rental.getpCenter().revalidate(); // 레이아웃 변화 재확인
				rental.getpCenter().repaint(); // 레이아웃 다시 가져오기
			}

		} else if (obj == btn_cancel) {
			dispose(); // 취소 버튼 누르면 화면 종료
		}

	}
}
