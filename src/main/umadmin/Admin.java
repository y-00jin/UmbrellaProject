package main.umadmin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.DB;

public class Admin extends JFrame implements ActionListener {

	private JPanel p1, p2;
	private String result;
	private String[] strs = { "학생", "우산", "차단리스트" };
	private JComboBox<String> cbStr;
	private DefaultTableModel model;
	private Vector<String> strTable;
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

		p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		cbStr = new JComboBox<String>(strs);
		cbStr.addActionListener(this);

		p1.add(cbStr);

		add(p1, new BorderLayout().NORTH);

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

		JScrollPane sc = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		sc.setPreferredSize(new Dimension(850, 500));

		p2.add(sc);
		add(p2, new BorderLayout().CENTER);
		
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
			int index = cbStr.getSelectedIndex();
			System.out.println(index);
			if (index == 0) {
				studentPanel();
			} else if (index == 1) {
				umbrellaPanel();
			} else if (index == 2) {
				blockPanel();
			}
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

		JScrollPane sc = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		sc.setPreferredSize(new Dimension(850, 500));

		p2.add(sc);
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

		JScrollPane sc = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		sc.setPreferredSize(new Dimension(850, 500));

		p2.add(sc);
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

		JScrollPane sc = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		sc.setPreferredSize(new Dimension(850, 500));

		p2.add(sc);
		add(p2, new BorderLayout().CENTER);
	}
}
