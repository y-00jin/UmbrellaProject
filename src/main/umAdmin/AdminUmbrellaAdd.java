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

public class AdminUmbrellaAdd extends JFrame implements ActionListener {
	private JButton btnAdd, btnCancel;
	private JTextField tfUmbrellaId, tfUmbrellaState;
	private JPanel pTop, pCenter, pBottom;
	private Admin admin;

	public AdminUmbrellaAdd(String title, int width, int height, Admin admin) {
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

		pTop.setBackground(new Color(0xB2CCFF));

		JLabel lbl1 = new JLabel("추 가");
		lbl1.setForeground(Color.BLACK); // JLabel글자 색 변경

		pTop.add(lbl1);

		add(pTop, BorderLayout.NORTH);

		this.setVisible(true);
	}

	private void setCenter() {
		pCenter = new JPanel();

		pCenter.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		pCenter.setLayout(new GridLayout(2, 2, 0, 10));

		JLabel lblId = new JLabel("우산코드 :");
		pCenter.add(lblId);

		tfUmbrellaId = new JTextField();
		pCenter.add(tfUmbrellaId);

		JLabel lblState = new JLabel("대여상태 :");
		pCenter.add(lblState);

		tfUmbrellaState = new JTextField();
		pCenter.add(tfUmbrellaState);

		add(pCenter, BorderLayout.CENTER);

		this.setVisible(true);
	}

	private void setBottom() {
		pBottom = new JPanel();

		pBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);

		pBottom.add(btnAdd);

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);

		pBottom.add(btnCancel);

		add(pBottom, BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public static void main(String[] args) {
		DB.init();
		// new AdminUmbrellaAdd("우산추가", 250, 200);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnAdd) {

			String umbrellaId = tfUmbrellaId.getText();
			String umbrellaState = tfUmbrellaState.getText();

			if (umbrellaState.equals("Y") | umbrellaState.equals("N")) {
				String sql = "INSERT INTO UMBRELLA VALUES('" + umbrellaId + "', " + "'" + umbrellaState + "')";
				DB.executeQuery(sql); // DB 내용 추가
				System.out.println(sql);

				dispose();

				// 새로고침
				admin.getP2().removeAll();
				admin.umbrellaPanel();
				admin.getP2().revalidate(); // 레이아웃 변화 재확인
				admin.getP2().repaint(); // 레이아웃 다시 가져오기
			} else {
				JOptionPane.showMessageDialog(null, "입력 양식이 잘못되었습니다!");
			}

		} else if (obj == btnCancel) {
			dispose(); // 취소 버튼 누르면 화면 종료
		}

	}
}
