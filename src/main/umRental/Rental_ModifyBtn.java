package main.umRental;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Rental_ModifyBtn extends JFrame implements ActionListener {
	private JButton btn_ok, btn_cancel;
	private JTextField tf_Umbcode, tf_Code;
	private JPanel pTop, pCenter, pBottom;

	public Rental_ModifyBtn(String title, int width, int height) {
		this.setTitle(title);
		setSize(width, height);	
		setLocationRelativeTo(this);	
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			
		dispose();
		
		setTop();
		setCenter();
		setBottom();

		//레이아웃
		setLayout(new BorderLayout()); 
	}

	private void setTop() {
		pTop = new JPanel();
		pTop.setBackground(new Color(0xB2CCFF));
		add(pTop, BorderLayout.NORTH);
		
		JLabel lbl1 = new JLabel("수 정");
		lbl1.setForeground(Color.BLACK); //JLabel글자 색 변경
		lbl1.setFont(new Font("HY헤드라인M",Font.PLAIN, 12));
		pTop.add(lbl1);
	}

	private void setCenter() {
		pCenter = new JPanel();
		pCenter.setBorder(BorderFactory.createEmptyBorder(20,10,20,10));
		pCenter.setBackground(Color.WHITE);
		pCenter.setLayout(new GridLayout(2, 2, 0,10));
		add(pCenter, BorderLayout.CENTER);
		
		JLabel lbl_Code = new JLabel("학번 :");
		lbl_Code.setFont(new Font("HY헤드라인M",Font.PLAIN, 12));
		pCenter.add(lbl_Code);
		
		tf_Code = new JTextField();
		pCenter.add(tf_Code);
		
		JLabel lbl_UmbCode = new JLabel("우산번호 :");	
		lbl_UmbCode.setFont(new Font("HY헤드라인M",Font.PLAIN, 12));
		pCenter.add(lbl_UmbCode);
		
		tf_Umbcode = new JTextField();
		pCenter.add(tf_Umbcode);
	}

	private void setBottom() {
		pBottom = new JPanel();
		pBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		pBottom.setBackground(Color.WHITE);
		add(pBottom, BorderLayout.SOUTH);
		
		btn_ok = new JButton("수정");
		btn_ok.addActionListener(this);
		pBottom.add(btn_ok);
		
		btn_cancel = new JButton("취소");
		btn_cancel.addActionListener(this);
		pBottom.add(btn_cancel);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Rental_ModifyBtn("수정", 250, 200);			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btn_ok) {
			if(!tf_Umbcode.getText().equals("") && !tf_Code.getText().equals("")) {
				//모든 항목 입력시 확인 버튼 클릭하면 저장되게
				if(tf_Code.getText() == "1"/*사실 1은 아니고 만약 중복된다면*/) {
					JOptionPane.showMessageDialog( // 메시지창 출력
							this, "중복된 아이디가 있습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog( // 메시지창 출력
							this, "처리가 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		} else if(obj == btn_cancel) {
			dispose(); //취소 버튼 누르면 화면 종료			
		}
		
	}
}
