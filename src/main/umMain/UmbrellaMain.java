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
import main.umReturn.Return;

public class UmbrellaMain extends JFrame implements ActionListener {

	private JPanel panelTitle, panelBtn;
	private JButton btnRental, btnAdmin, btnReturn;
	private JLabel lblTitle, lblSubTitle;

	public UmbrellaMain(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this); // 현재 클래스에 대해서 상대적인 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 타이틀 생성
		addTitle();

		// 버튼 생성
		addBtn();

		add(panelTitle, BorderLayout.NORTH);
		add(panelBtn, BorderLayout.CENTER);
		setVisible(true);
	}

	
	// 타이틀 생성
	private void addTitle() {

		panelTitle = new JPanel();
		panelTitle.setLayout(new GridLayout(2, 1, 0, 10));
		panelTitle.setBackground(new Color(0xFFFFFF)); // Color rgb 값 가져와 배경색 설정
		panelTitle.setBorder(BorderFactory.createEmptyBorder(130, 0, 0, 0)); // 마진 설정

		Font fontTitle = new Font("HY헤드라인M", Font.PLAIN, 50); // 타이틀에 들어갈 폰트 설정
		lblTitle = new JLabel("[ 우산 대여 프로그램 ]"); // label 생성
		lblTitle.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
		lblTitle.setForeground(new Color(0X5A7CC0)); // label 색상 설정
		lblTitle.setFont(fontTitle); // 설정한 font 적용

		panelTitle.add(lblTitle);

		Font fontSubTitle = new Font("HY헤드라인M", Font.PLAIN, 30); // 서브 타이틀에 들어갈 폰트 설정
		lblSubTitle = new JLabel("컴퓨터시스템과_도담도담"); // label 생성
		lblSubTitle.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
		lblSubTitle.setForeground(new Color(0x7EA0E4)); // label 색상 설정
		lblSubTitle.setFont(fontSubTitle); // 설정한 font 적용

		panelTitle.add(lblSubTitle);

	}

	
	//버튼 생성
	private void addBtn() {

		panelBtn = new JPanel();
		panelBtn.setLayout(new GridLayout(1, 3, 10, 10));
		panelBtn.setBackground(new Color(0xFFFFFF)); // 패널 배경색 설정
		panelBtn.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100)); // 마진 설정

		// 이미지 생성 및 크기 설정
		ImageIcon icon = new ImageIcon("libs/umbrella.png");
		Image changeIcon = icon.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
		ImageIcon btnIcon = new ImageIcon(changeIcon);

		// 버튼 글자 스타일
		Font fontBtn = new Font("HY헤드라인M", Font.PLAIN, 20);

		// 버튼 생성
		btnRental = new JButton("대여", btnIcon);

		btnReturn = new JButton("반납", btnIcon);
		btnReturn.addActionListener(this);

		btnAdmin = new JButton("관리자", btnIcon);

		// 버튼에 스타일 적용
		JButton[] btnArr = { btnRental, btnReturn, btnAdmin };

		for (int i = 0; i < btnArr.length; i++) {

			btnArr[i].setFont(fontBtn); // 폰트 스타일 적용
			btnArr[i].setForeground(new Color(0x5D5D5D)); // 글자색
			btnArr[i].setVerticalTextPosition(SwingConstants.BOTTOM); // 글자 그림 아래쪽
			btnArr[i].setContentAreaFilled(false); // 투명한 배경색
			btnArr[i].setBorderPainted(false); // 테두리 없애기

		}

		panelBtn.add(btnRental);
		panelBtn.add(btnReturn);
		panelBtn.add(btnAdmin);

	}

	public static void main(String[] args) {
		DB.init();
		new UmbrellaMain("우산 대여 프로그램", 900, 600);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnReturn) {
			new Return("반납", 900, 600);
		}
	}
}