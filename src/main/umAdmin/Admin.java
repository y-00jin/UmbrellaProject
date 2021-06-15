package main.umAdmin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
import main.style.hint;

public class Admin extends JFrame implements ActionListener, MouseListener {

	private JPanel panelContainer, panelTopBottom, panelTop, panelWest, panelEast, panelCenter, panelCenterWest,
			panelCenterEast;
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
	private JTextField tfSearch;
	private JButton btnSearch;
	private AdminUmbrellaAdd umAdd;
	private JPanel pTop;
	private JPanel pCenter;
	private JTextField tfMajor;
	private JTextField tfName;
	private JTextField tfPhone;
	private JPanel pBottom;
	private JButton btnOk;
	private JButton btnCancel;
	private String major;
	private String phone;
	private String stName;
	private JPanel panelTitle;
	private JLabel lblLogo;
	private JRadioButton rbY;
	private JRadioButton rbN;
	private JButton btnUmSearch;

	public String getId() {
		return id;
	}

	public JPanel getPanelCenter() {
		return panelCenter;
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

		panelContainer = new JPanel();
		panelContainer.setLayout(new BorderLayout());

		panelTop = new JPanel();
		panelTop.setLayout(new BorderLayout());

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

		panelTitle = new JPanel();
		panelTitle.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));

		panelTitle.setBackground(Color.white);
		panelTopBottom.setBackground(Color.WHITE);
		panelCenter.setBackground(Color.WHITE);// 배경색

		ImageIcon icontitle = new ImageIcon("libs/폼로고.jpg");
		Image changeIcon = icontitle.getImage().getScaledInstance(450, 50, Image.SCALE_SMOOTH);
		ImageIcon lblIcontitle = new ImageIcon(changeIcon);

		lblLogo = new JLabel(lblIcontitle);
		panelTitle.add(lblLogo);

		cbStr = new JComboBox<String>(strs);
		cbStr.addActionListener(this);

		btnClose = new JButton(btnIcon);
		btnClose.setContentAreaFilled(false); // 배경 투명하게
		btnClose.setBorderPainted(false); // 테두리 삭제

		btnClose.addActionListener(this);

		panelTopBottom.add(cbStr);

		panelTop.add(panelTitle, new BorderLayout().NORTH);
		panelTop.add(panelTopBottom, new BorderLayout().SOUTH);

		panelContainer.add(panelTop, new BorderLayout().NORTH);

		add(panelContainer);

		studentPanel();

		setVisible(true);
	}

	public static void main(String[] args) {
		DB.init();
		new Admin("관리자", 900, 600);
	}

	public void studentPanel() { // 학생테이블 생성
		panelCenter.setLayout(new BorderLayout());

		panelCenterEast = new JPanel();
		panelCenterEast.setBackground(Color.WHITE);
		panelCenterEast.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

		sc.setPreferredSize(new Dimension(500, 410)); // 스크롤팬 크기 설정

		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		TableColumnModel tcmSchedule = studentTable.getColumnModel();

		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		} // 테이블 내용 가운데 정렬

		panelCenterEast.add(sc);

		studentModify();

		// panelCenter.add(panelCenterWest, new BorderLayout().WEST);
		panelCenter.add(panelCenterWest);
		panelCenter.add(panelCenterEast, new BorderLayout().EAST);

		panelContainer.add(panelCenter, new BorderLayout().CENTER);

		this.setVisible(true);
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

		umTableAdd();

	}

	private void blockPanel() { // 블록테이블 생성
		panelCenter.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
		panelCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

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
		String sql = "SELECT * FROM BLOCKVIEW " + "ORDER BY Department"; // 차단목록 전부 조회
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

		sc.setPreferredSize(new Dimension(850, 360)); // 스크롤팬 크기 설정

		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		TableColumnModel tcmSchedule = blockTable.getColumnModel();

		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		} // 테이블 내용 가운데 정렬

		tfSearch = new hint("전공을 입력하세요"); // hint

		btnSearch = new JButton("검색");
		BtnFont.BtnStyle(btnSearch);

		panelCenter.add(sc);
		panelCenter.add(tfSearch);
		panelCenter.add(btnSearch);

		panelContainer.add(panelCenter, new BorderLayout().CENTER);
	}

	private void studentModify() {
		panelCenterWest = new JPanel();
		panelCenterWest.setLayout(new BorderLayout());

		panelCenterWest.setBorder(BorderFactory.createEmptyBorder(13, 10, 40, 10));
		panelCenterWest.setBackground(Color.WHITE);

		pTop = new JPanel();
		pTop.setBackground(new Color(0xB2CCFF));

		JLabel lbl1 = new JLabel("수 정");
		lbl1.setForeground(Color.BLACK); // JLabel글자 색 변경
		lbl1.setFont(new Font("HY헤드라인M", Font.PLAIN, 15));
		pTop.add(lbl1);

		panelCenterWest.add(pTop, BorderLayout.NORTH);

		pCenter = new JPanel();
		pCenter.setBackground(Color.WHITE);

		pCenter.setBorder(BorderFactory.createEmptyBorder(60, 10, 60, 10));
		pCenter.setLayout(new GridLayout(3, 2, 0, 60));

		JLabel lblMajor = new JLabel("학과 :");
		lblMajor.setFont(new Font("HY헤드라인M", Font.PLAIN, 15));
		pCenter.add(lblMajor);

		tfMajor = new JTextField();
		pCenter.add(tfMajor);

		JLabel lblName = new JLabel("이름 :");
		lblName.setFont(new Font("HY헤드라인M", Font.PLAIN, 15));
		pCenter.add(lblName);

		tfName = new JTextField();
		pCenter.add(tfName);

		JLabel lblPhone = new JLabel("전화번호 :");
		lblPhone.setFont(new Font("HY헤드라인M", Font.PLAIN, 15));
		pCenter.add(lblPhone);

		tfPhone = new JTextField();
		pCenter.add(tfPhone);

		panelCenterWest.add(pCenter, BorderLayout.CENTER);

		pBottom = new JPanel();

		pBottom.setBackground(Color.WHITE);

		btnOk = new JButton("수정");
		BtnFont.BtnStyle(btnOk);
		btnOk.addActionListener(this);

		pBottom.add(btnOk);

		btnCancel = new JButton("취소");
		BtnFont.BtnStyle(btnCancel);
		btnCancel.addActionListener(this);

		pBottom.add(btnCancel);

		panelCenterWest.add(pBottom, BorderLayout.SOUTH);
	}

	private void umTableAdd() {
		panelCenter.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
		panelCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

		umbrellaTable.getTableHeader().setReorderingAllowed(false); // 테이블 편집X
		umbrellaTable.addMouseListener(this);

		umbrellaTable.setFillsViewportHeight(true); // 컨테이너의 전체 높이를 테이블이 전부 사용하도록 설정 -> 테이블 색이 컨테이너 색으로 덮힘

		JTableHeader tableHeader = umbrellaTable.getTableHeader();
		tableHeader.setBackground(new Color(0xB2CCFF)); // 테이블 헤더 색 설정

		JScrollPane sc = new JScrollPane(umbrellaTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		sc.setPreferredSize(new Dimension(850, 360)); // 스크롤팬 크기 설정

		btnAdd = new JButton("추가");
		BtnFont.BtnStyle(btnAdd);
		btnAdd.addActionListener(this);

		btnDelect = new JButton("삭제");
		BtnFont.BtnStyle(btnDelect);
		btnDelect.addActionListener(this);

		btnUmSearch = new JButton("검색");
		BtnFont.BtnStyle(btnUmSearch);
		btnUmSearch.addActionListener(this);

		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		TableColumnModel tcmSchedule = umbrellaTable.getColumnModel();

		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		} // 테이블 내용 가운데 정렬

		JLabel lblSearch = new JLabel("대여 상태 : ");
		lblSearch.setFont(new Font("HY헤드라인M", Font.PLAIN, 15));

		JLabel lblSpace = new JLabel("                                                                                                                                             ");

		ButtonGroup bg = new ButtonGroup(); // 라디오그룹 생성
		rbY = new JRadioButton("Y");
		rbN = new JRadioButton("N");

		rbY.setBackground(Color.WHITE);
		rbN.setBackground(Color.WHITE);

		bg.add(rbY);
		bg.add(rbN);

		panelCenter.add(sc);

		panelCenter.add(lblSearch);
		panelCenter.add(rbY);
		panelCenter.add(rbN);
		panelCenter.add(btnUmSearch);
		panelCenter.add(lblSpace);

		panelCenter.add(btnAdd);
		panelCenter.add(btnDelect);

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

			umAdd = new AdminUmbrellaAdd("우산추가", 300, 250, this);

		}

		if (obj == btnDelect) {

			String sql = "DELETE FROM UMBRELLA " + "WHERE UMBRELLAID='" + umCode + "'";
			DB.executeQuery(sql); // DB 내용 삭제임

			panelCenter.removeAll();
			umbrellaPanel();
			panelCenter.revalidate(); // 레이아웃 변화 재확인
			panelCenter.repaint(); // 레이아웃 다시 가져오기

		}

		if (obj == btnOk) {
			String setStName = tfName.getText();
			String setDepartment = tfMajor.getText();
			String setPhone = tfPhone.getText();

			String sql = "UPDATE STUDENT SET NAME = '" + setStName + "'," + " DEPARTMENT = '" + setDepartment + "',"
					+ " PHONE = '" + setPhone + "'" + " WHERE STUDENTID = '" + id + "'";
			DB.executeQuery(sql); // DB 내용 수정

			// 새로고침
			panelCenter.removeAll();
			studentPanel();
			panelCenter.revalidate(); // 레이아웃 변화 재확인
			panelCenter.repaint(); // 레이아웃 다시 가져오기
		}

		if (obj == btnClose) {
			dispose();
		}

		if (obj == btnUmSearch) {
			if (rbY.isSelected()) {
				panelCenter.removeAll();

				Vector<String> column = new Vector<String>();
				column.addElement("우산코드");
				column.addElement("대여상태");

				model = new DefaultTableModel(column, 0) {
					public boolean isCellEditable(int r, int c) {
						return false;
					}
				};

				String sql = "SELECT * FROM UMBRELLA WHERE STATE = 'Y' order by UMBRELLAID asc"; // 우산상태 Y 조회
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
				} catch (SQLException e1) {
					System.out.println("접속 오류 / SQL 오류");
					e1.printStackTrace();
				}

				umbrellaTable = new JTable(model);

				umTableAdd();

				panelCenter.revalidate(); // 레이아웃 변화 재확인
				panelCenter.repaint(); // 레이아웃 다시 가져오기
			}

			else if (rbN.isSelected()) {
				panelCenter.removeAll();

				Vector<String> column = new Vector<String>();
				column.addElement("우산코드");
				column.addElement("대여상태");

				model = new DefaultTableModel(column, 0) {
					public boolean isCellEditable(int r, int c) {
						return false;
					}
				};

				String sql = "SELECT * FROM UMBRELLA WHERE STATE = 'N' order by UMBRELLAID asc"; // 우산상태 Y 조회
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
				} catch (SQLException e1) {
					System.out.println("접속 오류 / SQL 오류");
					e1.printStackTrace();
				}

				umbrellaTable = new JTable(model);

				umTableAdd();

				panelCenter.revalidate(); // 레이아웃 변화 재확인
				panelCenter.repaint(); // 레이아웃 다시 가져오기
			}

			else {
				panelCenter.removeAll();

				umbrellaPanel();

				panelCenter.revalidate(); // 레이아웃 변화 재확인
				panelCenter.repaint(); // 레이아웃 다시 가져오기
			}
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
				major = (String) data.getValueAt(row, 1);
				stName = (String) data.getValueAt(row, 2);
				phone = (String) data.getValueAt(row, 3);

				tfMajor.setText(major);
				tfName.setText(stName);
				tfPhone.setText(phone);
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