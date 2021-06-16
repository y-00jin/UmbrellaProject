package main.umStats;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.NumberFormat.Style;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import main.DB;
import main.style.BtnFont;
import main.umReturn.DateLabelFormatter;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class Stats extends JFrame implements ActionListener {

	private JPanel panelTop;
	private JPanel panelStats;
	private JPanel panelClose;
	private JLabel lblTitle;
	private JPanel panelSearch;
	private UtilDateModel model;
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	private JButton btnSearch;
	private JButton btnClose;
	private JPanel panelCenter;
	private JPanel panelInfo;
	private JPanel panelGraph;
	private JLabel lblRental;
	private JLabel lblRenResult;
	private JLabel lblReturn;
	private JLabel lblNoReturn;
	private JButton btnDraw;
	private DrawingPanel drawpanel;
	private JPanel panelTitle;
	private JButton btnDetail;
	private JLabel lblRetResult;
	private JLabel lblNoRetResult;
	private Date SelectedDate;
	private String rentalCount;
	private SimpleDateFormat dateFormatter;
	private String returnCount;
	private String noReturnCount;
	private JPanel panelAll;
	private JLabel lblLogo;
	private JPanel panelLogo;

	public Stats(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		// setLocation(800, 300); 직접 자리 배치
		setLocationRelativeTo(this); // 현재 클래스에 대해서 상대적인 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 탑_타이틀, 검색
		addTop();

		// 센터_그래프
		addCenter();

		add(panelTop, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);

		setVisible(true);
	}

	// 탑 패널
	private void addTop() {

		panelTop = new JPanel();
		panelTop.setBackground(Color.WHITE); // 배경색
		panelTop.setLayout(new BorderLayout());

		// 분석 타이틀
		addLogo();


		// 검색 패널
		addSearch();


		panelTop.add(panelLogo, BorderLayout.NORTH);
		panelTop.add(panelSearch, BorderLayout.SOUTH);

	}

	// 타이틀 생성
	private void addLogo() {

		panelLogo = new JPanel();
		panelLogo.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelLogo.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
		panelLogo.setBackground(Color.white);	//배경색
		
		ImageIcon icontitle = new ImageIcon("libs/폼로고.jpg");
		Image changeIcon = icontitle.getImage().getScaledInstance(450, 50, Image.SCALE_SMOOTH);
		ImageIcon lblIcontitle = new ImageIcon(changeIcon);
		
		lblLogo = new JLabel(lblIcontitle);
		panelLogo.add(lblLogo);


	}


	// 검색
	private void addSearch() {
		// 검색
		panelSearch = new JPanel();
		panelSearch.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		panelSearch.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
		panelSearch.setBackground(Color.WHITE);

		// 날짜 데이터피커
		model = new UtilDateModel(); // 데이터모델 객체 생성
		datePanel = new JDatePanelImpl(model); // 모델 객체를 이용해 JDatePanelImpl 생성 -> 달력 생성
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		// 생성된 달력을 텍스트필드와 ...버튼으로 나타냄 / DateLabelFormatter() 생성자를 이용해 YYYY-MM-DD로 텍스트필드에
		// 출력되는 유형 바꿈
		datePanel.addActionListener(this);
		panelSearch.add(datePicker);

		// 검색 버튼
		btnSearch = new JButton("검색");
		BtnFont.BtnStyle(btnSearch);
		panelSearch.add(btnSearch);

	}

	// 그래프 그리는 센터 패널
	private void addCenter() {
		// 센터 그래프
		panelCenter = new JPanel();
		panelCenter.setLayout(new BorderLayout());

		// 그래프를 그릴 패널을 만든다.
		drawpanel = new DrawingPanel();

		// 정보 입력 패널 만든다.
		panelInfo = new JPanel();
		panelInfo.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		panelInfo.setBackground(Color.white);
		panelInfo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

		Font fontLbl = new Font("굴림", Font.BOLD, 13);
		lblRental = new JLabel("대여 :");
		lblRental.setFont(fontLbl);
		panelInfo.add(lblRental);

		lblRenResult = new JLabel("");
		lblRenResult.setFont(fontLbl);
		panelInfo.add(lblRenResult);

		lblReturn = new JLabel("|  반납 :");
		lblReturn.setFont(fontLbl);
		panelInfo.add(lblReturn);

		lblRetResult = new JLabel("");
		lblRetResult.setFont(fontLbl);
		panelInfo.add(lblRetResult);

		lblNoReturn = new JLabel("|  미반납 :");
		lblNoReturn.setFont(fontLbl);
		panelInfo.add(lblNoReturn);

		lblNoRetResult = new JLabel("");
		lblNoRetResult.setFont(fontLbl);
		panelInfo.add(lblNoRetResult);

		btnDetail = new JButton("자세히 >>");

		BtnFont.BtnStyle(btnDetail);
		panelInfo.add(btnDetail);

		// 버튼 클릭 시 그래프 그리기
		btnSearch.addActionListener(new DrawAction(lblRenResult, lblRetResult, lblNoRetResult, drawpanel));

		panelCenter.add(panelInfo, BorderLayout.SOUTH);
		panelCenter.add(drawpanel, BorderLayout.CENTER);

	}

	public static void main(String[] args) {
		DB.init();
		new Stats("통계", 800, 600);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnClose) {
			dispose();
		} else if (obj == datePanel) {

			// 특정 월에 대여한 사람 수 얻어오기
			RentalSelect();
			lblRenResult.setText(rentalCount); // 총 대여한 사람 수

			ReturnSelect();
			lblRetResult.setText(returnCount);

			NoReturnSelect();

			lblNoRetResult.setText(noReturnCount);
		}
	}

	private void RentalSelect() {
		String datePattern = "MM"; // 데이터 포맷 형식 지정
		dateFormatter = new SimpleDateFormat(datePattern); // 데이터 포맷 형식 지정한 객체 생성

		SelectedDate = (Date) datePicker.getModel().getValue(); // 클릭된 날짜 값 가져오기

		// 대여한 사람 수 얻어오는 select 문
		String rentalSelect = "SELECT COUNT(*) FROM RENTAL rental WHERE rental.RENTALID IN (SELECT r.RENTALID "
				+ "FROM RENTAL r WHERE TO_CHAR(r.RENTALDATE , 'mm') = '" + dateFormatter.format(SelectedDate) + "')";

		ResultSet rs = DB.getResultSet(rentalSelect); // select하기
		try {
			while (rs.next()) {
				rentalCount = rs.getString(1); // 총 대여한 사람 수 얻어오기
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void ReturnSelect() {
		String returnSelect = "SELECT COUNT(*) FROM \"RETURN\" r2 WHERE r2.RETURNID IN (SELECT r.RETURNID "
				+ "FROM \"RETURN\" r WHERE TO_CHAR(r.RETURNDATE , 'mm') = '" + dateFormatter.format(SelectedDate)
				+ "')";
		ResultSet rs = DB.getResultSet(returnSelect); // select하기
		try {
			while (rs.next()) {
				returnCount = rs.getString(1); // 총 대여한 사람 수 얻어오기
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void NoReturnSelect() {
		String NoReturnSelect = "SELECT COUNT(*) FROM BLOCKVIEW b WHERE b.STUDENTID IN (SELECT b.STUDENTID FROM BLOCKVIEW b2 "
				+ "WHERE TO_CHAR(b2.RENTALDATE , 'mm') = '" + dateFormatter.format(SelectedDate) + "')";
		ResultSet rs = DB.getResultSet(NoReturnSelect);
		try {
			while (rs.next()) {
				noReturnCount = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
