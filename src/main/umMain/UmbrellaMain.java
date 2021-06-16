package main.umMain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import main.DB;
import main.umAdmin.Admin;
import main.umRental.Rental;
import main.umReturn.Return;
import main.umStats.Stats;

public class UmbrellaMain extends JFrame implements ActionListener {

	private static boolean check;
	private JPanel panelTitle, panelBtn;
	private JButton btnRental, btnAdmin, btnReturn;
	private JLabel lblTitle, lblSubTitle;
	private JButton btnStats;
	private JPanel panelTop;
	private JPanel panelExit;
	private JButton btnExit;
	private JPanel panelAll;
	private JButton btnTitle;

	public UmbrellaMain(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this); // 현재 클래스에 대해서 상대적인 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 전체 패널(테두리를 주기 위함)
		panelAll = new JPanel();
		panelAll.setLayout(new BorderLayout());
		panelAll.setBorder(new LineBorder(Color.darkGray, 1));	//테두리
		
		// 탑 패널 생성
		addPanelTop();

		// 버튼 생성
		addBtn();

		panelAll.add(panelTop, BorderLayout.NORTH);
		panelAll.add(panelBtn, BorderLayout.CENTER);
		add(panelAll);
		
		setUndecorated(true); //타이틀 없애기
		setVisible(true);
	}

	// 타이틀 생성
	private void addPanelTop() {

		panelTop = new JPanel();
		panelTop.setLayout(new BorderLayout());
		// 나가기 버튼
		addExit();

		// 타이틀
		addTitle();
	
		
		panelTop.add(panelExit, BorderLayout.NORTH);
		panelTop.add(panelTitle, BorderLayout.CENTER);
	}

	// 나가기 버튼
	private void addExit() {

		panelExit = new JPanel();
		panelExit.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelExit.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 5));
		panelExit.setBackground(new Color(0xDEE5F3));

		// 이미지
		ImageIcon iconExit = new ImageIcon("libs/exit.png");
		Image changeIcon = iconExit.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon btnExitIcon = new ImageIcon(changeIcon);

		// 버튼
		btnExit = new JButton(btnExitIcon);
		btnExit.addActionListener(this);
		btnExit.setBackground(new Color(0xDEE5F3));
		btnExit.setBorderPainted(false);

		btnExit.addActionListener(this);
		panelExit.add(btnExit);

	}
	
	// 타이틀
	private void addTitle() {
		panelTitle = new JPanel();
		panelTitle.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelTitle.setBackground(new Color(0xFFFFFF)); // Color rgb 값 가져와 배경색 설정
		panelTitle.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0)); // 마진 설정
		
		ImageIcon icontitle1 = new ImageIcon("libs/메인로고.jpg");
		Image changeIcon1 = icontitle1.getImage().getScaledInstance(750, 80, Image.SCALE_SMOOTH);
		ImageIcon lblIcontitle1 = new ImageIcon(changeIcon1);
		
		lblTitle = new JLabel(lblIcontitle1); // label 생성

		panelTitle.add(lblTitle);
	}

	// 버튼 생성
	private void addBtn() {

		panelBtn = new JPanel();
		panelBtn.setLayout(new GridLayout(4, 1, 0, 40));
		panelBtn.setBackground(new Color(0xFFFFFF)); // 패널 배경색 설정
		panelBtn.setBorder(BorderFactory.createEmptyBorder(50, 260, 50, 260)); // 마진 설정

		// 이미지 생성 및 크기 설정
		ImageIcon icon = new ImageIcon("libs/umbrella (2).png");
		Image changeIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon btnIcon = new ImageIcon(changeIcon);

		// 버튼 글자 스타일
		Font fontBtn = new Font("HY헤드라인M", Font.PLAIN, 21);

		// 버튼 생성
		btnRental = new JButton("    대             여    ");
		btnRental.addActionListener(this);

		btnReturn = new JButton("    반             납    ");
		btnReturn.addActionListener(this);

		btnAdmin = new JButton("    관       리       자    ");
		btnAdmin.addActionListener(this);

		btnStats = new JButton("   분             석   ");
		btnStats.addActionListener(this);

		// 버튼에 스타일 적용
		JButton[] btnArr = { btnRental, btnReturn, btnStats ,btnAdmin};

		for (int i = 0; i < btnArr.length; i++) {

			btnArr[i].setFont(fontBtn); // 폰트 스타일 적용
			btnArr[i].setForeground(Color.white); // 글자색
			btnArr[i].setVerticalTextPosition(SwingConstants.BOTTOM); // 글자 그림 아래쪽
			if(i%2 ==0) {
				btnArr[i].setBackground(new Color(0x5872A5));
			}
			else {
				btnArr[i].setBackground(new Color(0x7C96C9));
			}
			
			//btnArr[i].setContentAreaFilled(false); // 투명한 배경색
			btnArr[i].setBorderPainted(false); // 테두리 없애기
		}

		panelBtn.add(btnRental);
		panelBtn.add(btnReturn);
		panelBtn.add(btnStats);
		panelBtn.add(btnAdmin);
		
	}

	public static void main(String[] args) {
		DB.init();
		addView();
		new UmbrellaMain("Umbrella Rental Program", 900, 600);
		
	}

	private static void addView() {
		check = false;
		
		String viewSelect = "SELECT * FROM BLOCKVIEW b ";
		DB.getResultSet(viewSelect);
		ResultSet rs = DB.getResultSet(viewSelect);
		try {
			while(rs.next()) {
				check = true;
			}
		} catch (SQLException e) {
			check = false;
			e.printStackTrace();
		}
		
		if(check == true) {
			String viewDrop = "DROP VIEW BLOCKVIEW";
			DB.executeQuery(viewDrop);
		}
		
		String viewCreate = "CREATE VIEW blockView AS SELECT s.DEPARTMENT , s.STUDENTID , s.NAME , s.PHONE ,r.RENTALDATE, r.RETURNDUEDATE, r.RETURNSTATE "
				+ "FROM STUDENT s , UMBRELLA u , RENTAL r WHERE s.STUDENTID = r.STUDENTID AND r.umbrellaid = u.umbrellaid AND r.returnstate = 'N' AND  (SELECT SYSDATE FROM dual) > r.RETURNDUEDATE";
		DB.executeQuery(viewCreate);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnRental) {
			new Rental("대여", 900, 600);
		} else if (obj == btnReturn) {
			new Return("반납", 900, 600);
		} else if (obj == btnAdmin) {
			new Admin("관리자", 900, 700);
		} else if(obj == btnStats) {
			new Stats("통계", 800, 600);
		} else if(obj == btnExit) {
			System.exit(0);
		} 

	}
}