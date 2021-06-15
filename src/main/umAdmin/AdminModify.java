package main.umAdmin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.border.LineBorder;

import main.DB;
import main.style.BtnFont;

public class AdminModify extends JFrame implements ActionListener {
	private JButton btnOk, btnCancel;
	private JTextField tfMajor, tfName, tfPhone;
	private JPanel pTop, pCenter, pBottom, panelContainer;
	private Admin admin;

	public JTextField getTfMajor() {
		return tfMajor;
	}

	public JTextField getTfName() {
		return tfName;
	}

	public JTextField getTfPhone() {
		return tfPhone;
	}

	public AdminModify(String title, int width, int height, Admin admin) {
		this.admin = admin;
		this.setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dispose();

		panelContainer = new JPanel();
		
		panelContainer.setLayout(new BorderLayout());
		panelContainer.setBorder(new LineBorder(Color.GRAY, 2)); // 패널 테두리 설정
		
		setUndecorated(true); // 패널 타이틀바 삭제
		
		setTop();
		setCenter();
		setBottom();
		// 레이아웃
		
		add(panelContainer);
		
	}

	private void setTop() {
		pTop = new JPanel();
		pTop.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
		
		//pTop.setPreferredSize(new Dimension(250, 40));
		pTop.setBackground(new Color(0xB2CCFF));

		JLabel lbl1 = new JLabel("수 정");
		lbl1.setForeground(Color.BLACK); // JLabel글자 색 변경
		lbl1.setFont(new Font("HY헤드라인M",Font.PLAIN, 15));
		pTop.add(lbl1);

		panelContainer.add(pTop, BorderLayout.NORTH);
		this.setVisible(true);
	}

	private void setCenter() {
		pCenter = new JPanel();

		pCenter.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		pCenter.setLayout(new GridLayout(3, 2, 0, 20));
		pCenter.setBackground(Color.WHITE);
		
		JLabel lblMajor = new JLabel("학과 :");
		lblMajor.setFont(new Font("HY헤드라인M",Font.PLAIN, 15));
		pCenter.add(lblMajor);

		tfMajor = new JTextField();
		pCenter.add(tfMajor);

		JLabel lblName = new JLabel("이름 :");
		lblName.setFont(new Font("HY헤드라인M",Font.PLAIN, 15));
		pCenter.add(lblName);

		tfName = new JTextField();
		pCenter.add(tfName);

		JLabel lblPhone = new JLabel("전화번호 :");
		lblPhone.setFont(new Font("HY헤드라인M",Font.PLAIN, 15));
		pCenter.add(lblPhone);

		tfPhone = new JTextField();
		pCenter.add(tfPhone);

		panelContainer.add(pCenter, BorderLayout.CENTER);
		
		this.setVisible(true);
	}

	private void setBottom() {
		pBottom = new JPanel();

		pBottom.setBackground(Color.WHITE);
		
		pBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

		btnOk = new JButton("수정");
		BtnFont.BtnStyle(btnOk);
		btnOk.addActionListener(this);

		pBottom.add(btnOk);

		btnCancel = new JButton("취소");
		BtnFont.BtnStyle(btnCancel);
		btnCancel.addActionListener(this);

		pBottom.add(btnCancel);

		panelContainer.add(pBottom, BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public static void main(String[] args) {
		DB.init();
		// new AdminModify("학생수정", 250, 300);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOk) {
			String major = tfMajor.getText();
			String name = tfName.getText();
			String phone = tfPhone.getText();
			String id = admin.getId();

			String sql = "UPDATE STUDENT SET NAME = '" + name + "'," + " DEPARTMENT = '" + major + "'," + " PHONE = '" + phone + "'" + " WHERE STUDENTID = '" + id + "'";
			DB.executeQuery(sql); // DB 내용 수정

			// 새로고침
			admin.getPanelCenter().removeAll();
			admin.studentPanel();
			admin.getPanelCenter().revalidate(); // 레이아웃 변화 재확인
			admin.getPanelCenter().repaint(); // 레이아웃 다시 가져오기

			System.out.println(sql);

			dispose();
		} else if (e.getSource() == btnCancel) {
			dispose(); // 취소 버튼 누르면 화면 종료
		}
	}
}
