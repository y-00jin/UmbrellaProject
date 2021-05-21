package main.umrental;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class Rental_DB extends JFrame implements ActionListener {
   private JPanel pCenter, pBottom;
   private JButton btnF5, btnRental, btnModify, btnOk, btnReturn, btnOut;
   private Vector<String> vectorTitle;
   private DefaultTableModel model;
   private Vector<String> data;
   private String[] tmp;
   private JTable table;
   private String UMB_Code, class_Code, name, rentalDay, returnDay;

   public Rental_DB(String title, int width, int height) {
      this.setTitle(title);
      setSize(width, height);
      setLocationRelativeTo(this);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // 레이아웃
      setLayout(new BorderLayout());
      // setResizable(false); // 실행후 화면크기 변경 불가

      setCenter();
      setBottom();

      this.setVisible(true);
   }

   private void setCenter() {
      pCenter = new JPanel();
      pCenter.setLayout(new FlowLayout(FlowLayout.LEFT));
      pCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
      add(pCenter, BorderLayout.CENTER);

      vectorTitle = new Vector<String>();

      vectorTitle.addElement("우산코드");
      vectorTitle.addElement("학번");
      vectorTitle.addElement("이름");
      vectorTitle.addElement("대여일");
      vectorTitle.addElement("반납예정일");

      model = new DefaultTableModel(vectorTitle, 0) { // 테이블의 데이터 변경하려면 모델 사용

         public boolean isCellEditable(int r, int c) {
            return false;
         }

      };
      
      // 테이블
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver"); //오라클 드라이버
         
         Connection conn = DriverManager.getConnection(
               "jdbc:oracle:thin:@localhost:1521:XE", 
               "dodam", "inhatc4"); //오라클
               //1521: 포트번호
         
         Statement stmt = conn.createStatement(); //데이터베이스와 연동
         
         ResultSet rs = stmt.executeQuery("SELECT *FROM RENTRL"); //쿼리 넘기기
         
         while(rs.next()) { 
            data = new Vector<String>();
            
            UMB_Code = rs.getString("UMBCODE"); 
            class_Code = rs.getString("CLASS");             
            name = rs.getString(3);   
            rentalDay = rs.getString(4); 
            returnDay = rs.getString(5);
            
            System.out.println(UMB_Code + "\t" + class_Code + "\t" + name + "\t" + rentalDay +"\t"+ returnDay);
            data.add(0, UMB_Code);
            data.add(1, class_Code);
            data.add(2, name);
            data.add(3, rentalDay);
            data.add(4, returnDay);
            model.addRow(data);
         }
         
         System.out.println("성공");
         
      } catch (ClassNotFoundException e) {
         System.out.println("해당 드라이버가 없습니다.");
         e.printStackTrace(); //어디서 오류가 발생했는지 알려줌
         
      } catch (SQLException e) {
         System.out.println("접속오류 / SQL 오류");
         e.printStackTrace();
      }
      
      table = new JTable(model); // 테이터 변경 시 테이블에 직접 접근하지 않고 변경

      // 스크롤팬을 사용하지 않으면 컬럼명을 볼 수 없음
      JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      sp.setPreferredSize(new Dimension(850, 480)); //테이블 크기를 줄려면 JScroollPane의 크기를 변경

      pCenter.add(sp);
      
       //테이블 내용 가운데정렬
      DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

      tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

      TableColumnModel tcmSchedule = table.getColumnModel();

      for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
      tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
      }
   }

   private void setBottom() {
      pBottom = new JPanel();
      pBottom.setLayout(new FlowLayout(FlowLayout.LEFT));
      pBottom.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 0));
      add(pBottom, BorderLayout.SOUTH);

      btnF5 = new JButton("새로고침");

      pBottom.add(btnF5);

      btnRental = new JButton("대여");
      btnRental.addActionListener(this);
      pBottom.add(btnRental);

      btnModify = new JButton("수정");
      btnModify.addActionListener(this);
      pBottom.add(btnModify);

      btnOk = new JButton("완료");
      pBottom.add(btnOk);

      btnReturn = new JButton("반납");
      btnReturn.addActionListener(this);
      pBottom.add(btnReturn);

      btnOut = new JButton("차단");
      btnOut.addActionListener(this);
      pBottom.add(btnOut);
   }

   public static void main(String[] args) {
      new Rental_DB("대여", 900, 600);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      Object obj = e.getSource();
      if (obj == btnRental) {
        // new Rentalform("대여", 250, 200);
      } else if (obj == btnModify) {
        // new Rental_ModifyBtn("수정", 250, 200);
      } else if (obj == btnReturn) {
         JOptionPane.showMessageDialog( // 메시지창 출력
               this, "000님의 우산이 반납처리되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
      } else if (obj == btnOut) {
         
      }
   }
}