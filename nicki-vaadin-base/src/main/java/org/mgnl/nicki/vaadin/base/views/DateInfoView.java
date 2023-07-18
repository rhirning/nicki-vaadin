package org.mgnl.nicki.vaadin.base.views;

import java.text.ParseException;
import java.time.LocalDate;
import org.mgnl.nicki.core.helper.DataHelper;
import org.mgnl.nicki.vaadin.base.data.DateHelper;
import org.mgnl.nicki.vaadin.base.menu.application.ConfigurableView;
import org.mgnl.nicki.vaadin.base.notification.Notification;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class DateInfoView extends BaseInfoView implements ConfigurableView {

	private static final long serialVersionUID = 5748928755710070899L;
	private DatePicker datePicker;


	@Override
	public void setValue(String data) {
		LocalDate date;
		try {
			date = DataHelper.getLocalDate(DataHelper.dateFromDisplayDay(data));
			datePicker.setValue(date);
		} catch (ParseException e) {
			Notification.show("Invalid initial value");
		}
	}

	@Override
	public void addComponent(VerticalLayout canvas) {
		datePicker = new DatePicker();
		DateHelper.init(datePicker);
		canvas.add(datePicker);		
	}

	@Override
	protected String getValue() {
		return DataHelper.getDisplayDay(DataHelper.getDate(datePicker.getValue()));
	}

}
