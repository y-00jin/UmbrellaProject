package main.umAdmin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import main.DB;
import main.style.BtnFont;

public class AdminUmbrellaAdd extends JFrame implements ActionListener {
   private JButton btnAdd, btnCancel;
   private JTextField tfUmbrellaId, tfUmbrellaState;
   private JPanel pTop, pCenter, pBottom;
   private Admin admin;
   private JComponent panelContainer;

   public AdminUmbrellaAdd(String title, int width, int height, Admin admin) {
      this.admin = admin;
      this.setTitle(title);
      setSize(width, height);
      setLocationRelativeTo(admin);
      // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      dispose();
      
      panelContainer = new JPanel();
      
      panelContainer.setLayout(new BorderLayout());
      setUndecorated(true); // 패널 타이틀바 삭제
      panelContainer.setBorder(new LineBorder(Color.GRAY, 2)); // 패널 테두리 설정
      
      setTop();
      setCenter();
      setBottom();
      
      add(panelContainer);
      
      this.setVisible(true);
   }

   private void setTop() {
      pTop = new JPanel();

      pTop.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
      pTop.setBackground(new Color(0xB2CCFF));

      JLabel lbl1 = new JLabel("추 가");
      lbl1.setFont(new Font("HY헤드라인M",Font.PLAIN, 15));
      lbl1.setForeground(Color.BLACK); // JLabel글자 색 변경

      pTop.add(lbl1);

      panelContainer.add(pTop, BorderLayout.NORTH);
      
      this.setVisible(true);
   }

   private void setCenter() {
      pCenter = new JPanel();

      pCenter.setBorder(BorderFactory.createEmptyBorder(40, 10, 40, 10));
      pCenter.setLayout(new GridLayout(1, 2, 0, 40));
      pCenter.setBackground(Color.WHITE);
      
      JLabel lblId = new JLabel("우산코드 :");
      lblId.setFont(new Font("HY헤드라인M",Font.PLAIN, 15));
      pCenter.add(lblId);

      tfUmbrellaId = new JTextField();
      tfUmbrellaId.addActionListener(this);
      
      pCenter.add(tfUmbrellaId);

//      JLabel lblState = new JLabel("대여상태 :");
//      lblState.setFont(new Font("HY헤드라인M",Font.PLAIN, 15));
//      pCenter.add(lblState);

//      tfUmbrellaState = new JTextField();
//      pCenter.add(tfUmbrellaState);

      panelContainer.add(pCenter, BorderLayout.CENTER);

      this.setVisible(true);
   }

   private void setBottom() {
      pBottom = new JPanel();

      pBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
      pBottom.setBackground(Color.WHITE);

      btnAdd = new JButton("추가");
      BtnFont.BtnStyle(btnAdd);
      btnAdd.addActionListener(this);

      pBottom.add(btnAdd);

      btnCancel = new JButton("취소");
      BtnFont.BtnStyle(btnCancel);
      btnCancel.addActionListener(this);

      pBottom.add(btnCancel);

      panelContainer.add(pBottom, BorderLayout.SOUTH);

      this.setVisible(true);
   }

   public static void main(String[] args) {
      DB.init();
      // new AdminUmbrellaAdd("우산추가", 250, 200);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      Object obj = e.getSource();
      if (obj == btnAdd | obj == tfUmbrellaId) {

         String umbrellaId = tfUmbrellaId.getText();
        
         String ifUmExist =  "SELECT UMBRELLAID "
               + "FROM DODAM.UMBRELLA "
               + "WHERE UMBRELLAID IN '" + umbrellaId +"'";
         ResultSet rsUm = DB.getResultSet(ifUmExist);
         
         String umExist = "";
         
         try {
            if (rsUm.next()) {
            	umExist = rsUm.getString(1);
            }
         } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
         
         
         try {
			if (!rsUm.next()) {
			    String sql = "INSERT INTO UMBRELLA VALUES('" + umbrellaId + "', " + "'N')";
			    DB.executeQueryP(sql); // DB 내용 추가

			    JOptionPane.showMessageDialog(null, "추가 되었습니다.", "우산 추가", JOptionPane.PLAIN_MESSAGE);

			    dispose();

			    // 새로고침
			    admin.getPanelCenter().removeAll();
			    admin.umbrellaPanel();
			    admin.getPanelCenter().revalidate(); // 레이아웃 변화 재확인
			    admin.getPanelCenter().repaint(); // 레이아웃 다시 가져오기
			    
			 }
			
		} catch (HeadlessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

      } else if (obj == btnCancel) {
         dispose(); // 취소 버튼 누르면 화면 종료
      }

   }
}