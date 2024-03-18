package org.mgnl.nicki.vaadin.base.components;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;

import org.mgnl.nicki.core.data.Period;
import org.mgnl.nicki.core.helper.DataHelper;
import org.mgnl.nicki.core.helper.PERIOD;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.renderer.TextRenderer;

public class PeriodSelect extends HorizontalLayout {
	private static final long serialVersionUID = 7377022216739582730L;
	private Select<PERIOD> periodSelect;
	private DatePicker fromPicker;
	private DatePicker toPicker;
	private Button executeButton;
	private Consumer<PERIOD> consumer;

	public PeriodSelect() {
		periodSelect = new Select<PERIOD>();
		periodSelect.setItems(PERIOD.values());
		periodSelect.setRenderer(new TextRenderer<>(PERIOD::getName));
		periodSelect.setEmptySelectionAllowed(false);
		fromPicker = new DatePicker();
		fromPicker.setPlaceholder("von");
		toPicker = new DatePicker();
		toPicker.setPlaceholder("bis");
		executeButton = new Button("Ausführen");
		add(periodSelect, fromPicker, toPicker, executeButton);
		setVerticalComponentAlignment(Alignment.END, periodSelect, fromPicker, toPicker, executeButton);
		setMargin(false);
		setSpacing(false);
		setPadding(false);
		fromPicker.setVisible(false);
		toPicker.setVisible(false);
		executeButton.setVisible(false);
		executeButton.addClickListener(e -> execute(PERIOD.USER_DEFINED));
		periodSelect.setValue(PERIOD.THIS_MONTH);
		
		periodSelect.addValueChangeListener(event -> {
			if (event.getValue() == PERIOD.USER_DEFINED) {
				fromPicker.setVisible(true);
				toPicker.setVisible(true);
				executeButton.setVisible(true);
			} else if (event.getValue() != null) {
				fromPicker.setVisible(false);
				toPicker.setVisible(false);
				executeButton.setVisible(false);
				execute(event.getValue());
			}
		});

	}

	private void execute(PERIOD period) {
		if (consumer != null) {
			if (periodSelect.getValue() != PERIOD.USER_DEFINED || (fromPicker.getValue() != null && toPicker.getValue() != null)) {
				consumer.accept(period);
			}
		}
	}

	public void setConsumer(Consumer<PERIOD> consumer) {
		this.consumer = consumer;
	}
	
	public void setLabel(String label) {
		periodSelect.setLabel(label);
	}

	public void setValue(PERIOD value) {
		periodSelect.setValue(value);		
	}

	public Calendar getFrom() {
		if (periodSelect.getValue() == PERIOD.USER_DEFINED) {
			LocalDate val = fromPicker.getValue();
			return getCalendar(val);
		} else {
			return periodSelect.getValue().getStart();
		}
	}

	public Calendar getTo() {
		if (periodSelect.getValue() == PERIOD.USER_DEFINED) {
			LocalDate val = toPicker.getValue();
			return getCalendar(val);
		} else {
			return periodSelect.getValue().getEnd();
		}
	}

	private Calendar getCalendar(LocalDate val) {
		Date date = DataHelper.getDate(val);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public Period getValue() {
		return new Period(getFrom(), getTo());
	}

}
