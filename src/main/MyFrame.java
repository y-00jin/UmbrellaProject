package main;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MyFrame extends JFrame {

	public MyFrame(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		//setLocation(800, 300);	직접 자리 배치
		setLocationRelativeTo(this);	//현재 클래스에 대해서 상대적인 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//레이아웃
		setLayout(new BorderLayout());
		
		
		setVisible(true);
	}

	public static void main(String[] args) {
		new MyFrame("My 프레임", 300, 200);
	}

}
