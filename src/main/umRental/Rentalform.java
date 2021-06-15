package main.umRental;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

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

public class Rentalform extends JFrame implements ActionListener {
	private JButton btn_ok, btn_cancel;
	private JTextField tf_Umbcode, tf_Code;
	private JPanel pBase, pTop, pCenter, pBottom;
	private String max;
	private static String sysdate;
	private static String sysduedate;
	private static String sqlsysduedate;

	public Rentalform(String title, int width, int height) {
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
	}

	private void setTop() {
		// Top패널
		pTop = new JPanel();
		pTop.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15)); // 패널 flowlayout, vgap
		pTop.setBackground(new Color(0xB2CCFF));
		pBase.add(pTop, BorderLayout.NORTH);

		// 대여 라벨
		JLabel lbl1 = new JLabel("대 여");
		lbl1.setFont(new Font("HY헤드라인M", Font.PLAIN, 15));
		lbl1.setForeground(Color.BLACK); // JLabel글자 색 변경
		pTop.add(lbl1);
	}

	private void setCenter() {
		// CENTER 패널 - GridLayout
		pCenter = new JPanel();
		pCenter.setBorder(BorderFactory.createEmptyBorder(20, 10, 25, 10));
		pCenter.setBackground(Color.WHITE);
		pCenter.setLayout(new GridLayout(2, 2, 0, 40));
		pBase.add(pCenter, BorderLayout.CENTER);

		// 학번
		JLabel lbl_Code = new JLabel("학번 :");
		lbl_Code.setFont(new Font("HY헤드라인M", Font.PLAIN, 15));
		pCenter.add(lbl_Code);

		tf_Code = new JTextField();
		pCenter.add(tf_Code);

		// 우산
		JLabel lbl_UmbCode = new JLabel("우산번호 :");
		lbl_UmbCode.setFont(new Font("HY헤드라인M", Font.PLAIN, 15));
		pCenter.add(lbl_UmbCode);

		tf_Umbcode = new JTextField();
		pCenter.add(tf_Umbcode);
	}

	private void setBottom() {
		pBottom = new JPanel();
		pBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		pBottom.setBackground(Color.WHITE);
		pBase.add(pBottom, BorderLayout.SOUTH);

		btn_ok = new JButton("대여");
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
		DB.init();
		new Rentalform("대여", 300, 300);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String code = new String();
		code = tf_Code.getText();

		String umbCode = new String();
		umbCode = tf_Umbcode.getText();
		
		// 대여 아이디 자동으로 가장 큰 값 넣어주기 위해서 대여 아이디의 최대값을 구한 후 +1 증가
		String sqlMax = "SELECT MAX(RENTALID) +1  FROM RENTAL r ";
		ResultSet rsMax = DB.getResultSet(sqlMax); // 쿼리 넘기기
		try {
			rsMax.next(); //getString이전에 이것을 써야 ResultSet.next호출되지 않았다고 오류가 안뜸
			max = rsMax.getString(1);
			System.out.println(max);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		Object obj = e.getSource();

		if (obj == btn_ok) {
			if (!tf_Umbcode.getText().equals("") && !tf_Code.getText().equals("")) {
				// 모든 항목 입력시 확인 버튼 클릭하면 저장되게
				if (tf_Code.getText().equals("1") /* 사실 1은 아니고 만약 중복된다면 */) {
					JOptionPane.showMessageDialog( // 메시지창 출력
							this, "중복된 아이디가 있습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);

				} else {

					String sql = "INSERT INTO RENTAL VALUES('" + max +"', '" + umbCode + "', '" + code + "', TO_DATE( SYSDATE, 'YYYY-MM-DD'), TO_DATE( SYSDATE + 14, 'YYYY-MM-DD'), 'N')";

					DB.executeQuery(sql); // DB에 sql 추가
					System.out.println(sql);
					JOptionPane.showMessageDialog( // 메시지창 출력
							this, "처리가 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
			}
		} else if (obj == btn_cancel) {
			dispose(); // 취소 버튼 누르면 화면 종료
		}
	}
}