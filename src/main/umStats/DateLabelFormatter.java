package main.umStats;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;

import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

public class DateLabelFormatter extends AbstractFormatter {

	// 데이터 형식 정하기
	private String datePattern = "yyyy-MM-dd";
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);	// datePattern으로 형식 지정
	
	@Override
	public Object stringToValue(String text) throws ParseException {
		
		return dateFormatter.parseObject(text);	//입력받은 날짜값을 지정한 형식을 적용하여 리턴해줌 
	}

	// 날짜 클릭 시 텍스트 필드에 클릭된 날짜 출력
	@Override
	public String valueToString(Object value) throws ParseException {
		if(value != null) {
			Calendar cal = (Calendar)value;
			return dateFormatter.format(cal.getTime());
		}
		return "";
	}
	

}
