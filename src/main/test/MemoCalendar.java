package main.test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


import main.DB;

class CalendarDataManager{ // 6*7배열에 나타낼 달력 값을 구하는 class
	static final int CAL_WIDTH = 7;
	final static int CAL_HEIGHT = 6;
	int calDates[][] = new int[CAL_HEIGHT][CAL_WIDTH];	
	int calYear;	//년도
	int calMonth;	//월
	int calDayOfMon;	//일
	final int calLastDateOfMonth[]={31,28,31,30,31,30,31,31,30,31,30,31};	//1월부터 12월까지 마지막 날
	int calLastDate;
	Calendar today = Calendar.getInstance();	//캘린더 객체 사용
	Calendar cal;

	public CalendarDataManager(){ 
		setToday(); 
	}
	public void setToday(){
		calYear = today.get(Calendar.YEAR); //현재 년도
		calMonth = today.get(Calendar.MONTH);	//현재 월
		calDayOfMon = today.get(Calendar.DAY_OF_MONTH);	//현재 일
		makeCalData(today);	//캘린더의 today 객체를 이용해 달력을 만듦
	}
	private void makeCalData(Calendar cal){
		// 1일의 위치와 마지막 날짜를 구함 
		// DAY_OF_WEEK : 요일 / 1~7까지의 값을 리턴 / 일, 월 ~ 토요일
		// DAY_OF_MONTH : 현재 월의 날짜
		int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK)+7-(cal.get(Calendar.DAY_OF_MONTH))%7)%7;
		if(calMonth == 1) calLastDate = calLastDateOfMonth[calMonth] + leapCheck(calYear);	// 현재 위치(날짜) 뽑아오기
		else calLastDate = calLastDateOfMonth[calMonth];
		// 달력 배열 초기화
		for(int i = 0 ; i<CAL_HEIGHT ; i++){
			for(int j = 0 ; j<CAL_WIDTH ; j++){
				calDates[i][j] = 0;
			}
		}
		// 달력 배열에 값 채워넣기
		for(int i = 0, num = 1, k = 0 ; i<CAL_HEIGHT ; i++){
			if(i == 0) k = calStartingPos;
			else k = 0;
			for(int j = k ; j<CAL_WIDTH ; j++){
				if(num <= calLastDate) calDates[i][j]=num++;
			}
		}
	}
	private int leapCheck(int year){ // 윤년인지 확인하는 함수
		if(year%4 == 0 && year%100 != 0 || year%400 == 0) return 1;
		else return 0;
	}
	public void moveMonth(int mon){ // 현재달로 부터 n달 전후를 받아 달력 배열을 만드는 함수(1년은 +12, -12달로 이동 가능)
		calMonth += mon;
		if(calMonth>11) while(calMonth>11){
			calYear++;
			calMonth -= 12;
		} else if (calMonth<0) while(calMonth<0){
			calYear--;
			calMonth += 12;
		}
		cal = new GregorianCalendar(calYear,calMonth,calDayOfMon);
		makeCalData(cal);
	}
}

public class MemoCalendar extends CalendarDataManager{ // CalendarDataManager의 GUI 
	// 창 구성요소와 배치도
	JFrame mainFrame;
	
	JPanel calOpPanel;
		JButton todayBut;
		JLabel todayLab;
		JButton lYearBut;
		JButton lMonBut;
		JLabel curMMYYYYLab;
		JButton nMonBut;
		JButton nYearBut;
		ListenForCalOpButtons lForCalOpButtons = new ListenForCalOpButtons();
	
	JPanel calPanel;	//달력 구성
		JButton weekDaysName[];
		JButton dateButs[][] = new JButton[6][7];
		listenForDateButs lForDateButs = new listenForDateButs(); 
	
	JPanel infoPanel;
		JLabel infoClock;
	
	JPanel memoPanel;
		JLabel selectedDate;
		JTextArea memoArea;
		JScrollPane memoAreaSP;
		JPanel memoSubPanel;
		JButton saveBut; 
		JButton delBut; 
		JButton clearBut;
	

	//상수, 메세지
	final String WEEK_DAY_NAME[] = { "SUN", "MON", "TUE", "WED", "THR", "FRI", "SAT" };
	final String title = "메모 달력 ver 1.0";


	private JPanel panelInfo;


	private Vector<String> returnColumn;


	private DefaultTableModel model;


	private JTable table;

	private JLabel lblLogo;

	private JPanel panelTitle;

	private JPanel panelTop;

	public static void main(String[] args){
		DB.init();
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new MemoCalendar();
			}
		});
	}
	public MemoCalendar() { //구성요소 순으로 정렬되어 있음. 각 판넬 사이에 빈줄로 구별
		
		mainFrame = new JFrame(title);
		mainFrame.setBackground(Color.white);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(1070,550);
		mainFrame.setLocationRelativeTo(null);
		
		try{
			UIManager.setLookAndFeel ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//LookAndFeel Windows 스타일 적용
			SwingUtilities.updateComponentTreeUI(mainFrame) ;
		}catch(Exception e){
		}
		
		panelTop = new JPanel();
		panelTop.setBackground(Color.white);
		panelTop.setLayout(new BorderLayout());
		
		calOpPanel = new JPanel();
		calOpPanel.setBackground(Color.white);
		calOpPanel.setLayout(new BorderLayout());
		
		panelTitle = new JPanel();
		panelTitle.setBackground(Color.white);
		panelTitle.setLayout(new GridLayout(1, 1));
		ImageIcon icontitle = new ImageIcon("libs/폼로고.jpg");
		Image changeIcon = icontitle.getImage().getScaledInstance(350, 50, Image.SCALE_SMOOTH);
		ImageIcon lblIcontitle = new ImageIcon(changeIcon);
		
		lblLogo = new JLabel(lblIcontitle);
		
		calOpPanel.setBackground(Color.white);
			todayBut = new JButton("Today");
			todayBut.setToolTipText("Today");
			todayBut.addActionListener(lForCalOpButtons);
			
			todayLab = new JLabel(today.get(Calendar.MONTH)+1+"/"+today.get(Calendar.DAY_OF_MONTH)+"/"+today.get(Calendar.YEAR));
			lYearBut = new JButton("<<");
			lYearBut.setToolTipText("Previous Year");
			lYearBut.addActionListener(lForCalOpButtons);
			lMonBut = new JButton("<");
			lMonBut.setToolTipText("Previous Month");
			lMonBut.addActionListener(lForCalOpButtons);
			curMMYYYYLab = new JLabel("<html><table width=100><tr><th><font size=5>"+((calMonth+1)<10?"&nbsp;":"")+(calMonth+1)+" / "+calYear+"</th></tr></table></html>");
			nMonBut = new JButton(">");
			nMonBut.setToolTipText("Next Month");
			nMonBut.addActionListener(lForCalOpButtons);
			nYearBut = new JButton(">>");
			nYearBut.setToolTipText("Next Year");
			nYearBut.addActionListener(lForCalOpButtons);
			
			calOpPanel.setLayout(new GridBagLayout());
			calOpPanel.setBackground(Color.white);
			GridBagConstraints calOpGC = new GridBagConstraints();
			calOpGC.gridx = 1;
			calOpGC.gridy = 1;
			calOpGC.gridwidth = 2;
			calOpGC.gridheight = 1;
			calOpGC.weightx = 1;
			calOpGC.weighty = 1;
			calOpGC.insets = new Insets(5,5,0,0);
			calOpGC.anchor = GridBagConstraints.WEST;
			calOpGC.fill = GridBagConstraints.NONE;
			calOpPanel.add(todayBut,calOpGC);
			calOpGC.gridwidth = 3;
			calOpGC.gridx = 2;
			calOpGC.gridy = 1;
			calOpPanel.add(todayLab,calOpGC);
			calOpGC.anchor = GridBagConstraints.CENTER;
			calOpGC.gridwidth = 1;
			calOpGC.gridx = 1;
			calOpGC.gridy = 2;
			calOpPanel.add(lYearBut,calOpGC);
			calOpGC.gridwidth = 1;
			calOpGC.gridx = 2;
			calOpGC.gridy = 2;
			calOpPanel.add(lMonBut,calOpGC);
			calOpGC.gridwidth = 2;
			calOpGC.gridx = 3;
			calOpGC.gridy = 2;
			calOpPanel.add(curMMYYYYLab,calOpGC);
			calOpGC.gridwidth = 1;
			calOpGC.gridx = 5;
			calOpGC.gridy = 2;
			calOpPanel.add(nMonBut,calOpGC);
			calOpGC.gridwidth = 1;
			calOpGC.gridx = 6;
			calOpGC.gridy = 2;
			calOpPanel.add(nYearBut,calOpGC);
		
		calPanel=new JPanel();
		calPanel.setBackground(Color.white);
		// 요일 글자 색
			weekDaysName = new JButton[7];
			for(int i=0 ; i<CAL_WIDTH ; i++){	//CAL_WIDTH = 7 ( 일~토 )
				weekDaysName[i]=new JButton(WEEK_DAY_NAME[i]);
				weekDaysName[i].setBorderPainted(false);
				weekDaysName[i].setContentAreaFilled(false);
				weekDaysName[i].setForeground(Color.WHITE);
				if(i == 0) weekDaysName[i].setBackground(new Color(200, 50, 50));	// 일요일
				else if (i == 6) weekDaysName[i].setBackground(new Color(50, 100, 200));	//토요일
				else weekDaysName[i].setBackground(new Color(150, 150, 150));	//월요일~금요일
				weekDaysName[i].setOpaque(true);	//투명도
				weekDaysName[i].setFocusPainted(false);
				calPanel.add(weekDaysName[i]);
			}
			for(int i=0 ; i<CAL_HEIGHT ; i++){	//CAL_HEIGHT = 6 / 1~ 31 ...까지 추가
				for(int j=0 ; j<CAL_WIDTH ; j++){
					dateButs[i][j]=new JButton();
					dateButs[i][j].setBorderPainted(false);
					dateButs[i][j].setContentAreaFilled(false);
					dateButs[i][j].setBackground(Color.WHITE);
					dateButs[i][j].setOpaque(true);
					dateButs[i][j].addActionListener(lForDateButs);
					calPanel.add(dateButs[i][j]);
				}
			}
			calPanel.setLayout(new GridLayout(0,7,2,2));
			calPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
			showCal(); // 달력을 표시

		//calOpPanel, calPanel을  frameSubPanelWest에 배치
		JPanel frameSubPanelWest = new JPanel();
		frameSubPanelWest.setBackground(Color.white);
		Dimension calOpPanelSize = calOpPanel.getPreferredSize();
		calOpPanelSize.height = 90;
		calOpPanel.setPreferredSize(calOpPanelSize);
		frameSubPanelWest.setLayout(new BorderLayout());
		frameSubPanelWest.add(calOpPanel,BorderLayout.NORTH);
		frameSubPanelWest.add(calPanel,BorderLayout.CENTER);
		mainFrame.setBackground(Color.white);
		
		panelTop.add(lblLogo, BorderLayout.NORTH);
		panelTop.add(frameSubPanelWest, BorderLayout.CENTER);
		
		
		//infoPanel, 테이블을  frameSubPanelEast에 배치
		panelInfo = new JPanel();
		panelInfo.setBackground(new Color(0xFFFFFF));
		panelInfo.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

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

		// 테이블 생성
		model.setNumRows(0);

		String returnSelect = "select return.RETURNID, um.UMBRELLAID, st.STUDENTID, st.NAME, TO_CHAR(rental.rentaldate, \'YYYY-MM-DD\'), TO_CHAR(return.returndate, \'YYYY-MM-DD\') "
				+ " from RETURN return, RENTAL rental, STUDENT st, UMBRELLA um where return.rentalid = rental.rentalid"
				+ "  and rental.studentid = st.studentid and rental.umbrellaid = um.umbrellaid ORDER BY RETURN.RETURNID";

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
		JScrollPane sc = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sc.setPreferredSize(new Dimension(600, 470));
		panelInfo.add(sc);
		
		
//		JPanel frameSubPanelEast = new JPanel();
//		frameSubPanelEast.setLayout(new BorderLayout());
//
//		Dimension frameSubPanelWestSize = frameSubPanelWest.getPreferredSize();
//		frameSubPanelWestSize.width = 410;
//		frameSubPanelWest.setPreferredSize(frameSubPanelWestSize);
//		
		
		//frame에 전부 배치
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(panelTop, BorderLayout.WEST);
		mainFrame.add(panelInfo, BorderLayout.CENTER);
		mainFrame.setVisible(true);

		focusToday(); //현재 날짜에 focus를 줌 (mainFrame.setVisible(true) 이후에 배치해야함)
		
	}
	private void focusToday(){
		if(today.get(Calendar.DAY_OF_WEEK) == 1)
			dateButs[today.get(Calendar.WEEK_OF_MONTH)][today.get(Calendar.DAY_OF_WEEK)-1].requestFocusInWindow();
		else
			dateButs[today.get(Calendar.WEEK_OF_MONTH)-1][today.get(Calendar.DAY_OF_WEEK)-1].requestFocusInWindow();
	}

	private void showCal(){
		for(int i=0;i<CAL_HEIGHT;i++){
			for(int j=0;j<CAL_WIDTH;j++){
				String fontColor="black";
				if(j==0) fontColor="red";
				else if(j==6) fontColor="blue";
				
				File f =new File("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDates[i][j]<10?"0":"")+calDates[i][j]+".txt");
				if(f.exists()){
					dateButs[i][j].setText("<html><b><font color="+fontColor+">"+calDates[i][j]+"</font></b></html>");
				}
				else dateButs[i][j].setText("<html><font color="+fontColor+">"+calDates[i][j]+"</font></html>");

				JLabel todayMark = new JLabel("<html><font color=green>*</html>");
				dateButs[i][j].removeAll();
				if(calMonth == today.get(Calendar.MONTH) &&
						calYear == today.get(Calendar.YEAR) &&
						calDates[i][j] == today.get(Calendar.DAY_OF_MONTH)){
					dateButs[i][j].add(todayMark);
					dateButs[i][j].setToolTipText("Today");
				}
				
				if(calDates[i][j] == 0) dateButs[i][j].setVisible(false);
				else dateButs[i][j].setVisible(true);
			}
		}
	}
	private class ListenForCalOpButtons implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == todayBut){
				setToday();
				lForDateButs.actionPerformed(e);
				focusToday();
			}
			else if(e.getSource() == lYearBut) moveMonth(-12);
			else if(e.getSource() == lMonBut) moveMonth(-1);
			else if(e.getSource() == nMonBut) moveMonth(1);
			else if(e.getSource() == nYearBut) moveMonth(12);
			
			curMMYYYYLab.setText("<html><table width=100><tr><th><font size=5>"+((calMonth+1)<10?"&nbsp;":"")+(calMonth+1)+" / "+calYear+"</th></tr></table></html>");
			showCal();
		}
	}
	private class listenForDateButs implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int k=0,l=0;
			for(int i=0 ; i<CAL_HEIGHT ; i++){
				for(int j=0 ; j<CAL_WIDTH ; j++){
					if(e.getSource() == dateButs[i][j]){ 
						k=i;
						l=j;
					}
				}
			}
	
			if(!(k ==0 && l == 0)) calDayOfMon = calDates[k][l]; //today버튼을 눌렀을때도 이 actionPerformed함수가 실행되기 때문에 넣은 부분

			cal = new GregorianCalendar(calYear,calMonth,calDayOfMon);
			String dDayString = new String();
			int dDay=((int)((cal.getTimeInMillis() - today.getTimeInMillis())/1000/60/60/24));
			if(dDay == 0 && (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR)) 
					&& (cal.get(Calendar.MONTH) == today.get(Calendar.MONTH))
					&& (cal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH))) dDayString = "Today"; 
			else if(dDay >=0) dDayString = "D-"+(dDay+1);
			else if(dDay < 0) dDayString = "D+"+(dDay)*(-1);
			
		}
	}
	private class ThreadConrol extends Thread{
		public void run(){
			boolean msgCntFlag = false;
			int num = 0;
			String curStr = new String();
		}
	}
}