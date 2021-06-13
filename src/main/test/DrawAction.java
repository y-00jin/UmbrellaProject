package main.test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DrawAction implements ActionListener {
 
 JTextField txt1, txt2, txt3;
 DrawingPanel drawpanel;
 
 public DrawAction(JTextField txt1, JTextField txt2, JTextField txt3, DrawingPanel drawpanel){
  this.txt1 = txt1;
  this.txt2 = txt2;
  this.txt3 = txt3;
  this.drawpanel = drawpanel;
 }
 
 public void actionPerformed(ActionEvent e){
  try{
  int kor = Integer.parseInt(txt1.getText());
  int eng = Integer.parseInt(txt2.getText());
  int math = Integer.parseInt(txt3.getText());
  
  drawpanel.setScore(kor, eng, math);
  
  //그래프를 그리는 패널의 paint()를 간접적으로 호출
  drawpanel.repaint();
  
  }catch(NumberFormatException n){
   System.out.println("잘못된 숫자 포멧입니다.");
   
  }
 }
}