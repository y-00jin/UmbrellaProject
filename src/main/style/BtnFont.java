package main.style;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
 
public class BtnFont {
    public static void BtnStyle(JButton btnFont){
      Font fontBtn = new Font("HY헤드라인M", Font.PLAIN, 15);
      btnFont.setFont(fontBtn); // 폰트 스타일 적용
      btnFont.setForeground(new Color(0x5D5D5D)); // 글자색
      btnFont.setBackground(new Color(0xD9E5FF));
      //btnFont.setBorderPainted(false); // 테두리 없애기
   }
}