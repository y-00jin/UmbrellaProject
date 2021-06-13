package main.umAnalysis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat.Style;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

	public Stats(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		// setLocation(800, 300); 직접 자리 배치
		setLocationRelativeTo(this); // 현재 클래스에 대해서 상대적인 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 탑
		panelTop = new JPanel();
		panelTop.setBackground(Color.WHITE);
		panelTop.setLayout(new BorderLayout());

		panelTitle = new JPanel();
		panelTitle.setBackground(new Color(0xDEE5F3));
		panelTitle.setLayout(new BorderLayout());

		// 분석 타이틀 패널
		panelStats = new JPanel();
		panelStats.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelStats.setBackground(new Color(0xDEE5F3));

		// 분석 타이틀
		Font lblTitleFont = new Font("HY헤드라인M", Font.PLAIN, 20);
		lblTitle = new JLabel("통계");
		lblTitle.setBorder(BorderFactory.createEmptyBorder(8, 10, 0, 0));
		lblTitle.setFont(lblTitleFont);
		panelStats.add(lblTitle);

		// 닫기
		panelClose = new JPanel();

		panelClose.setLayout(new FlowLayout(FlowLayout.RIGHT));
		// panelClose.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		panelClose.setBackground(new Color(0xDEE5F3));

		ImageIcon iconExit = new ImageIcon("libs/exit.png");
		Image changeIcon = iconExit.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon btnIcon = new ImageIcon(changeIcon);

		btnClose = new JButton(btnIcon);
		btnClose.setBackground(new Color(0xDEE5F3));
		// btnClose.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 10));
		btnClose.setBorderPainted(false);
		btnClose.addActionListener(this);

		panelClose.add(btnClose);

		// 검색
		panelSearch = new JPanel();
		panelSearch.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		panelSearch.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
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
		btnSearch.addActionListener(this);
		panelSearch.add(btnSearch);

		panelTitle.add(panelStats, BorderLayout.WEST);
		panelTitle.add(panelClose, BorderLayout.EAST);
		panelTitle.add(panelSearch, BorderLayout.SOUTH);
		panelTop.add(panelTitle);
		add(panelTop, BorderLayout.NORTH);

		// 센터 그래프
		panelCenter = new JPanel();
		panelCenter.setLayout(new BorderLayout());

		// 그래프를 그릴 패널을 만든다.
		drawpanel = new DrawingPanel();

		// 정보 입력 패널 만든다.
		panelInfo = new JPanel();
		panelInfo.setBackground(Color.white);
		panelInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		lblRental = new JLabel("대여 : ");
		panelInfo.add(lblRental);

		lblRenResult = new JLabel("");
		panelInfo.add(lblRenResult);

		lblReturn = new JLabel("반납 : ");
		panelInfo.add(lblReturn);

		lblRetResult = new JLabel("");
		panelInfo.add(lblRetResult);

		lblNoReturn = new JLabel("미반납 : ");
		panelInfo.add(lblNoReturn);

		lblNoRetResult = new JLabel("");
		panelInfo.add(lblNoRetResult);

		btnDetail = new JButton("자세히 >>");
		BtnFont.BtnStyle(btnDetail);
		panelInfo.add(btnDetail);
		
		//버튼 클릭 시 그래프 그리기
		btnSearch.addActionListener(new DrawAction(lblRenResult, lblRetResult, lblNoRetResult, drawpanel));
		
		panelCenter.add(panelInfo, BorderLayout.SOUTH);
		panelCenter.add(drawpanel, BorderLayout.CENTER);

		add(panelCenter, BorderLayout.CENTER);

		setVisible(true);
	}

	public static void main(String[] args) {
		new Stats("통계", 600, 500);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnClose) {
			dispose();
		} else if(obj==btnSearch) {
			new DrawAction(lblRenResult, lblRetResult, lblNoRetResult, drawpanel);
		} else if(obj==datePanel) {
			lblRenResult.setText("80");
			lblRetResult.setText("60");
			lblNoRetResult.setText("20");
		}

	}
 
}
