package main.umAdmin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import main.DB;
import main.style.BtnFont;

public class Admin extends JFrame implements ActionListener, MouseListener {

	private JPanel panelContainer, panelTopTop, panelTopBottom, panelTop, panelWest, panelEast, panelCenter;
	private String result;
	private String[] strs = { "학생", "우산", "미반납자" };
	private JComboBox<String> cbStr;
	private DefaultTableModel model;
	private Vector<String> con;
	private JTable studentTable, umbrellaTable, blockTable;
	private String returnState;
	private String umbrellaID;
	private String umbrellaState;
	private String studentID;
	private String studentMajor;
	private String studentName;
	private String studentPhone;
	private String id;
	private JButton btnDelect;
	private JButton btnAdd;
	private AdminModify modify;
	private JTable table;
	private String umCode;
	private JButton btnClose;
	private ImageIcon icon;
	private ImageIcon btnIcon;
	private String department;
	private String studentId;
	private String name;
	private String phoneNumber;
	private String returnDueDate;

	public String getId() {
		return id;
	}

	public JPanel getPanelCenter() {
		return panelCenter;
	}
	
	public String getumbrellaState() {
		return umbrellaState;
	}

	public Admin(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);
		dispose();
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//		result = "";
//		result = JOptionPane.showInputDialog("관리자 확인을 위해 \n" + "비밀번호 네자리를 입력해주세요");
//
//		while (!result.equals("****")) {
//			result = JOptionPane.showInputDialog("비밀번호 오류!");
//		} // 관리자 확인 비밀번호 입력

		setUndecorated(true); // 타이블바 삭제

		panelContainer = new JPanel();
		panelContainer.setLayout(new BorderLayout());

		panelTop = new JPanel();
		panelTop.setLayout(new BorderLayout());

		panelTopTop = new JPanel();
		panelTopTop.setLayout(new BorderLayout());
		panelTopTop.setPreferredSize(new Dimension(900, 50)); //패널 사이즈 조정
		
		panelTopBottom = new JPanel();
		
		panelWest = new JPanel();
		panelEast = new JPanel();

		panelCenter = new JPanel();

		panelTopBottom.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelTopBottom.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 10)); // 마진
		
		panelWest.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelWest.setBorder(BorderFactory.createEmptyBorder(8, 20, 0, 10)); // 마진

		panelEast.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelEast.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 10)); // 마진

		panelCenter.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
		panelCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // 마진

		panelTopTop.setBackground(new Color(0xDEE5F3));
		panelTopBottom.setBackground(Color.WHITE);
		panelWest.setBackground(new Color(0xDEE5F3));
		panelEast.setBackground(new Color(0xDEE5F3));
		panelCenter.setBackground(Color.WHITE); // 패널 배경색 설정

		Font lblFont = new Font("HY헤드라인M", Font.PLAIN, 20);

		JLabel lblTitle = new JLabel("관리자");
		lblTitle.setFont(lblFont);

		panelTopTop.add(lblTitle);

		cbStr = new JComboBox<String>(strs);
		cbStr.addActionListener(this);

		icon = new ImageIcon("libs/exit.png");
		Image changeIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		btnIcon = new ImageIcon(changeIcon);

		btnClose = new JButton(btnIcon);
		btnClose.setContentAreaFilled(false); // 배경 투명하게
		btnClose.setBorderPainted(false); // 테두리 삭제

		btnClose.addActionListener(this);

		panelWest.add(lblTitle);
		panelEast.add(btnClose);

		panelTopTop.add(panelWest, new BorderLayout().WEST);
		panelTopTop.add(panelEast, new BorderLayout().EAST);

		panelTopBottom.add(cbStr);
		 
		panelTop.add(panelTopTop, new BorderLayout().NORTH);
		panelTop.add(panelTopBottom, new BorderLayout().SOUTH);
		
		panelContainer.add(panelTop, new BorderLayout().NORTH);

		add(panelContainer);

		studentPanel();

		panelContainer.setBorder(new LineBorder(Color.GRAY, 2)); // 패널 테두리 설정

		setVisible(true);
	}

	public static void main(String[] args) {
		DB.init();
		new Admin("관리자", 900, 600);
	}

	private void blockPanel() { // 블록테이블 생성
		Vector<String> column = new Vector<String>();
		column.addElement("전공");
		column.addElement("학번");
		column.addElement("이름");
		column.addElement("전화번호");
		column.addElement("반납예정일");
		column.addElement("반납여부");

		model = new DefaultTableModel(column, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		String sql = "SELECT * FROM BLOCKVIEW "
					+ "ORDER BY Department"; // 차단목록 전부 조회
		ResultSet rs = DB.getResultSet(sql);

		try {
			while (rs.next()) {
				con = new Vector<String>();
				department = rs.getString(1);
				studentId = rs.getString(2);
				name = rs.getString(3);
				phoneNumber = rs.getString(4);
				returnDueDate = rs.getString(6);
				returnState = rs.getString(7);

				con.add(department);
				con.add(studentId);
				con.add(name);
				con.add(phoneNumber);
				con.add(returnDueDate);
				con.add(returnState);
				model.addRow(con); // 테이블에 내용 추가
			}
		} catch (SQLException e) {
			System.out.println("접속 오류 / SQL 오류");
			e.printStackTrace();
		}

		blockTable = new JTable(model);
		blockTable.getTableHeader().setReorderingAllowed(false); // 테이블 편집X
		blockTable.addMouseListener(this);

		blockTable.setFillsViewportHeight(true); // 컨테이너의 전체 높이를 테이블이 전부 사용하도록 설정 -> 테이블 색이 컨테이너 색으로 덮힘

		JTableHeader tableHeader = blockTable.getTableHeader();
		tableHeader.setBackground(new Color(0xB2CCFF)); // 테이블 헤더 색 설정

		JScrollPane sc = new JScrollPane(blockTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		sc.setPreferredSize(new Dimension(850, 410)); // 스크롤팬 크기 설정


		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		TableColumnModel tcmSchedule = blockTable.getColumnModel();

		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		} // 테이블 내용 가운데 정렬

		panelCenter.add(sc);

		panelContainer.add(panelCenter, new BorderLayout().CENTER);
	}

	public void umbrellaPanel() { // 우산테이블 생성
		Vector<String> column = new Vector<String>();
		column.addElement("우산코드");
		column.addElement("대여상태");

		model = new DefaultTableModel(column, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		String sql = "SELECT * FROM UMBRELLA order by UMBRELLAID asc"; // 우산목록 전부 조회
		ResultSet rs = DB.getResultSet(sql);

		try {
			while (rs.next()) {
				con = new Vector<String>();
				umbrellaID = rs.getString(1);
				umbrellaState = rs.getString(2);

				con.add(umbrellaID);
				con.add(umbrellaState);
				model.addRow(con); // 테이블에 내용 추가
			}
		} catch (SQLException e) {
			System.out.println("접속 오류 / SQL 오류");
			e.printStackTrace();
		}

		umbrellaTable = new JTable(model);
		umbrellaTable.getTableHeader().setReorderingAllowed(false); // 테이블 편집X
		umbrellaTable.addMouseListener(this);

		umbrellaTable.setFillsViewportHeight(true); // 컨테이너의 전체 높이를 테이블이 전부 사용하도록 설정 -> 테이블 색이 컨테이너 색으로 덮힘

		JTableHeader tableHeader = umbrellaTable.getTableHeader();
		tableHeader.setBackground(new Color(0xB2CCFF)); // 테이블 헤더 색 설정

		JScrollPane sc = new JScrollPane(umbrellaTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		sc.setPreferredSize(new Dimension(850, 410)); // 스크롤팬 크기 설정

		btnAdd = new JButton("추가");
		BtnFont.BtnStyle(btnAdd);
		btnAdd.addActionListener(this);

		btnDelect = new JButton("삭제");
		BtnFont.BtnStyle(btnDelect);
		btnDelect.addActionListener(this);

		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		TableColumnModel tcmSchedule = umbrellaTable.getColumnModel();

		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		} // 테이블 내용 가운데 정렬

		panelCenter.add(sc);
		panelCenter.add(btnAdd);
		panelCenter.add(btnDelect);

		panelContainer.add(panelCenter, new BorderLayout().CENTER);

	}

	public String getUmbrellaState() {
		return umbrellaState;
	}

	public void studentPanel() { // 학생테이블 생성
		Vector<String> column = new Vector<String>();
		column.addElement("학번");
		column.addElement("학과");
		column.addElement("이름");
		column.addElement("전화번호");

		model = new DefaultTableModel(column, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		String sql = "SELECT * FROM STUDENT"; // 학생목록 전부 조회
		ResultSet rs = DB.getResultSet(sql);

		try {
			while (rs.next()) {
				con = new Vector<String>();
				studentID = rs.getString(1);
				studentMajor = rs.getString(3);
				studentName = rs.getString(2);
				studentPhone = rs.getString(4);

				con.add(studentID);
				con.add(studentMajor);
				con.add(studentName);
				con.add(studentPhone);
				model.addRow(con); // 테이블에 내용 추가
			}
		} catch (SQLException e) {
			System.out.println("접속 오류 / SQL 오류");
			e.printStackTrace();
		}

		studentTable = new JTable(model);
		studentTable.getTableHeader().setReorderingAllowed(false); // 테이블 편집X
		studentTable.addMouseListener(this);

		studentTable.setFillsViewportHeight(true); // 컨테이너의 전체 높이를 테이블이 전부 사용하도록 설정 -> 테이블 색이 컨테이너 색으로 덮힘

		JTableHeader tableHeader = studentTable.getTableHeader();
		tableHeader.setBackground(new Color(0xB2CCFF)); // 테이블 헤더 색 설정

		JScrollPane sc = new JScrollPane(studentTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		sc.setPreferredSize(new Dimension(850, 410)); // 스크롤팬 크기 설정

		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		TableColumnModel tcmSchedule = studentTable.getColumnModel();

		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		} // 테이블 내용 가운데 정렬

		panelCenter.add(sc);

		panelContainer.add(panelCenter, new BorderLayout().CENTER);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == cbStr) {

			panelCenter.removeAll(); // 패널 지우기

			int index = cbStr.getSelectedIndex(); // 콤보박스 index 받아오기
			if (index == 0) {
				studentPanel();
			} else if (index == 1) {
				umbrellaPanel(); // index 1일때 우산 테이블
			} else if (index == 2) {
				blockPanel(); // index 2일때 차단 테이블
			}
			panelCenter.revalidate(); // 레이아웃 변화 재확인
			panelCenter.repaint(); // 레이아웃 다시 가져오기

		}

		if (obj == btnAdd) {

			new AdminUmbrellaAdd("우산추가", 300, 300, this);

		}

		if (obj == btnDelect) {

			String sql = "DELETE FROM UMBRELLA " + "WHERE UMBRELLAID='" + umCode + "'";
			DB.executeQuery(sql); // DB 내용 삭제임

			panelCenter.removeAll();
			umbrellaPanel();
			panelCenter.revalidate(); // 레이아웃 변화 재확인
			panelCenter.repaint(); // 레이아웃 다시 가져오기

		}

		if (obj == btnClose) {
			dispose();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		table = (JTable) e.getComponent();
		model = (DefaultTableModel) table.getModel();

		if (e.getSource() == studentTable) { // 학생테이블에서 적용되는 마우스 이벤트

			if (e.getClickCount() == 2) {
				int row = studentTable.getSelectedRow(); // 선택한 셀의 행 번호 계산

				TableModel data = studentTable.getModel(); // 테이블의 모델 객체 얻어오기

				id = (String) data.getValueAt(row, 0);
				String major = (String) data.getValueAt(row, 1);
				String name = (String) data.getValueAt(row, 2);
				String phone = (String) data.getValueAt(row, 3);

				modify = new AdminModify("학생수정", 250, 300, this);

				modify.getTfMajor().setText(major);
				modify.getTfName().setText(name);
				modify.getTfPhone().setText(phone);
			}

		}

		if (e.getSource() == umbrellaTable) { // 우산테이블에서 적용되는 마우스 이벤트

			umCode = (String) model.getValueAt(table.getSelectedRow(), 0);

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}