package main.test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;

import main.umReturn.DateLabelFormatter;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class Test extends JFrame implements ActionListener {

	private UtilDateModel model;
	private JDatePanelImpl datePanel;
	private JButton btn;
	private JDatePickerImpl datePicker;
	private Date SelectedDate;

	public Test(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(this);	//현재 클래스에 대해서 상대적인 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new FlowLayout());
		
		model = new UtilDateModel();
		datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());	//화면에 보이는거
		
		
		btn = new JButton("확인");
		btn.addActionListener(this);
		
		add(datePicker);
		add(btn);

		setVisible(true);
	}

	
	public static void main(String[] args) {
		new Test("테스트", 500, 500);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == btn) {
			String datePattern = "yyyy-MM-dd";
			SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
			SelectedDate = (Date) datePicker.getModel().getValue();
			
			System.out.println((dateFormatter.format(SelectedDate)));
		}
	}

}
