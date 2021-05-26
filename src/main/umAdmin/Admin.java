package main.umAdmin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import main.DB;
import main.style.BtnFont;

public class Admin extends JFrame implements ActionListener, MouseListener {

   private JPanel p1, p2;
   private String result;
   private String[] strs = { "학생", "우산", "차단목록" };
   private JComboBox<String> cbStr;
   private DefaultTableModel model;
   private Vector<String> con;
   private JTable studentTable, umbrellaTable, blockTable;
   private String blockID;
   private String blockMajor;
   private String blockName;
   private String returnState;
   private String umbrellaID;
   private String umbrellaState;
   private String studentID;
   private String studentMajor;
   private String studentName;
   private String studentGrade;
   private String studentPhone;
   private String id;
   private JButton btnDelect;
   private JButton btnAdd;
   private JButton btnCancel;
   private AdminModify modify;
   private AdminUmbrellaAdd umbrellaAdd;
   private JTable table;
   private String umCode;

   public String getId() {
      return id;
   }

   public JPanel getP2() {
      return p2;
   }

   public Admin(String title, int width, int height) {
      setTitle(title);
      setSize(width, height);
      setLocationRelativeTo(this);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//      result = "";
//      result = JOptionPane.showInputDialog("관리자 확인을 위해 \n" + "비밀번호 네자리를 입력해주세요");
//
//      while (!result.equals("inhatc4")) {
//         result = JOptionPane.showInputDialog("비밀번호 오류!");
//      } //관리자 확인 비밀번호 입력

      p1 = new JPanel();
      p2 = new JPanel();

      p1.setLayout(new FlowLayout(FlowLayout.LEFT));
      p1.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 10)); // 마진

      p2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
      p2.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // 마진

      p1.setBackground(Color.WHITE);
      p2.setBackground(Color.WHITE); // 패널 배경색 설정

      cbStr = new JComboBox<String>(strs);
      cbStr.addActionListener(this);

      UIManager.put("ComboBox.background", new ColorUIResource(Color.yellow));
      UIManager.put("JTextField.background", new ColorUIResource(Color.yellow));
      UIManager.put("ComboBox.selectionBackground", new ColorUIResource(Color.magenta));
      UIManager.put("ComboBox.selectionForeground", new ColorUIResource(Color.blue));

      p1.add(cbStr);

      add(p1, new BorderLayout().NORTH);

      studentPanel(); // 처음 켜졌을때 p2에 학생 테이블 불러옴

      setVisible(true);
   }

   public static void main(String[] args) {
      DB.init();
      new Admin("관리자", 900, 700);
   }

   private void blockPanel() { // 블록테이블 생성
      Vector<String> column = new Vector<String>();
      column.addElement("차단코드");
      column.addElement("학과");
      column.addElement("이름");
      column.addElement("반납여부");

      model = new DefaultTableModel(column, 0) {
         public boolean isCellEditable(int r, int c) {
            return false;
         }
      };

      String sql = "SELECT * FROM BLOCKLIST"; // 차단목록 전부 조회
      ResultSet rs = DB.getResultSet(sql);

      try {
         while (rs.next()) {
            con = new Vector<String>();
            blockID = rs.getString(1);
            blockMajor = rs.getString(2);
            blockName = rs.getString(2);
            returnState = rs.getString(2);

            con.add(blockID);
            con.add(blockMajor);
            con.add(blockName);
            con.add(returnState);
            model.addRow(con); // 테이블에 내용 추가
         }
      } catch (SQLException e) {
         System.out.println("접속 오류 / SQL 오류");
         e.printStackTrace();
      }

      blockTable = new JTable(model);
      blockTable.getTableHeader().setReorderingAllowed(false); // 테이블 편집X
      blockTable.addMouseListener(this);

      blockTable.setFillsViewportHeight(true); // 컨테이너의 전체 높이를 테이블이 전부 사용하도록 설정 -> 테이블 색이 컨테이너 색으로 덮힘

      JTableHeader tableHeader = blockTable.getTableHeader();
      tableHeader.setBackground(new Color(0xB2CCFF)); // 테이블 헤더 색 설정

      JScrollPane sc = new JScrollPane(blockTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

      sc.setPreferredSize(new Dimension(850, 535)); // 스크롤팬 크기 설정

      btnCancel = new JButton("차단 취소");
      BtnFont.BtnStyle(btnCancel);

      DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

      tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

      TableColumnModel tcmSchedule = blockTable.getColumnModel();

      for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
         tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
      } // 테이블 내용 가운데 정렬

      p2.add(sc);
      p2.add(btnCancel);

      add(p2, new BorderLayout().CENTER);
   }

   public void umbrellaPanel() { // 우산테이블 생성
      Vector<String> column = new Vector<String>();
      column.addElement("우산코드");
      column.addElement("대여상태");

      model = new DefaultTableModel(column, 0) {
         public boolean isCellEditable(int r, int c) {
            return false;
         }
      };

      String sql = "SELECT * FROM UMBRELLA order by UMBRELLAID asc"; // 우산목록 전부 조회
      ResultSet rs = DB.getResultSet(sql);
      
      

      try {
         while (rs.next()) {
            con = new Vector<String>();
            umbrellaID = rs.getString(1);
            umbrellaState = rs.getString(2);

            con.add(umbrellaID);
            con.add(umbrellaState);
            model.addRow(con); // 테이블에 내용 추가
         }
      } catch (SQLException e) {
         System.out.println("접속 오류 / SQL 오류");
         e.printStackTrace();
      }

      umbrellaTable = new JTable(model);
      umbrellaTable.getTableHeader().setReorderingAllowed(false); // 테이블 편집X
      umbrellaTable.addMouseListener(this);

      umbrellaTable.setFillsViewportHeight(true); // 컨테이너의 전체 높이를 테이블이 전부 사용하도록 설정 -> 테이블 색이 컨테이너 색으로 덮힘

      JTableHeader tableHeader = umbrellaTable.getTableHeader();
      tableHeader.setBackground(new Color(0xB2CCFF)); // 테이블 헤더 색 설정

      JScrollPane sc = new JScrollPane(umbrellaTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

      sc.setPreferredSize(new Dimension(850, 535)); // 스크롤팬 크기 설정

      btnAdd = new JButton("추가");
      BtnFont.BtnStyle(btnAdd);
      btnAdd.addActionListener(this);

      btnDelect = new JButton("삭제");
      BtnFont.BtnStyle(btnDelect);
      btnDelect.addActionListener(this);

      DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

      tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

      TableColumnModel tcmSchedule = umbrellaTable.getColumnModel();

      for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
         tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
      } // 테이블 내용 가운데 정렬

      p2.add(sc);
      p2.add(btnAdd);
      p2.add(btnDelect);

      add(p2, new BorderLayout().CENTER);

   }

   public void studentPanel() { // 학생테이블 생성
      Vector<String> column = new Vector<String>();
      column.addElement("학번");
      column.addElement("학과");
      column.addElement("학년");
      column.addElement("이름");
      column.addElement("전화번호");

      model = new DefaultTableModel(column, 0) {
         public boolean isCellEditable(int r, int c) {
            return false;
         }
      };

      String sql = "SELECT * FROM STUDENT"; // 학생목록 전부 조회
      ResultSet rs = DB.getResultSet(sql);

      try {
         while (rs.next()) {
            con = new Vector<String>();
            studentID = rs.getString(1);
            studentMajor = rs.getString(3);
            studentGrade = rs.getString(4);
            studentName = rs.getString(2);
            studentPhone = rs.getString(5);

            con.add(studentID);
            con.add(studentMajor);
            con.add(studentGrade);
            con.add(studentName);
            con.add(studentPhone);
            model.addRow(con); // 테이블에 내용 추가
         }
      } catch (SQLException e) {
         System.out.println("접속 오류 / SQL 오류");
         e.printStackTrace();
      }

      studentTable = new JTable(model);
      studentTable.getTableHeader().setReorderingAllowed(false); // 테이블 편집X
      studentTable.addMouseListener(this);

      studentTable.setFillsViewportHeight(true); // 컨테이너의 전체 높이를 테이블이 전부 사용하도록 설정 -> 테이블 색이 컨테이너 색으로 덮힘

      JTableHeader tableHeader = studentTable.getTableHeader();
      tableHeader.setBackground(new Color(0xB2CCFF)); // 테이블 헤더 색 설정

      JScrollPane sc = new JScrollPane(studentTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

      sc.setPreferredSize(new Dimension(850, 535)); // 스크롤팬 크기 설정

      DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

      tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

      TableColumnModel tcmSchedule = studentTable.getColumnModel();

      for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
         tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
      } // 테이블 내용 가운데 정렬

      p2.add(sc);

      add(p2, new BorderLayout().CENTER);

   }

   @Override
   public void actionPerformed(ActionEvent e) {
      Object obj = e.getSource();
      
      if (obj == cbStr) {

         p2.removeAll(); // 패널 지우기

         int index = cbStr.getSelectedIndex(); // 콤보박스 index 받아오기
         if (index == 0) {
            studentPanel(); // index 0일때 학생 테이블
         } else if (index == 1) {
            umbrellaPanel(); // index 1일때 우산 테이블
         } else if (index == 2) {
            blockPanel(); // index 2일때 차단 테이블
         }
         p2.revalidate(); // 레이아웃 변화 재확인
         p2.repaint(); // 레이아웃 다시 가져오기

      }

      if (obj == btnAdd) {

         new AdminUmbrellaAdd("우산추가", 300, 300, this);

      }

      if (obj == btnDelect) {
         
         String sql = "DELETE FROM UMBRELLA "
               + "WHERE UMBRELLAID='" + umCode + "'";
         DB.executeQuery(sql); // DB 내용 삭제임
         
         p2.removeAll();
         umbrellaPanel();
         p2.revalidate(); // 레이아웃 변화 재확인
         p2.repaint(); // 레이아웃 다시 가져오기
         
      }

   }

   @Override
   public void mouseClicked(MouseEvent e) {
      table = (JTable) e.getComponent();
      model = (DefaultTableModel) table.getModel();

      if (e.getSource() == studentTable) { // 학생테이블에서 적용되는 마우스 이벤트

         if (e.getClickCount() == 2) {
            int row = studentTable.getSelectedRow(); // 선택한 셀의 행 번호 계산

            TableModel data = studentTable.getModel(); // 테이블의 모델 객체 얻어오기

            id = (String) data.getValueAt(row, 0);
            String major = (String) data.getValueAt(row, 1);
            String grade = (String) data.getValueAt(row, 2);
            String name = (String) data.getValueAt(row, 3);
            String phone = (String) data.getValueAt(row, 4);

            modify = new AdminModify("학생수정", 250, 300, this);

            modify.getTfMajor().setText(major);
            modify.getTfGrade().setText(grade);
            modify.getTfName().setText(name);
            modify.getTfPhone().setText(phone);
         }

      }

      if (e.getSource() == umbrellaTable) { // 우산테이블에서 적용되는 마우스 이벤트

         umCode = (String) model.getValueAt(table.getSelectedRow(), 0);
         System.out.println("우산코드는 " + umCode);

      }

   }

   @Override
   public void mousePressed(MouseEvent e) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseReleased(MouseEvent e) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseEntered(MouseEvent e) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseExited(MouseEvent e) {
      // TODO Auto-generated method stub

   }
}