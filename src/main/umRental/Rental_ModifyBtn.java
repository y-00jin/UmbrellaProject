package main.umRental;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

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
	private static Vector<String> data;

	public Rental_ModifyBtn(String title, int width, int height) {
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
		new Rental_ModifyBtn("수정", 300, 300);
		
		
	}
	public void executeSelectQuery(PreparedStatement pstmt, Vector rthValue, int nRenturn) throws Exception{
		String sqlAgoStudentId = "SELECT STUDENTID " 
				+ "FROM RENTAL";
		ResultSet rsStudent = DB.getResultSet(sqlAgoStudentId); // 쿼리 넘기기
		DB.executeQuery(sqlAgoStudentId); 
		
		try {
			while (rsStudent.next()) {
				for(int i = 1; i <= nRenturn; i++)
				data.addElement(rsStudent.getString(i));
				System.out.println(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btn_ok) {
			String studentCode = tf_Code.getText();
			String umcode = tf_Umbcode.getText();
			String getId = rental.getRentalId();
			
			// 우산 상태 뽑아오기
			String sqlAgoUmbId =  "SELECT STATE "
					+ "FROM DODAM.UMBRELLA "
					+ "WHERE UMBRELLAID LIKE '" + umcode +"'";
			String agoUmbState = "";
			ResultSet rsUm = DB.getResultSet(sqlAgoUmbId); //쿼리 넘기기
			
			try {
				rsUm.next(); //getString이전에 이것을 써야 ResultSet.next호출되지 않았다고 오류가 안뜸
				agoUmbState = rsUm.getString(1);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			// 학번 뽑아오기 - 대여 테이블에 입력한 학번이 있는지 
			// -> 학번이 있고 대여상태가 N이면 미반납자, 학번이 있고 대여상태가 Y이면 예전이 대여하고 반납한 사람
//			String sqlAgoStudentId = "SELECT STUDENTID " 
//					+ "FROM RENTAL";
//			
//			ResultSet rsStudent = DB.getResultSet(sqlAgoStudentId); // 쿼리 넘기기
//
//			try {
//				while (rsStudent.next()) {
//					data = new Vector<String>();
//					data.add(sqlAgoStudentId);
//					System.out.println(data);
//				}
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
			
			// 미반납자에 있는 학번, 현재 대여중인 학번
			// 대여를 하면 반납 상태가 N, 반납을 하면 Y
			// 이미 반납한 학생은 반납상태가 Y,
			// 현재 대여중(+미반납자)이면 지금 대여 테이블에 학번이 있으니까
			// 중복 처리할때 반납상태가 Y이거나 대여테이블에 학번이 없을경우로 하면 될듯
			// while문으로 돌려야 하나,,,
			
//			if (!agoStudentId.equals(null))/* 사실 1은 아니고 만약 중복된다면 */) {
//				JOptionPane.showMessageDialog( // 메시지창 출력
//						this, "중복된 아이디가 있습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
//			} else 
				if (agoUmbState.equals("Y")){ // 이미 대여한 우산일 경우
				JOptionPane.showMessageDialog( // 메시지창 출력
						this, "이미 대여중인 우산입니다.", "메시지", JOptionPane.WARNING_MESSAGE);
			} else {
				String sql = "UPDATE RENTAL SET UMBRELLAID = '" + umcode + "'," + "STUDENTID = '" + studentCode + "' WHERE RENTALID = '" + getId + "'";
				ResultSet rs = DB.getResultSet(sql); // 쿼리 넘기기
				DB.executeQuery(sql); // DB 내용 수정

				dispose(); //현재 수정창 끄기
				JOptionPane.showMessageDialog( // 메시지창 출력
						this, "처리가 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
				rental.rentalTable(); // 새로고침
			}

		} else if (obj == btn_cancel) {
			dispose(); // 취소 버튼 누르면 화면 종료
		}

	}
}
