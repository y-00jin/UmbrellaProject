package main.umReturn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Test extends JFrame implements ActionListener {

	private static Calendar cal = Calendar.getInstance();
	private String[] day = new String[] { "S", "M", "T", "W", "T", "F", "S" };
	private ArrayList<JButton> lstBtn = new ArrayList<JButton>();
	private int thisYear;
	private int thisMonth;
	private JLabel lblYear;
	private JLabel lblMonth;
	private JLabel lblday;
	private int dayX;
	private int dayY;
	private JButton btnDay;
	private JPanel jpTitle;
	private JLabel lblTitle;
	private JPanel jpSelect;
	private JTextField tfGo;
	private JLabel lblCome;
	private JPanel jpCalendarLeft;
	private static int todayYear = cal.get(Calendar.YEAR);
	private static int todayMonth = cal.get(Calendar.MONTH) + 1;
	private int setTime = 0;
	private int selectindex = 0;
	private JPanel jpBtn;
	private JButton btnReselect;
	private JButton btnLeft;
	private JButton btnRight;
	private JPanel jpSelect2;
	private JTextField tfGo1;
	private JPanel p11pp;
	private JPanel jpTitle1;

	public Test(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this); // 현재 클래스에 대해서 상대적인 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container c = getContentPane();
		c.setBackground(Color.white);

		setCalendarForm();

		
		setVisible(true);
	}


	private void setCalendar(JPanel jpCal, int year, int month, int x, int y) {
		jpCal = new JPanel();
		jpCal.setLayout(null);
		jpCal.setSize(290, 370);
		jpCal.setLocation(x, y);
		jpCal.setBackground(Color.WHITE);

		thisYear = year; // 현재 년
		thisMonth = month; // 현재 달

		cal.set(thisYear, thisMonth - 1, 1);// 캘린더 객체에 년도, 달 설정과 Date 1로 설정

		int sDayNum = cal.get(Calendar.DAY_OF_WEEK); // 1일의 요일 얻어오기
		int endDate = cal.getActualMaximum(Calendar.DATE); // 달의 마지막 일 얻기

		int intDateNum = 1; // 1일부터 날짜 시작

		lblYear = new JLabel(thisYear + "년"); // 년도 표시 라벨
		lblYear.setSize(150, 40);
		lblYear.setLocation(15, 2);
		lblMonth = new JLabel(thisMonth + "월"); // 달 표시 라벨
		lblMonth.setSize(80, 40);
		lblMonth.setLocation(65, 0);
		jpCal.add(lblYear);
		jpCal.add(lblMonth);

		for (int i = 0; i < 7; i++) { // 요일 추가
			lblday = new JLabel(day[i]); // day[i]로 요일 배열 추가
			lblday.setSize(100, 40);
			lblday.setLocation(40 * i + 16, 30); // 위치 설정
			jpCal.add(lblday);

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
				btnDay.setLocation(dayX, dayY);
				lstBtn.add(btnDay);
				jpCal.add(btnDay);

				dayX += 40;

			} else // date값의 버튼 출력
			{
				btnDay = new JButton(intDateNum + "");
				btnDay.addActionListener(this);
				btnDay.setBorderPainted(false); // 버튼 윤곽선 제거
				// btnDay.setBackground(new Color(153, 204, 255)); //버튼 색 설정
				btnDay.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // 안쪽 여백 설정
				btnDay.setBackground(Color.white); // 버튼 색 설정
				btnDay.setSize(40, 40);
				btnDay.setLocation(dayX, dayY);

				if ((i - 8) % 7 == 0) {
					btnDay.setForeground(Color.red); // 일요일은 빨간색 설정
				} else if (i % 7 == 0) {
					btnDay.setForeground(Color.blue); // 토요일은 파란색 설정
				} else {
					btnDay.setForeground(Color.black); // 나머지 검정색 설정
				}

				lstBtn.add(btnDay);
				jpCal.add(btnDay);

				intDateNum++; // 출력할 date 증가
				dayX += 40; // X좌표 증가로 옆으로 이동
			}

			if (i % 7 == 0) // i가 7로 나눠지면 다음 줄로 이동
			{
				dayY += 50; // Y좌표 증가로 다음 줄로 이동
				dayX = 0; // X좌표 초기화
			}

		}

		add(jpCal); // 판넬 추가

	}

	private void setCalendarForm() {

		jpTitle = new JPanel();
		jpTitle.setLayout(null);
		jpTitle.setSize(200, 60);
		jpTitle.setLocation(50, 40);
		jpTitle.setBackground(Color.white);

		// 제목라벨
		lblTitle = new JLabel("날짜 검색");
		lblTitle.setSize(150, 40);
		lblTitle.setLocation(16, 0);
		jpTitle.add(lblTitle);

		// 가는날, 오는날
		jpSelect = new JPanel(); // 날짜표시 판매
		jpSelect.setLayout(null);
		jpSelect.setSize(650, 40);
		jpSelect.setLocation(40, 100);
		jpSelect.setBackground(Color.white);

		tfGo = new JTextField(); // 가는 날짜 선택 시 확인
		tfGo.setSize(150, 40);
		tfGo.setLocation(0, 0);

		lblCome = new JLabel(" ~ "); // 오는날 라벨
		lblCome.setSize(150, 40);
		lblCome.setLocation(170, 0);

		tfGo1 = new JTextField(); // 가는 날짜 선택 시 확인
		tfGo1.setSize(150, 40);
		tfGo1.setLocation(190, 0);
		
		jpSelect.add(tfGo);
		jpSelect.add(lblCome);
		jpSelect.add(tfGo1);
		
		// 달력추가
		setCalendar(jpCalendarLeft, todayYear + setTime, todayMonth + setTime, 50, 160); // 이번달 달력 출력

		// 하단 버튼 판넬
		jpBtn = new JPanel();
		jpBtn.setLayout(null);
		jpBtn.setSize(650, 80);
		jpBtn.setLocation(50, 550);
		jpBtn.setBackground(Color.white);

		// 버튼 추가 및 설정
		btnReselect = new JButton("검색");
		btnReselect.setSize(230, 60);
		btnReselect.setLocation(40, 500);
		btnReselect.setBorderPainted(false); // 버튼 윤곽선 제거
		btnReselect.setBackground(new Color(10, 90, 150)); // 버튼 색 설정
		btnReselect.setForeground(Color.white);
		btnReselect.addActionListener(this);
		jpBtn.add(btnReselect);

		// 캘린더 변경 버튼
		btnLeft = new JButton("<");
		btnLeft.setSize(50, 310);
		btnLeft.setLocation(5, 200);
		btnLeft.setBorderPainted(false);
		btnLeft.setBackground(Color.white);
		btnLeft.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btnLeft.addActionListener(this);
		btnRight = new JButton(">");
		btnRight.setSize(50, 300);
		btnRight.setLocation(350, 200);
		btnRight.setBorderPainted(false);
		btnRight.setBackground(Color.white);
		btnRight.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btnRight.addActionListener(this);
		
		jpTitle1 = new JPanel();
		jpTitle1.setLayout(null);
		jpTitle1.setSize(700, 600);
		jpTitle1.setLocation(450, 40);
		jpTitle1.setBackground(Color.red);
		
		add(btnLeft);
		add(btnRight);
		add(jpTitle);
		add(jpTitle1);
		add(jpSelect);
		add(jpBtn);
		
		
		
	}

	public static void main(String[] args) {
		new Test("날짜 검색", 800, 800);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		String objText = e.getActionCommand();
		System.out.println(thisYear + "-" + thisMonth + "-" + objText);
		if (obj == btnReselect) {
			tfGo.setText("");
			selectindex = 0;
		} else if (obj == btnLeft) { //이전 달 달력 출력
			setTime -= 1;
			setCalendar(jpCalendarLeft, todayYear+setTime, todayMonth+setTime, 50, 160); 
		} else if (obj == btnRight) {
			setTime += 1;
			setCalendar(jpCalendarLeft, todayYear+setTime, todayMonth+setTime, 50, 160);
		} else {	//일 버튼을 클릭했을 경우
			if (selectindex == 0) {
			tfGo.setText(objText);
			selectindex++;
			} else if(selectindex == 1) {
				selectindex--;
			}
		}
	}

}
