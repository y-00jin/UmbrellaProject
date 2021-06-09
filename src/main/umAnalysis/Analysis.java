package main.umAnalysis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.text.NumberFormat.Style;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.style.BtnFont;
import main.umReturn.DateLabelFormatter;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class Analysis extends JFrame {

	private JPanel panelTop;
	private JPanel panelWest;
	private JPanel panelClose;
	private JLabel lblTitle;
	private JPanel panelSearch;
	private UtilDateModel model;
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	private JButton btnSearch;
	private JButton btnClose;
	private JPanel panelCenter;

	public Analysis(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		//setLocation(800, 300);	직접 자리 배치
		setLocationRelativeTo(this);	//현재 클래스에 대해서 상대적인 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		// 탑
		panelTop = new JPanel();
		panelTop.setBackground(Color.WHITE);
		panelTop.setLayout(new BorderLayout());
		
		// 왼쪽
		panelWest = new JPanel();
		panelWest.setBackground(Color.WHITE);
		panelWest.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelWest.setLayout(new GridLayout(2,1));
		
		//왼쪽_제목
		Font lblFont = new Font("HY헤드라인M", Font.PLAIN, 15);
		lblTitle = new JLabel("분석");
		lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		lblTitle.setFont(lblFont);
		panelWest.add(lblTitle);
		
		// 왼쪽_검색
		panelSearch = new JPanel();
		panelSearch.setBackground(Color.WHITE);
		
		// 날짜 데이터피커
		model = new UtilDateModel(); // 데이터모델 객체 생성
		datePanel = new JDatePanelImpl(model); // 모델 객체를 이용해 JDatePanelImpl 생성 -> 달력 생성
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		// 생성된 달력을 텍스트필드와 ...버튼으로 나타냄 / DateLabelFormatter() 생성자를 이용해 YYYY-MM-DD로 텍스트필드에
		// 출력되는 유형 바꿈
		panelSearch.add(datePicker);
		
		// 검색 버튼
		btnSearch = new JButton("검색");
		BtnFont.BtnStyle(btnSearch);
		
		panelSearch.add(btnSearch);
		
		panelWest.add(panelSearch);
		
		
		// 닫기
		panelClose = new JPanel();
		
		panelClose.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelClose.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		panelClose.setBackground(Color.white);

		ImageIcon iconExit = new ImageIcon("libs/exit.png");
		Image changeIcon = iconExit.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon btnIcon = new ImageIcon(changeIcon);

		btnClose = new JButton(btnIcon);
		btnClose.setBackground(Color.WHITE);
		btnClose.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 10));
		// BtnFont.BtnStyle(btnClose);
		btnClose.setBorderPainted(false);
		panelClose.add(btnClose);
		
		
		panelTop.add(panelWest, BorderLayout.WEST);
		panelTop.add(panelClose, BorderLayout.EAST);
		add(panelTop, BorderLayout.NORTH);
		
		
		// 센터 그래프
		panelCenter = new JPanel();
		
		
		add(panelCenter, BorderLayout.CENTER);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Analysis("통계", 900, 600);
	}

}