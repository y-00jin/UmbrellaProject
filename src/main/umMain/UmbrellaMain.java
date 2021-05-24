package main.umMain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class UmbrellaMain extends JFrame {

	private JPanel panelInfo;
	private JPanel panelBtn;
	private ImageIcon drop1;
	private JButton btn1;
	private JButton btn3;
	private JButton btn2;
	private ImageIcon drop2;
	private ImageIcon drop3;
	private JLabel lblTitle;


	
	public UmbrellaMain(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);	//현재 클래스에 대해서 상대적인 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		// 정보 패널 생성
		panelInfo = new JPanel();
		panelInfo.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));	// 레이아웃 센터, hgap, vgap 설정
		panelInfo.setBackground(new Color(0xD4F4FA));	//Color rgb 값 가져와 배경색 설정
		panelInfo.setBorder(BorderFactory.createEmptyBorder(130, 0, 0, 0));	//마진 설정
		
		
		Font fontTitle = new Font("휴먼둥근헤드라인", Font.PLAIN, 50);	// [ 우산 대여 프로그램 ] 타이틀에 들어갈 폰트 설정
		lblTitle = new JLabel("[ 우산 대여 프로그램 ]");	// label 생성
		lblTitle.setForeground(new Color(0x353535));	//label 색상 설정
		lblTitle.setFont(fontTitle);	// 설정한 font 적용
		
		panelInfo.add(lblTitle);
		
		
		Font font2 = new Font("휴먼둥근헤드라인", Font.PLAIN, 30);	// [ 컴퓨터시스템과_도담도담 ] 에 들어갈 폰트 설정
		JLabel lbl2 = new JLabel("컴퓨터시스템과_도담도담");	//label 생성
		lbl2.setForeground(new Color(0x595959));	//label 색상 설정	
		lbl2.setFont(font2);	//설정한 font 적용
		
		panelInfo.add(lbl2);
		
		
		
		
		// 버튼 생성
		panelBtn = new JPanel();
		panelBtn.setBackground(new Color(0xD4F4FA));	//패널 배경색 설정
		
		drop1 = new ImageIcon("libs/umbrella1.png");
		btn1 = new JButton(drop1);
		btn1.setVerticalTextPosition(JButton.BOTTOM);
		btn1.setContentAreaFilled(false);	//투명한 배경색
		btn1.setBorderPainted(false);		// 테두리 없애기
		
		drop2 = new ImageIcon("libs/umbrella2.png");
		btn2 = new JButton( drop2);
		btn2.setVerticalTextPosition(JButton.BOTTOM);
		btn2.setContentAreaFilled(false);	
		btn2.setBorderPainted(false);		
		
		drop3 = new ImageIcon("libs/umbrella3.png");
		btn3 = new JButton(drop3);
		btn3.setVerticalTextPosition(JButton.BOTTOM);
		btn3.setContentAreaFilled(false);
		btn3.setBorderPainted(false);
	
		// 정보
		panelBtn.add(btn1);
		panelBtn.add(btn2);
		panelBtn.add(btn3);
	
		
		add(panelInfo, BorderLayout.CENTER);
		add(panelBtn, BorderLayout.SOUTH);
		setVisible(true);
	}

	public static void main(String[] args) {
		new UmbrellaMain("우산 대여 프로그램", 900, 600);
	}

}
