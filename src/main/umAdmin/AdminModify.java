package main.umAdmin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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

import main.DB;

public class AdminModify extends JFrame implements ActionListener {
	private JButton btnOk, btnCancel;
	private JTextField tfMajor, tfGrade, tfName, tfPhone;
	private JPanel pTop, pCenter, pBottom;
	private Admin admin;

	public JTextField getTfMajor() {
		return tfMajor;
	}

	public JTextField getTfGrade() {
		return tfGrade;
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

		setTop();
		setCenter();
		setBottom();

		// 레이아웃
		setLayout(new BorderLayout());
	}

	private void setTop() {
		pTop = new JPanel();
		pTop.setBackground(new Color(0xffe493));
		add(pTop, BorderLayout.NORTH);

		JLabel lbl1 = new JLabel("수 정");
		lbl1.setForeground(Color.BLACK); // JLabel글자 색 변경
		pTop.add(lbl1);
	}

	private void setCenter() {
		pCenter = new JPanel();
		pCenter.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		pCenter.setLayout(new GridLayout(4, 2, 0, 10));
		add(pCenter, BorderLayout.CENTER);

		JLabel lblMajor = new JLabel("학과 :");
		pCenter.add(lblMajor);

		tfMajor = new JTextField();
		pCenter.add(tfMajor);

		JLabel lblGrade = new JLabel("학년 :");
		pCenter.add(lblGrade);

		tfGrade = new JTextField();
		pCenter.add(tfGrade);

		JLabel lblName = new JLabel("이름 :");
		pCenter.add(lblName);

		tfName = new JTextField();
		pCenter.add(tfName);

		JLabel lblPhone = new JLabel("전화번호 :");
		pCenter.add(lblPhone);

		tfPhone = new JTextField();
		pCenter.add(tfPhone);

	}

	private void setBottom() {
		pBottom = new JPanel();
		pBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		add(pBottom, BorderLayout.SOUTH);

		btnOk = new JButton("수정");
		btnOk.addActionListener(this);
		pBottom.add(btnOk);

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		pBottom.add(btnCancel);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		DB.init();
		//new AdminModify("수정", 250, 300);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOk) {
			String major = tfMajor.getText();
			String grade = tfGrade.getText();
			String name = tfName.getText();
			String phone = tfPhone.getText();
			String id = admin.getId();

			String sql = "UPDATE STUDENT SET NAME = '" + name + "'," + " DEPARTMENT = '" + major + "',"
							+ " GRADE = '" + grade + "'," + " PHONE = '" + phone + "'" + " WHERE STUDENTID = '" + id + "'";
			DB.executeQuery(sql);
			System.out.println(sql);
			
			dispose();

		} else if (e.getSource() == btnCancel) {
			dispose();
		}
	}
}
