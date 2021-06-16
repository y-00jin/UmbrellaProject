package main.style;

import java.awt.Color;  
import java.awt.Font;  
import java.awt.event.FocusAdapter;  
import java.awt.event.FocusEvent;  
import javax.swing.JTextField;  

public class hint extends JTextField{
		Font gainFont = new Font("HY헤드라인M", Font.PLAIN, 10); // 텍스트필드에 입력할때
		Font lostFont = new Font("HY헤드라인M", Font.ITALIC, 10); // 텍스트필드에 입력하기 전 힌트
		
		public hint(final String hint) {  
		    setText(hint);  
		    setFont(lostFont);  
		    setForeground(Color.GRAY);  
		  
		    this.addFocusListener(new FocusAdapter() {  

		      @Override  
		      public void focusGained(FocusEvent e) {  // 포커스를 얻었을 때
		        if (getText().equals(hint)) {  
		          setText("");  
		          setFont(gainFont);  

		        } else {  
		          setText(getText());  
		          setFont(gainFont);  
		        }  
		      }  

		      @Override  
		      public void focusLost(FocusEvent e) {  // 포커스를 잃었을 때
		        if (getText().equals(hint)|| getText().length()==0) {  
		          setText(hint);  
		          setFont(lostFont);  
		          setForeground(Color.GRAY);  

		        } else {  
		          setText(getText());  
		          setFont(gainFont);  
		          setForeground(Color.BLACK);  
		        }  
		      }  
		    }); 
		  }  
}
