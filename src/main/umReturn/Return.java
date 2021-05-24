package main.umReturn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import main.DB;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;


public class Return extends JFrame implements ActionListener {

	private JPanel panelSearch;
	private JLabel lblDate1;
	private JTextField tfDate1;
	private JLabel lblHyphen;
	private JTextField tfDate2;
	private JButton btnSearch;
	private JPanel panelInfo;
	private Vector<String> con;
	private Vector<String> returnColumn;
	private DefaultTableModel model;
	private JTable table;
	private JButton btnReset;
	private UtilDateModel model1;
	private JDatePanelImpl datePanel1;
	private JDatePickerImpl datePicker1;
	private UtilDateModel model2;
	private JDatePanelImpl datePanel2;
	private JDatePickerImpl datePicker2;
	private Date SelectedDate1;
	private Date SelectedDate2;

	public Return(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this); // 현재 클래스에 대해서 상대적인 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addSearch();
		
		addTable();
		
		
		add(panelSearch, BorderLayout.NORTH);
		add(panelInfo, BorderLayout.CENTER);
		setVisible(true);
	}

	private void addSearch() {
		panelSearch = new JPanel();
		panelSearch.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelSearch.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		lblDate1 = new JLabel("날짜 검색 : ");
		panelSearch.add(lblDate1);

		
		
		model1 = new UtilDateModel();
		datePanel1 = new JDatePanelImpl(model1);
		//datePicker1 = new JDatePickerImpl(datePanel1);
		datePicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
		panelSearch.add(datePicker1);
		

		lblHyphen = new JLabel(" - ");
		panelSearch.add(lblHyphen);

		
		model2 = new UtilDateModel();
		datePanel2 = new JDatePanelImpl(model2);
		//datePicker2 = new JDatePickerImpl(datePanel2);
		datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		panelSearch.add(datePicker2);

		btnSearch = new JButton("검색");
		btnSearch.addActionListener(this);
		panelSearch.add(btnSearch);

		btnReset = new JButton("초기화");
		panelSearch.add(btnReset);
		
		
	}

	private void addTable() {
		panelInfo = new JPanel();
		panelInfo.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		returnColumn = new Vector<String>();
		returnColumn.add("반납 코드");
		returnColumn.add("우산 코드");
		returnColumn.add("학번");
		returnColumn.add("이름");
		returnColumn.add("대여일");
		returnColumn.add("반납일");

		model = new DefaultTableModel(returnColumn, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		/*
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@114.71.137.174:53994:XE",
					"dodam", "inhatc4");
			
			Statement stmt = conn.createStatement();
			
			
			ResultSet rs = stmt.executeQuery(
					"SELECT RETURNID, UMBRELLAID, STUDENTID, NAME, TO_CHAR(RENTALDATE, \'YYYY-MM-DD\') RENTALDATE,"
					+ " TO_CHAR(RETURNDATE, \'YYYY-MM-DD\') RETURNDATE FROM return");
			
			while(rs.next()) {
				con = new Vector<String>();
				String returnId = rs.getString("returnid");
				String umbrellaId = rs.getString("umbrellaid");
				String studentId = rs.getString("studentid");
				String name = rs.getString("name");
				String rentalDate = rs.getString("rentaldate");
				String returnDate = rs.getString("returndate");
			
				con.add(returnId);
				con.add(umbrellaId);
				con.add(studentId);
				con.add(name);
				con.add(rentalDate);
				con.add(returnDate);
				
				model.addRow(con);
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("해당 드라이버가 없습니다.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("접속 오류 / SQL 오류");
			e.printStackTrace();
		} */
		

		table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false); // 테이블 편집X
		//table.getTableHeader().setResizingAllowed(false);
		JScrollPane sc = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sc.setPreferredSize(new Dimension(860,480));
		panelInfo.add(sc);
		
	}

	public static void main(String[] args) {
		DB.init();
		new Return("반납", 900, 600);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnSearch) {
			String datePattern = "yyyy-MM-dd";
			SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
			
			SelectedDate1 = (Date) datePicker1.getModel().getValue();
			SelectedDate2 = (Date) datePicker2.getModel().getValue();
			
			System.out.println((dateFormatter.format(SelectedDate1)));
			System.out.println((dateFormatter.format(SelectedDate2)));
		}
		
	}

}
