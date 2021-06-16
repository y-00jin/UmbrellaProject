// 그림 그리는 이벤트 (다른곳에서 호출가능)
package main.umStats;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DrawAction implements ActionListener {

	JLabel txt1, txt2, txt3;
	DrawingPanel drawpanel;

	public DrawAction(JLabel txt1, JLabel txt2, JLabel txt3, DrawingPanel drawpanel) {	//라벨 값 입력 받고 저장함
		this.txt1 = txt1;
		this.txt2 = txt2;
		this.txt3 = txt3;
		this.drawpanel = drawpanel;	// 그림 그릴 패널
	}

	public void actionPerformed(ActionEvent e) {
		try {
			// 정보를 입력받아 정수값으로 변환
			int umRental = Integer.parseInt(txt1.getText());
			int umReturn = Integer.parseInt(txt2.getText());
			int umNoReturn = Integer.parseInt(txt3.getText());

			drawpanel.setScore(umRental, umReturn, umNoReturn);

			// 그래프를 그리는 패널의 paint()를 간접적으로 호출
			drawpanel.repaint();

		} catch (NumberFormatException n) {

		}
	}
}