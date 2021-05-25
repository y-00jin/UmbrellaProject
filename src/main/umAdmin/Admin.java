package main.umAdmin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import main.DB;

public class Admin extends JFrame implements ActionListener, MouseListener {

	private JPanel p1, p2;
	private String result;
	private String[] strs = { "학생", "우산", "차단목록" };
	private JComboBox<String> cbStr;
	private DefaultTableModel model;
	private Vector<String> con;
	private JTable table;
	private String blockID;
	private String blockMajor;
	private String blockName;
	private String returnState;
	private String umbrellaID;
	private String umbrellaState;
	private String studentID;
	private String studentMajor;
	private String studentName;
	private String studentGrade;
	private String studentPhone;
	private String id;
	private JButton btnModify;
	private JButton btnDelect;
	private JButton btnAdd;
	private JButton btnCancel;
	private AdminModify modify;

	public String getId() {
		return id;
	}

	public Admin(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//		result = "";
//		result = JOptionPane.showInputDialog("관리자 확인을 위해 \n" + "비밀번호 네자리를 입력해주세요");
//
//		while (!result.equals("inhatc4")) {
//			result = JOptionPane.showInputDialog("비밀번호 오류!");
//		}

		p1 = new JPanel();
		p2 = new JPanel();

		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 10));

		p2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
		p2.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		
		p1.setBackground(Color.WHITE);
		p2.setBackground(Color.WHITE);

		cbStr = new JComboBox<String>(strs);
		cbStr.addActionListener(this);
		
		UIManager.put("ComboBox.background", new ColorUIResource(Color.yellow));
        UIManager.put("JTextField.background", new ColorUIResource(Color.yellow));
        UIManager.put("ComboBox.selectionBackground", new ColorUIResource(Color.magenta));
        UIManager.put("ComboBox.selectionForeground", new ColorUIResource(Color.blue));

		p1.add(cbStr);

		add(p1, new BorderLayout().NORTH);

		studentPanel();

		setVisible(true);
	}

	public static void main(String[] args) {
		DB.init();
		new Admin("관리자", 900, 700);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == cbStr) {
			p2.removeAll();

			int index = cbStr.getSelectedIndex();
			if (index == 0) {
				studentPanel();
			} else if (index == 1) {
				umbrellaPanel();
			} else if (index == 2) {
				blockPanel();
			}
			p2.revalidate();
			p2.repaint();
		}

	}

	private void blockPanel() {
		Vector<String> column = new Vector<String>();
		column.addElement("차단코드");
		column.addElement("학과");
		column.addElement("이름");
		column.addElement("반납여부");

		model = new DefaultTableModel(column, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		String sql = "SELECT * FROM BLOCKLIST";
		ResultSet rs = DB.getResultSet(sql);

		try {
			while (rs.next()) {
				con = new Vector<String>();
				blockID = rs.getString(1);
				blockMajor = rs.getString(2);
				blockName = rs.getString(2);
				returnState = rs.getString(2);

				con.add(blockID);
				con.add(blockMajor);
				con.add(blockName);
				con.add(returnState);
				model.addRow(con);
			}
		} catch (SQLException e) {
			System.out.println("접속 오류 / SQL 오류");
			e.printStackTrace();
		}

		table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false); // 테이블 편집X
		
		table.setFillsViewportHeight(true);
		
		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setBackground(new Color(0xB2CCFF));
		
		JScrollPane sc = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		sc.setPreferredSize(new Dimension(850, 535));

		btnCancel = new JButton("차단 취소");

		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		TableColumnModel tcmSchedule = table.getColumnModel();

		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}

		p2.add(sc);
		p2.add(btnCancel);

		add(p2, new BorderLayout().CENTER);
	}

	private void umbrellaPanel() {
		Vector<String> column = new Vector<String>();
		column.addElement("우산코드");
		column.addElement("대여상태");

		model = new DefaultTableModel(column, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		String sql = "SELECT * FROM UMBRELLA";
		ResultSet rs = DB.getResultSet(sql);

		try {
			while (rs.next()) {
				con = new Vector<String>();
				umbrellaID = rs.getString(1);
				umbrellaState = rs.getString(2);

				con.add(umbrellaID);
				con.add(umbrellaState);
				model.addRow(con);
			}
		} catch (SQLException e) {
			System.out.println("접속 오류 / SQL 오류");
			e.printStackTrace();
		}

		table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false); // 테이블 편집X
		
		table.setFillsViewportHeight(true);
		
		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setBackground(new Color(0xB2CCFF));
		
		JScrollPane sc = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		sc.setPreferredSize(new Dimension(850, 535));

		btnAdd = new JButton("추가");
		btnDelect = new JButton("삭제");

		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		TableColumnModel tcmSchedule = table.getColumnModel();

		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
		p2.add(sc);
		p2.add(btnAdd);
		p2.add(btnDelect);

		add(p2, new BorderLayout().CENTER);

	}

	private void studentPanel() {
		Vector<String> column = new Vector<String>();
		column.addElement("학번");
		column.addElement("학과");
		column.addElement("학년");
		column.addElement("이름");
		column.addElement("전화번호");

		model = new DefaultTableModel(column, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		String sql = "SELECT * FROM STUDENT";
		ResultSet rs = DB.getResultSet(sql);

		try {
			while (rs.next()) {
				con = new Vector<String>();
				studentID = rs.getString(1);
				studentMajor = rs.getString(3);
				studentGrade = rs.getString(4);
				studentName = rs.getString(2);
				studentPhone = rs.getString(5);

				con.add(studentID);
				con.add(studentMajor);
				con.add(studentGrade);
				con.add(studentName);
				con.add(studentPhone);
				model.addRow(con);
			}
		} catch (SQLException e) {
			System.out.println("접속 오류 / SQL 오류");
			e.printStackTrace();
		}

		table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false); // 테이블 편집X
		table.addMouseListener(this);

		table.setFillsViewportHeight(true);
		
		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setBackground(new Color(0xB2CCFF));
		
		JScrollPane sc = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		sc.setPreferredSize(new Dimension(850, 535));

		btnModify = new JButton("수정");

		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		TableColumnModel tcmSchedule = table.getColumnModel();

		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
		p2.add(sc);
		p2.add(btnModify);

		add(p2, new BorderLayout().CENTER);

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getClickCount() == 2) {
			int row = table.getSelectedRow(); // 선택한 셀의 행 번호 계산

			TableModel data = table.getModel(); // 테이블의 모델 객체 얻어오기

			id = (String) data.getValueAt(row, 0);
			String major = (String) data.getValueAt(row, 1);
			String grade = (String) data.getValueAt(row, 2);
			String name = (String) data.getValueAt(row, 3);
			String phone = (String) data.getValueAt(row, 4);

			modify = new AdminModify("수정", 250, 300, this);

			modify.getTfMajor().setText(major);
			modify.getTfGrade().setText(grade);
			modify.getTfName().setText(name);
			modify.getTfPhone().setText(phone);
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
