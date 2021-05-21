package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.tools.javac.Main;

public class UmbrellaMain extends JFrame {

	private JPanel panelBack;
	private ImageIcon icon;
	private JLabel lblImg;
	private Image img;
	private Image changeImg;
	private ImageIcon changeIcon;
	private JPanel panelBtn;
	private ImageIcon drop1;
	private JButton btn1;
	private JButton btn3;
	private JButton btn2;
	private ImageIcon drop2;
	private ImageIcon drop3;

	public UmbrellaMain(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);	//현재 클래스에 대해서 상대적인 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//레이아웃
		setLayout(new BorderLayout());
		
		panelBack = new JPanel();
		
		
		panelBack.setBackground(Color.white);
		icon = new ImageIcon("libs/back.gif");
		img = icon.getImage();
		changeImg = img.getScaledInstance(880, 580, Image.SCALE_SMOOTH);
		changeIcon = new ImageIcon(changeImg);
		
		lblImg = new JLabel(changeIcon);
		panelBack.add(lblImg, BorderLayout.CENTER);
		
		Font font = new Font("맑은 고딕", Font.BOLD, 40);
		JLabel lbl = new JLabel("우산 대여 프로그램");
		lbl.setForeground(new Color(0xFF1291));;
		lbl.setFont(font);
		
		lbl.setBounds(270, 50, 350, 50);
		lblImg.add(lbl);
		
		
		
		panelBtn = new JPanel();
		panelBtn.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 0));
		panelBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelBtn.setBackground(Color.white);
		
		
		
		drop1 = new ImageIcon("libs/dropC.png");
		btn1 = new JButton("대여", drop1);
		btn1.setVerticalTextPosition(JButton.BOTTOM);
		btn1.setContentAreaFilled(false);	//투명한 배경색
		btn1.setBorderPainted(false);		// 테두리 없애기
		
		drop2 = new ImageIcon("libs/dropB.png");
		btn2 = new JButton("반납", drop2);
		btn2.setVerticalTextPosition(JButton.BOTTOM);
		btn2.setContentAreaFilled(false);	
		btn2.setBorderPainted(false);		
		
		drop3 = new ImageIcon("libs/dropP.png");
		btn3 = new JButton("관리자", drop3);
		btn3.setVerticalTextPosition(JButton.BOTTOM);
		btn3.setContentAreaFilled(false);
		btn3.setBorderPainted(false);
		
		panelBtn.add(btn1);
		panelBtn.add(btn2);
		panelBtn.add(btn3);
		
		add(panelBack, BorderLayout.CENTER);
		add(panelBtn, BorderLayout.SOUTH);
		setVisible(true);
	}

	public static void main(String[] args) {
		new UmbrellaMain("우산 대여 프로그램", 900, 600);
	}

}
