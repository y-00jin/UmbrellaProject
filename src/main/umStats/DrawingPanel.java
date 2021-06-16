package main.umStats;

import java.awt.*;
import javax.swing.*;


public class DrawingPanel extends JPanel {
	int umRental, umReturn, umNoReturn;

	public void paint(Graphics g) {// 페인트는 그래픽스 객체를 가지고 있는 메소드
		
		g.clearRect(0, 0, getWidth(), getHeight());	// 패널 전체를 하얀색으로 표현
		g.setColor(Color.gray);
		g.drawLine(230, 280, 580, 280); // 그래프의 가로 길이
		g.drawLine(230, 60, 230, 280); // 그래프의 세로 길이
		

		for (int cnt = 1; cnt <= 10; cnt++) {
			g.setColor(Color.gray);
			// 좌표값을 10의 간격으로 10~100까지 출력
			g.drawString(cnt * 10 + "", 190, 285 - 20 * cnt); // 스트링타입으로 변환시켜주기 위해 +"" 추가
			// 좌표값을 10의 간격의 라인으로 출력
			g.drawLine(230, 280 - 20 * cnt, 580, 280 - 20 * cnt);

		}
		Font fontBtn = new Font("HY헤드라인M", Font.PLAIN, 15);
		g.setFont(fontBtn);
		g.drawString("대여", 285, 315);
		g.drawString("반납", 385, 315);
		g.drawString("미반납", 483, 315);
		g.setColor(new Color(178,204,255));
		
		if (umRental > 0) {
			
			g.fillRect(275, 280 - umRental * 2, 50, umRental * 2);
		}

		if (umReturn > 0) {
			g.fillRect(375, 280 - umReturn * 2, 50, umReturn * 2);
		}

		if (umNoReturn > 0) {
			g.fillRect(480, 280 - umNoReturn * 2, 50, umNoReturn * 2);
		}
	}


	void setScore(int umRental, int umReturn, int umNoReturn) {
		this.umRental = umRental;
		this.umReturn = umReturn;
		this.umNoReturn = umNoReturn;
	}

}