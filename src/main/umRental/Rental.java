package main.umRental;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import main.DB;
import main.style.BtnFont;
import main.style.hint;

public class Rental extends JFrame implements ActionListener, MouseListener {
	private JPanel pBase, pCenter, pBottom, pTitle;
	private JButton btnF5, btnRental, btnDelete, btnReturn, btnExit, btnSearch;
	private Vector<String> vectorTitle;
	private DefaultTableModel model;
	private Vector<String> data;
	private JTable table;
	private String rentalID, umbreallaID, studentID, studentName, rentalDATE, returndueDATE;
	private String rentalId, umbcode, code, max, count;
	private int row = -1;
	private JLabel lblLogo;
	private hint tfSearch;
	private Vector<String> con;
	private String serRentalID, serUmbreallaID, serStudentID, serStudentName, serrentalDATE, serRentalDATE,
			serReturndueDATE;
	private JTable studentTable;

	public Rental(String title, int width, int height) {
		this.setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);
		dispose();
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 레이아웃
		pBase = new JPanel(); // 전체 패널
		pBase.setLayout(new BorderLayout());
		pBase.setBorder(new LineBorder(Color.GRAY, 1)); // 패널 테두리
		add(pBase);

		// setResizable(false); // 실행후 화면크기 변경 불가

		setTop();
		setTable();
		setBottom();

		this.setVisible(true);
	}

	private void setTop() {
		// 상단 패널
		pTitle = new JPanel();
		// pExit.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pTitle.setLayout(new FlowLayout(FlowLayout.LEFT));
		pTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0)); // 패널 마진
		pTitle.setBackground(Color.white); // 패널 배경색

		ImageIcon icontitle = new ImageIcon("libs/폼로고.jpg");
		Image changeIcon = icontitle.getImage().getScaledInstance(450, 50, Image.SCALE_SMOOTH);
		ImageIcon lblIcontitle = new ImageIcon(changeIcon);

		lblLogo = new JLabel(lblIcontitle);
		pTitle.add(lblLogo);
		pBase.add(pTitle, BorderLayout.NORTH);

	}

	public void setTable() {
		// 패널설정
		pCenter = new JPanel();
		pCenter.setLayout(new FlowLayout(FlowLayout.LEFT));
		pCenter.setBackground(Color.WHITE);
		pCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0)); // 패널 마진
		pBase.add(pCenter, BorderLayout.CENTER); // 프레임에 패널 가운데에 붙임

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
		String sql = "SELECT r.RENTALID , um.UMBRELLAID , st.STUDENTID, st.NAME , TO_CHAR(r.RENTALDATE, 'YYYY-MM-DD'), TO_CHAR(r.RETURNDUEDATE, 'YYYY-MM-DD')"
				+ "FROM RENTAL r, UMBRELLA um, STUDENT st "
				+ "WHERE r.UMBRELLAID = um.UMBRELLAID AND st.STUDENTID = r.STUDENTID AND r.RETURNSTATE LIKE 'N'"
				+ "ORDER BY r.RENTALDATE";
		// 대여의 대여아이디, 우산의 우산 아이디, 학생의 학생아이디, 학생의 이름, 대여의 대여일, 대여의 반납예정일, 반납상태를 출력
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

		// 테이블
		table = new JTable(model); // 테이터 변경 시 테이블에 직접 접근하지 않고 변경
		table.getTableHeader().setReorderingAllowed(false); // 테이블 컬럼의 이동을 방지
		// table.getTableHeader().setResizingAllowed(false); // 테이블 컬럼의 사이즈를 고정
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 테이블 로우를 한개만 선택가능
		table.setFillsViewportHeight(true); // 스크롤 팬 안에 테이블 꽉차게 표시 -> 이거 없으면 배경색 설정 안됨
		table.setBackground(Color.white); // 테이블 배경색 지정

		table.addMouseListener(this);
		JTableHeader tableHeader = table.getTableHeader(); // 테이블 헤더 값 가져오기
		tableHeader.setBackground(new Color(0xB2CCFF)); // 테이블헤더 배경색 지정

		// 스크롤팬을 사용하지 않으면 컬럼명을 볼 수 없음
		JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(860, 410)); // 테이블 크기를 줄려면 JScroollPane의 크기를 변경
		pCenter.add(sp);

		// 테이블 내용 가운데정렬
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcmSchedule = table.getColumnModel();

		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}

	}

	public JTable getTable() {
		return table;
	}

	public JPanel getpCenter() {
		return pCenter;
	}

	private void setBottom() {
		// Bottom 전체 패널
		pBottom = new JPanel();
		pBottom.setLayout(new FlowLayout(FlowLayout.LEFT));
		pBottom.setBackground(Color.WHITE); // 배경색
		pBottom.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 0)); // 패널 마진
		pBase.add(pBottom, BorderLayout.SOUTH); // 남쪽 정렬

		// 검색 텍스트박스
		tfSearch = new hint("학번을 입력하세요"); // hint
		tfSearch.addActionListener(this);
		pBottom.add(tfSearch);

		// 검색 버튼
		btnSearch = new JButton("검색");
		BtnFont.BtnStyle(btnSearch);
		btnSearch.addActionListener(this);
		pBottom.add(btnSearch);

		JLabel lblSpace = new JLabel("                                                                                                                                 ");
		pBottom.add(lblSpace);

		// 새로고침 버튼
		btnF5 = new JButton("새로고침");
		BtnFont.BtnStyle(btnF5);
		btnF5.addActionListener(this);
		pBottom.add(btnF5);

		// 대여버튼
		btnRental = new JButton("대여");
		BtnFont.BtnStyle(btnRental);
		btnRental.addActionListener(this);
		pBottom.add(btnRental);

		// 삭제버튼
		btnDelete = new JButton("삭제");
		BtnFont.BtnStyle(btnDelete);
		btnDelete.addActionListener(this);
		pBottom.add(btnDelete);

		// 반납버튼
		btnReturn = new JButton("반납");
		BtnFont.BtnStyle(btnReturn);
		btnReturn.addActionListener(this);
		pBottom.add(btnReturn);
	}

	public static void main(String[] args) {
		DB.init(); // DB클래스의 init()메소드 사용
		new Rental("대여", 900, 600);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnRental) {
			// ---------------------------------- 대 여 --------------------------------------
			new Rentalform("대여", 300, 300, this);

		} else if (obj == btnDelete) {
			// ---------------------------------- 삭 제 --------------------------------------
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "삭제할 목록을 선택해주세요.", "경고 메시지", JOptionPane.WARNING_MESSAGE);
			} else {
				int result = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);

				if (result == JOptionPane.YES_OPTION) {
					String sqlDelete = "DELETE FROM DODAM.RENTAL " + "WHERE RENTALID='" + rentalId + "'";
					ResultSet rs = DB.getResultSet(sqlDelete); // 쿼리 넘기기
					DB.executeQuery(sqlDelete); // DB 내용 수정

					// 삭제한 우산 상태 N로 변경
					String sqlStateModify = "UPDATE UMBRELLA " + "SET STATE='N'" + "WHERE UMBRELLAID='" + umbcode + "'";
					ResultSet rsStateModify = DB.getResultSet(sqlStateModify); // 쿼리 넘기기
					DB.executeQuery(sqlStateModify); // DB 내용 수정

					rentalTable(); // 새로고침
					// 메시지창 출력
					JOptionPane.showMessageDialog(this, "삭제가 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
				}
			}

		} else if (obj == btnReturn) {
			// ---------------------------------- 반 납 --------------------------------------
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "반납할 목록을 선택해주세요.", "경고 메시지", JOptionPane.WARNING_MESSAGE);
			} else {

				String sqlCount = "SELECT count(*) from return";
				ResultSet rsCount = DB.getResultSet(sqlCount);
				try {
					rsCount.next(); // getString이전에 이것을 써야 ResultSet.next호출되지 않았다고 오류가 안뜸
					count = rsCount.getString(1);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (count.equals("0")) {
					String sqlReturn = "INSERT INTO RETURN (RETURNID, RENTALID, RETURNDATE) " + "VALUES('1', '"
							+ rentalId + "', TO_DATE( SYSDATE))";
					DB.executeQuery(sqlReturn); // DB에 sqlReturn 추가

					// 반납된 대여아이디의 반납 상태를 Y로 바꿔줌 -> 대여테이블에서 보여지지 않음
					String sqlStateModify = "UPDATE RENTAL " + "SET RETURNSTATE='Y'" + "WHERE RENTALID='" + rentalId
							+ "'";
					ResultSet rsStateModify = DB.getResultSet(sqlStateModify); // 쿼리 넘기기
					DB.executeQuery(sqlStateModify); // DB 내용 수정

					// 반납된 우산상태를 N으로 바꿔줌 -> 다시 대여 가능하게
					String sqlUmbStateModify = "UPDATE UMBRELLA " + "SET STATE='N'" + "WHERE UMBRELLAID='" + umbcode
							+ "'";
					ResultSet rsUmbStateModify = DB.getResultSet(sqlUmbStateModify); // 쿼리 넘기기
					DB.executeQuery(sqlUmbStateModify); // DB 내용 수정

					rentalTable(); // 테이블 새로고침

					// 반납한 사람의 이름 알아오기
					String sqlName = "SELECT NAME " + "FROM STUDENT " + "WHERE STUDENTID LIKE '" + code + "'";
					String findName = "";
					ResultSet rsFindName = DB.getResultSet(sqlName); // 쿼리 넘기기
					try {
						rsFindName.next(); // getString이전에 이것을 써야 ResultSet.next호출되지 않았다고 오류가 안뜸
						findName = rsFindName.getString(1);

					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					// 메시지창 출력
					JOptionPane.showMessageDialog(this, findName + "님의 우산이 반납처리되었습니다.", "메시지",
							JOptionPane.INFORMATION_MESSAGE);

				}
				// 대여 아이디 자동으로 가장 큰 값 넣어주기 위해서 대여 아이디의 최대값을 구한 후 +1 증가
				String sqlMax = "SELECT MAX(RETURNID) +1  FROM RETURN ";
				ResultSet rsMax = DB.getResultSet(sqlMax); // 쿼리 넘기기
				try {
					if (rsMax.next()) {// getString이전에 이것을 써야 ResultSet.next호출되지 않았다고 오류가 안뜸
						max = rsMax.getString(1);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				setReturn();

			}
		} else if (obj == btnExit) {
			dispose();

		} else if (obj == btnF5) {
			rentalTable();
		} else if (obj == btnSearch | obj == tfSearch) {
			// ---------------------------------- 검 색 --------------------------------------
			String findStudentId = tfSearch.getText();
			System.out.println(findStudentId);

			String sqlfindStudentId = "SELECT RENTALID, UMBRELLAID, STUDENTID, TO_CHAR(RENTALDATE, 'YYYY-MM-DD'), TO_CHAR(RETURNDUEDATE, 'YYYY-MM-DD') "
					+ "FROM RENTAL " + "WHERE STUDENTID LIKE '" + findStudentId + "'";

			ResultSet rsSt = DB.getResultSet(sqlfindStudentId);
			String findSI = "";

			try {
				if (rsSt.next()) {
					findSI = rsSt.getString(1);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			System.out.println(rsSt);

			if (findStudentId.equals(findSI)) {
				getpCenter().removeAll();
				model.setNumRows(0);

				String sql = "SELECT RENTALID, UMBRELLAID, STUDENTID, TO_CHAR(RENTALDATE, 'YYYY-MM-DD'), TO_CHAR(RETURNDUEDATE, 'YYYY-MM-DD') "
						+ "FROM RENTAL " + "WHERE STUDENTID LIKE '" + findStudentId + "'";
				ResultSet rs = DB.getResultSet(sql);

				try {
					while (rs.next()) {
						con = new Vector<String>();
						serRentalID = rs.getString(1); // DB의 첫번째를 rentalID를 넣음
						serUmbreallaID = rs.getString(2);
						serStudentID = rs.getString(3);
						serStudentName = rs.getString(4);

						serRentalDATE = rs.getString(5);
						serReturndueDATE = rs.getString(6);

						con.add(0, serRentalID);
						con.add(1, serUmbreallaID);
						con.add(2, serStudentID);
						con.add(3, serStudentName);
						con.add(4, serRentalDATE);
						con.add(5, serReturndueDATE);
						model.addRow(con); // 테이블에 내용 추가
					}
				} catch (SQLException e2) {
					System.out.println("접속 오류 / SQL 오류");
					e2.printStackTrace();
				}

				table = new JTable(model);
				
				table.getTableHeader().setReorderingAllowed(false); // 테이블 컬럼의 이동을 방지
				// table.getTableHeader().setResizingAllowed(false); // 테이블 컬럼의 사이즈를 고정
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 테이블 로우를 한개만 선택가능
				table.setFillsViewportHeight(true); // 스크롤 팬 안에 테이블 꽉차게 표시 -> 이거 없으면 배경색 설정 안됨
				table.setBackground(Color.white); // 테이블 배경색 지정

				table.addMouseListener(this);
				JTableHeader tableHeader = table.getTableHeader(); // 테이블 헤더 값 가져오기
				tableHeader.setBackground(new Color(0xB2CCFF)); // 테이블헤더 배경색 지정

				// 스크롤팬을 사용하지 않으면 컬럼명을 볼 수 없음
				JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				sp.setPreferredSize(new Dimension(860, 410)); // 테이블 크기를 줄려면 JScroollPane의 크기를 변경
				pCenter.add(sp);

				// 테이블 내용 가운데정렬
				DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
				tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				TableColumnModel tcmSchedule = table.getColumnModel();

				for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
					tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
				}

				getpCenter().revalidate(); // 레이아웃 변화 재확인
				getpCenter().repaint(); // 레이아웃 다시 가져오기
				
			} else if (findSI.equals("")) {
				JOptionPane.showMessageDialog(null, "검색 결과가 존재하지 않습니다.", "검색 결과", JOptionPane.PLAIN_MESSAGE);

				model.setNumRows(0);
			}
		}
	}

	private void setReturn() {
		// 선택한 행의 정보를 반납테이블에 현재날짜를 반납아이디로 추가
		String sqlReturn = "INSERT INTO RETURN (RETURNID, RENTALID, RETURNDATE) " + "VALUES('" + max + "', '" + rentalId
				+ "', TO_DATE(SYSDATE))";
		DB.executeQuery(sqlReturn); // DB에 sqlReturn 추가

		// 반납된 대여아이디의 반납 상태를 Y로 바꿔줌 -> 대여테이블에서 보여지지 않음
		String sqlStateModify = "UPDATE RENTAL " + "SET RETURNSTATE='Y'" + "WHERE RENTALID='" + rentalId + "'";
		ResultSet rsStateModify = DB.getResultSet(sqlStateModify); // 쿼리 넘기기
		DB.executeQuery(sqlStateModify); // DB 내용 수정

		// 반납된 우산상태를 N으로 바꿔줌 -> 다시 대여 가능하게
		String sqlUmbStateModify = "UPDATE UMBRELLA " + "SET STATE='N'" + "WHERE UMBRELLAID='" + umbcode + "'";
		ResultSet rsUmbStateModify = DB.getResultSet(sqlUmbStateModify); // 쿼리 넘기기
		DB.executeQuery(sqlUmbStateModify); // DB 내용 수정

		rentalTable(); // 테이블 새로고침

		// 반납한 사람의 이름 알아오기
		String sqlName = "SELECT NAME " + "FROM STUDENT " + "WHERE STUDENTID LIKE '" + code + "'";
		String findName = "";
		ResultSet rsFindName = DB.getResultSet(sqlName); // 쿼리 넘기기
		try {
			rsFindName.next(); // getString이전에 이것을 써야 ResultSet.next호출되지 않았다고 오류가 안뜸
			findName = rsFindName.getString(1);

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// 메시지창 출력
		JOptionPane.showMessageDialog(this, findName + "님의 우산이 반납처리되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
	}

	public int setRow(int i) {
		row = i;
		return row;
	}

	public void rentalTable() { // 새로고침
		getpCenter().removeAll(); // 패널 지우기
		setTable(); // 테이블 호출
		getpCenter().revalidate(); // 레이아웃 변화 재확인
		getpCenter().repaint(); // 레이아웃 다시 가져오기
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		table = (JTable) e.getComponent(); // 이벤트를 발생한 컴포넌트를 반환
		model = (DefaultTableModel) table.getModel(); // 테이블에 추가 - 데이터모델 가져오기

		if (e.getSource() == table) { // 클릭한 것이 테이블이라면
			row = table.getSelectedRow(); // 선택한 셀의 행 번호 계산
			TableModel data = table.getModel(); // 테이블의 모델 객체 얻어오기

			rentalId = (String) data.getValueAt(row, 0);
			umbcode = (String) data.getValueAt(row, 1);
			code = (String) data.getValueAt(row, 2);
		}
	}

	public String getRentalId() {
		return rentalId;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}