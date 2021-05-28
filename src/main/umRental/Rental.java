package main.umRental;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import main.DB;
import main.style.BtnFont;

public class Rental extends JFrame implements ActionListener {
	private JPanel pCenter, pBottom, pBtn, pExit;
	private JButton btnF5, btnRental, btnModify, btnOk, btnReturn, btnOut, btnExit;
	private Vector<String> vectorTitle;
	private DefaultTableModel model;
	private Vector<String> data;
	private String[] tmp, value;
	private JTable table;
	private String rentalID, umbreallaID, studentID, studentName, rentalDATE, returndueDATE;

	public Rental(String title, int width, int height) {
		setUndecorated(true); //타일트바 없애기
		this.setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);
		dispose();
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 레이아웃
		setLayout(new BorderLayout());
		// setResizable(false); // 실행후 화면크기 변경 불가

		setTable();
		setBottom();

		this.setVisible(true);
	}

	private void setTable() {
		// 패널설정
		pCenter = new JPanel();
		pCenter.setLayout(new FlowLayout(FlowLayout.LEFT));
		pCenter.setBackground(Color.WHITE);
		pCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0)); // 패널마진
		add(pCenter, BorderLayout.CENTER); // 프레임에 패널 가운데에 붙임

		vectorTitle = new Vector<String>(); // 헤더 값

		vectorTitle.addElement("대여번호");
		vectorTitle.addElement("우산코드");
		vectorTitle.addElement("학번");
		vectorTitle.addElement("이름");
		vectorTitle.addElement("대여일");
		vectorTitle.addElement("반납예정일");

		model = new DefaultTableModel(vectorTitle, 0) { // 테이블의 데이터 변경하려면 모델 사용

			public boolean isCellEditable(int r, int c) {
				return false;
			}

		};

		// DB
		String sql = "SELECT r.RENTALID , um.UMBRELLAID , st.STUDENTID, st.NAME , r.RENTALDATE , r.RETURNDUEDATE "
				+ "FROM RENTAL r, UMBRELLA um, STUDENT st "
				+ "WHERE r.UMBRELLAID = um.UMBRELLAID AND st.STUDENTID = r.STUDENTID " + "ORDER BY r.RENTALID";
		// 대여의 대여아이디, 우산의 우산 아이디, 학생의 학생아이디, 학생의 이름, 대여의 대여일, 대여의 반납예정일을 출력
		// 대여의 우산아이디와 우산의 우산아이디 && 학생의 학생아이디와 대여의 학생아이디가 같을때
		// 대여아이디로 정렬

		ResultSet rs = DB.getResultSet(sql); // 쿼리 넘기기

		try {
			while (rs.next()) {
				data = new Vector<String>();

				rentalID = rs.getString(1); // DB의 첫번째를 rentalID를 넣음
				umbreallaID = rs.getString(2);
				studentID = rs.getString(3);
				studentName = rs.getString(4);

				rentalDATE = rs.getString(5);
				returndueDATE = rs.getString(6);

				// System.out.println(rentalID + "\t" + umbreallaID + "\t" + studentID + "\t" +
				// rentalDATE +"\t"+ returndueDATE);
				data.add(0, rentalID);
				data.add(1, umbreallaID);
				data.add(2, studentID);
				data.add(3, studentName);
				data.add(4, rentalDATE);
				data.add(5, returndueDATE);

				model.addRow(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("DB연결 성공");

		// 테이블
		table = new JTable(model); // 테이터 변경 시 테이블에 직접 접근하지 않고 변경
		table.getTableHeader().setReorderingAllowed(false); // 테이블 컬럼의 이동을 방지
		// table.getTableHeader().setResizingAllowed(false); // 테이블 컬럼의 사이즈를 고정
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 테이블 로우를 한개만 선택가능
		table.setFillsViewportHeight(true); // 스크롤 팬 안에 테이블 꽉차게 표시 -> 이거 없으면 배경색 설정 안됨
		table.setBackground(Color.white); // 테이블 배경색 지정

		JTableHeader tableHeader = table.getTableHeader(); // 테이블 헤더 값 가져오기
		tableHeader.setBackground(new Color(0xB2CCFF)); // 테이블헤더 배경색 지정

		// 스크롤팬을 사용하지 않으면 컬럼명을 볼 수 없음
		JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(850, 480)); // 테이블 크기를 줄려면 JScroollPane의 크기를 변경
		pCenter.add(sp);

		// 테이블 내용 가운데정렬
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcmSchedule = table.getColumnModel();

		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
	}

	private void setBottom() {
		// Bottom 전체 패널
		pBottom = new JPanel();
		pBottom.setLayout(new BorderLayout());
		pBottom.setBackground(Color.WHITE); // 배경색
		pBottom.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 0)); // 패널 마진
		add(pBottom, BorderLayout.SOUTH); // 남쪽 정렬

		// Bottom의 WEST패널
		pBtn = new JPanel();
		pBtn.setLayout(new FlowLayout(FlowLayout.LEFT));
		pBtn.setBackground(Color.WHITE);
		pBottom.add(pBtn, BorderLayout.WEST);

		// 새로고침 버튼
		btnF5 = new JButton("새로고침");
		BtnFont.BtnStyle(btnF5);
		pBtn.add(btnF5);

		// 대여버튼
		btnRental = new JButton("대여");
		BtnFont.BtnStyle(btnRental);
		btnRental.addActionListener(this);
		pBtn.add(btnRental);

		// 수정버튼
		btnModify = new JButton("수정");
		BtnFont.BtnStyle(btnModify);
		btnModify.addActionListener(this);
		pBtn.add(btnModify);

		// 반납버튼
		btnReturn = new JButton("반납");
		BtnFont.BtnStyle(btnReturn);
		btnReturn.addActionListener(this);
		pBtn.add(btnReturn);

		// 차단버튼
		btnOut = new JButton("차단");
		BtnFont.BtnStyle(btnOut);
		btnOut.addActionListener(this);
		pBtn.add(btnOut);

		// Bottom의 EAST 패널
		pExit = new JPanel();
		pExit.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pExit.setBackground(Color.WHITE);
		pExit.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
		add(pExit, BorderLayout.NORTH);
		//pBottom.add(pExit, BorderLayout.EAST);

		// 닫기버튼
		ImageIcon icon = new ImageIcon("libs/KakaoTalk_20210528_191417857.png");
		Image changeIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon btnIcon = new ImageIcon(changeIcon);
		
		btnExit = new JButton(btnIcon);
		btnExit.setAlignmentX(JButton.RIGHT); //오른쪽 정렬
		btnExit.setContentAreaFilled(false); //버튼 내용영역 채우기 x
		btnExit.setFocusPainted(false); 
		btnExit.setBorderPainted(false); // 버튼 테두리 없애기
		
		btnExit.addActionListener(this);
		pExit.add(btnExit);
	}

	public static void main(String[] args) {
		DB.init(); //DB클래스의 init()메소드 사용
		new Rental("대여", 900, 700);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnRental) {
			new Rentalform("대여", 300, 300);
		} else if (obj == btnModify) {
			new Rental_ModifyBtn("수정", 300, 300);
		} else if (obj == btnReturn) {
			JOptionPane.showMessageDialog( // 메시지창 출력
					this, "000님의 우산이 반납처리되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
		} else if (obj == btnOut) {

		} else if (obj == btnExit) {
			dispose();
		}
	}
}