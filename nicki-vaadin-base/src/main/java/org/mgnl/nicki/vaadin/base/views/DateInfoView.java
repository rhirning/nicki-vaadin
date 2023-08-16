package org.mgnl.nicki.vaadin.base.views;

/*-
 * #%L
 * nicki-vaadin-base
 * %%
 * Copyright (C) 2020 - 2023 Ralf Hirning
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
