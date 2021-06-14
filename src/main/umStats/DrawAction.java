package main.umStats;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DrawAction implements ActionListener {
 
 JLabel txt1, txt2, txt3;
 DrawingPanel drawpanel;
 
 public DrawAction(JLabel txt1, JLabel txt2, JLabel txt3, DrawingPanel drawpanel){
  this.txt1 = txt1;
  this.txt2 = txt2;
  this.txt3 = txt3;
  this.drawpanel = drawpanel;
 }
 
 public void actionPerformed(ActionEvent e){
  try{
	  
  int umRental = Integer.parseInt(txt1.getText());
  int umReturn = Integer.parseInt(txt2.getText());
  int umNoReturn = Integer.parseInt(txt3.getText());
  
  drawpanel.setScore(umRental, umReturn, umNoReturn);
  
  //그래프를 그리는 패널의 paint()를 간접적으로 호출
  drawpanel.repaint();
  
  }catch(NumberFormatException n){
   System.out.println("잘못된 숫자 포멧입니다.");
   
  }
 }
}