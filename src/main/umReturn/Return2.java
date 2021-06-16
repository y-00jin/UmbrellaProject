package main.umReturn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import main.DB;
import main.style.BtnFont;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class Return2 extends JFrame implements ActionListener {

	private JPanel panelSearch, panelInfo;
	private JLabel lblDate1, lblHyphen;
	private JButton btnSearch, btnReset;
	private Vector<String> returnColumn;
	private DefaultTableModel model;
	private JTable table;
	private UtilDateModel model1, model2;
	private JDatePanelImpl datePanel1, datePanel2;
	private JDatePickerImpl datePicker1, datePicker2;
	private Date SelectedDate1, SelectedDate2;
	private JButton btnClose;
	private JPanel panelTop;
	private Font lblFont = new Font("HY헤드라인M", Font.PLAIN, 15);
	private JLabel lblLogo;

	private static Calendar cal = Calendar.getInstance();
	private String[] day = new String[] { "S", "M", "T", "W", "T", "F", "S" };
	private ArrayList<JButton> lstBtn = new ArrayList<JButton>();
	private int thisYear;
	private int thisMonth;
	private static int todayYear = cal.get(Calendar.YEAR);
	private static int todayMonth = cal.get(Calendar.MONTH) + 1;
	private int setTime = 0;
	private int selectindex = 0;
	private JLabel lblYear;
	private JLabel lblMonth;
	private JLabel lblday;
	private int dayX;
	private int dayY;
	private JButton btnDay;
	private JLabel lblTitle;
	private JTextField tfDate1;
	private JPanel pTitle;
	private JTextField tfDate2;
	private JPanel jpCalendarLeft;
	private JButton btnLeft;
	private JButton btnRight;

	public Return2(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this); // 현재 클래스에 대해서 상대적인 위치DD
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// dispose(); // 닫으면 이 프레임만 종료

		Container c = getContentPane();
		c.setBackground(Color.white);

		FormLayout();

//		// 탑 패널 생성 (타이틀 & 검색)
//		addTop();
//
//		// 테이블 생성
//		addTable();
//
//		add(panelTop, BorderLayout.NORTH);
//		add(panelInfo, BorderLayout.CENTER);

		setVisible(true);
	}

	private void setCalendar(JPanel panelCal, int year, int month, int x, int y) {
		panelCal = new JPanel();
		panelCal.setLayout(null);
		panelCal.setSize(300, 280);
		panelCal.setLocation(x, y);
		panelCal.setBackground(Color.WHITE);
		thisYear = year; // 현재 년
		thisMonth = month; // 현재 달

		cal.set(thisYear, thisMonth - 1, 1);// 캘린더 객체에 년도, 달 설정과 Date 1로 설정

		int sDayNum = cal.get(Calendar.DAY_OF_WEEK); // 1일의 요일 얻어오기
		int endDate = cal.getActualMaximum(Calendar.DATE); // 달의 마지막 일 얻기

		int intDateNum = 1; // 1일부터 날짜 시작

//		lblYear = new JLabel(thisYear + "년"); // 년도 표시 라벨
//		lblYear.setFont(lblFont);
//		lblYear.setSize(150, 40);
//		lblYear.setLocation(100, 2);
		lblYear.setText(year + " 년 ");
		lblMonth.setText(month + " 월");

//		lblMonth = new JLabel(thisMonth + "월"); // 달 표시 라벨
//		lblMonth.setFont(lblFont);
//		lblMonth.setSize(80, 40);
//		lblMonth.setLocation(160, 2);
		// panelCal.add(lblYear);
		// panelCal.add(lblMonth);

		for (int i = 0; i < 7; i++) { // 요일 추가
			lblday = new JLabel(day[i]); // day[i]로 요일 배열 추가
			lblday.setFont(lblFont);
			lblday.setSize(100, 40);
			lblday.setLocation(40 * i + 26, 30); // 위치 설정
			panelCal.add(lblday);

		}

		dayX = 0; // 날짜버튼 x좌표 기본 0으로 설정
		dayY = 70; // 날짜버튼 y좌표 기본 설정

		for (int i = 1; intDateNum <= endDate; i++) { // intDateNum이 1일부터 마지막일이 될 때까지 반복

			if (i < sDayNum) // i가 요일숫자보다 작으면 공백 버튼
			{
				btnDay = new JButton("");
				btnDay.setBorderPainted(false); // 버튼 윤곽선 제거
				// btnDay.setBackground(new Color(153, 204, 255)); //버튼 색 설정
				btnDay.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // 안쪽 여백 설정
				btnDay.setBackground(Color.white); // 버튼 색 설정
				btnDay.setSize(40, 40);
				btnDay.setLocation(dayX + 10, dayY);
				lstBtn.add(btnDay);
				panelCal.add(btnDay);

				dayX += 40;

			} else // date값의 버튼 출력
			{
				btnDay = new JButton(intDateNum + "");
				btnDay.addActionListener(this);
				btnDay.setBorderPainted(false); // 버튼 윤곽선 제거
				// btnDay.setBackground(new Color(153, 204, 255)); //버튼 색 설정
				btnDay.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // 안쪽 여백 설정
				btnDay.setBackground(Color.white); // 버튼 색 설정
				btnDay.setSize(40, 30);
				btnDay.setLocation(dayX + 10, dayY);

				if ((i - 8) % 7 == 0) {
					btnDay.setForeground(Color.red); // 일요일은 빨간색 설정
				} else if (i % 7 == 0) {
					btnDay.setForeground(Color.blue); // 토요일은 파란색 설정
				} else {
					btnDay.setForeground(Color.black); // 나머지 검정색 설정
				}

				lstBtn.add(btnDay);
				panelCal.add(btnDay);

				intDateNum++; // 출력할 date 증가
				dayX += 40; // X좌표 증가로 옆으로 이동
			}

			if (i % 7 == 0) // i가 7로 나눠지면 다음 줄로 이동
			{
				dayY += 40; // Y좌표 증가로 다음 줄로 이동
				dayX = 0; // X좌표 초기화
			}

		}

		add(panelCal); // 판넬 추가

	}

	private void FormLayout() {

		// 로고
		pTitle = new JPanel();
		pTitle.setLayout(null);
		pTitle.setSize(500, 80);
		pTitle.setLocation(0, 0);
		pTitle.setBackground(Color.white); // 배경색

		// 로고 이미지
		ImageIcon icontitle = new ImageIcon("libs/폼로고.jpg");
		Image changeIcon = icontitle.getImage().getScaledInstance(450, 50, Image.SCALE_SMOOTH);
		ImageIcon lblIcontitle = new ImageIcon(changeIcon);

		lblLogo = new JLabel(lblIcontitle);
		lblLogo.setSize(450, 50);
		lblLogo.setLocation(20, 20);
		pTitle.add(lblLogo);

		// 날짜 검색 라벨
		lblTitle = new JLabel("날짜 검색");
		lblTitle.setFont(lblFont);
		lblTitle.setSize(80, 40);
		lblTitle.setLocation(20, 100);
		pTitle.add(lblTitle);

		// 텍스트 필드
		tfDate1 = new JTextField();
		tfDate1.setHorizontalAlignment(SwingConstants.CENTER);
		tfDate1.setSize(100, 40);
		tfDate1.setLocation(100, 100);
		pTitle.add(tfDate1);

		// -
		lblHyphen = new JLabel(" - ");
		lblHyphen.setFont(lblFont);
		lblHyphen.setSize(30, 40);
		lblHyphen.setLocation(210, 100);
		pTitle.add(lblHyphen);

		// 텍스트 필드
		tfDate2 = new JTextField();
		tfDate2.setHorizontalAlignment(SwingConstants.CENTER);
		tfDate2.setSize(100, 40);
		tfDate2.setLocation(240, 100);
		pTitle.add(tfDate2);

		lblYear = new JLabel("년");
		lblYear.setFont(lblFont);
		lblYear.setSize(80, 40);
		lblYear.setLocation(130, 150);
		pTitle.add(lblYear);

		lblMonth = new JLabel("월"); // 달 표시 라벨
		lblMonth.setFont(lblFont);
		lblMonth.setSize(80, 40);
		lblMonth.setLocation(200, 150);
		pTitle.add(lblMonth);

		setCalendar(jpCalendarLeft, todayYear + setTime, todayMonth + setTime, 30, 178); // 이번달 달력 출력

		btnSearch = new JButton("검색");
		BtnFont.BtnStyle(btnSearch);
		btnSearch.setSize(150, 40);
		btnSearch.setLocation(20, 480);
		btnSearch.addActionListener(this);
		pTitle.add(btnSearch);

		btnReset = new JButton("초기화");
		BtnFont.BtnStyle(btnReset);
		btnReset.setSize(150, 40);
		btnReset.setLocation(190, 480);
		btnReset.addActionListener(this);
		pTitle.add(btnReset);

		btnLeft = new JButton("<");
		btnLeft.setFont(lblFont);
		btnLeft.setSize(30, 250);
		btnLeft.setLocation(10, 200);
		btnLeft.setBorderPainted(false);
		btnLeft.setBackground(Color.white);
		btnLeft.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btnLeft.addActionListener(this);
		pTitle.add(btnLeft);

		btnRight = new JButton(">");
		btnRight.setFont(lblFont);
		btnRight.setSize(30, 250);
		btnRight.setLocation(320, 200);
		btnRight.setBorderPainted(false);
		btnRight.setBackground(Color.white);
		btnRight.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btnRight.addActionListener(this);
		pTitle.add(btnRight);

		// 테이블 헤더 생성
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

		model.setNumRows(0);
		String returnSelect = "select return.RETURNID, um.UMBRELLAID, st.STUDENTID, st.NAME, TO_CHAR(rental.rentaldate, \'YYYY-MM-DD\'), TO_CHAR(return.returndate, \'YYYY-MM-DD\') "
				+ " from RETURN return, RENTAL rental, STUDENT st, UMBRELLA um where return.rentalid = rental.rentalid"
				+ "  and rental.studentid = st.studentid and rental.umbrellaid = um.umbrellaid ORDER BY RETURN.RETURNID desc";

		ResultSet rs = DB.getResultSet(returnSelect);
		String[] rsArr = new String[6]; // 값 받아올 배열
		try {
			while (rs.next()) {

				for (int i = 0; i < rsArr.length; i++) {
					rsArr[i] = rs.getString(i + 1); // 값 저장
				}

				model.addRow(rsArr); // 모델에 추가

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		table = new JTable(model); // 테이블에 추가
		table.getTableHeader().setReorderingAllowed(false); // 테이블 편집X
		table.setFillsViewportHeight(true); // 테이블 배경색
		JTableHeader tableHeader = table.getTableHeader(); // 테이블 헤더 값 가져오기
		tableHeader.setBackground(new Color(0xB2CCFF)); // 가져온 테이블 헤더의 색 지정

		// 스크롤 팬
		JScrollPane sc = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sc.setPreferredSize(new Dimension(860, 470));
		sc.setLocation(380, 100);
		sc.setSize(700, 420);
		pTitle.add(sc);

		add(pTitle);
	}

	private void returnTable() {

		model.setNumRows(0);
		String returnSelect = "select return.RETURNID, um.UMBRELLAID, st.STUDENTID, st.NAME, TO_CHAR(rental.rentaldate, \'YYYY-MM-DD\'), TO_CHAR(return.returndate, \'YYYY-MM-DD\') "
				+ " from RETURN return, RENTAL rental, STUDENT st, UMBRELLA um where return.rentalid = rental.rentalid"
				+ "  and rental.studentid = st.studentid and rental.umbrellaid = um.umbrellaid ORDER BY RETURN.RETURNID DESC";

		ResultSet rs = DB.getResultSet(returnSelect);
		String[] rsArr = new String[6]; // 값 받아올 배열
		try {
			while (rs.next()) {

				for (int i = 0; i < rsArr.length; i++) {
					rsArr[i] = rs.getString(i + 1); // 값 저장
				}

				model.addRow(rsArr); // 모델에 추가

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		table = new JTable(model); // 테이블에 추가
	}

	public static void main(String[] args) {
		DB.init();
		new Return2("반납", 1120, 600);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		String objText = e.getActionCommand();
		String date = thisYear + "-" + thisMonth + "-" + objText;

		if (obj == btnSearch) {
			if (tfDate1.getText().equals("") || tfDate2.getText().equals("")) {
				JOptionPane.showMessageDialog( // 메시지창 출력
						this, "날짜를 지정해주세요.", "메시지", JOptionPane.WARNING_MESSAGE);
			} else {
				JOptionPane.showMessageDialog( // 메시지창 출력
						this, "검색이 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);

			}

//			model.setNumRows(0);
//
//			String datePattern = "yyyy-MM-dd"; // 데이터 포맷 형식 지정
//			SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern); // 데이터 포맷 형식 지정한 객체 생성
//
//			SelectedDate1 = (Date) datePicker1.getModel().getValue(); // 클릭된 날짜값 가져오기
//			SelectedDate2 = (Date) datePicker2.getModel().getValue(); // 클릭된 날짜값 가져오기
//			String returnSelect = "SELECT return.returnid, umbrella.UMBRELLAID ,student.STUDENTID , student.NAME , TO_CHAR(rental.RENTALDATE, 'YYYY-MM-DD'), TO_CHAR(RETURN.RETURNDATE, 'YYYY-MM-DD') "
//					+ "FROM \"RETURN\" return , STUDENT student , UMBRELLA umbrella, RENTAL rental "
//					+ "WHERE return.RENTALID = rental.rentalid AND rental.studentid = student.studentid AND rental.umbrellaid = umbrella.umbrellaid "
//					+ "and return.RETURNDATE BETWEEN '" + dateFormatter.format(SelectedDate1) + "' AND '"
//					+ dateFormatter.format(SelectedDate2) + "'";
//
//			ResultSet rs = DB.getResultSet(returnSelect); // 데이터 불러오기
//			String[] rsArr = new String[6];
//			try {
//				while (rs.next()) {
//
//					for (int i = 0; i < rsArr.length; i++) {
//						rsArr[i] = rs.getString(i + 1);
//					}
//
//					model.addRow(rsArr); // 테이블에 추가
//
//				}
//			} catch (SQLException exception) {
//				// TODO Auto-generated catch block
//				exception.printStackTrace();
//			}
//
//			table = new JTable(model);
		}

		else if (obj == btnReset) {
			selectindex = 0;
			tfDate1.setText("");
			tfDate2.setText("");
			todayYear = cal.get(Calendar.YEAR);
			todayMonth = cal.get(Calendar.MONTH) + 1;
			
			System.out.println(todayYear);
			System.out.println(todayMonth);
			setCalendar(jpCalendarLeft, todayYear, todayMonth, 30, 150);
			returnTable();
		}

		else if (obj == btnLeft) {
			setTime -= 1;
			setCalendar(jpCalendarLeft, todayYear, todayMonth + setTime, 30, 150);

		} else if (obj == btnRight) {
			System.out.println(todayYear);
			System.out.println(todayMonth);
		
			setTime += 1;
			setCalendar(jpCalendarLeft, todayYear, todayMonth + setTime, 30, 150);
			
			
		} else {
			if (selectindex == 0) {
				tfDate1.setText(date);
				selectindex++;

			} else if (selectindex == 1) {
				tfDate2.setText(date);
				selectindex = 0;

			}
		}
	}
}