
package org.mgnl.nicki.vaadin.base.data;

import java.util.Arrays;
import java.util.Locale;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;

/*-
 * #%L
 * nicki-vaadin-base
 * %%
 * Copyright (C) 2017 Ralf Hirning
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


public class DateHelper {
	public static DatePickerI18n datePickerI18n = new DatePickerI18n()
			.setFirstDayOfWeek(1)
			.setWeek("Woche")
	        .setCalendar("Kalender").setClear("Löschen").setToday("Heute")
	        .setCancel("Abbrechen").setFirstDayOfWeek(1)
	        .setMonthNames(Arrays.asList("Januar", "Februar", "März",
	                "April", "Mai", "Juni", "Juli", "August", "September",
	                "Oktober", "November", "Dezember"))
	        .setWeekdays(Arrays.asList("Sonntag", "Montag", "Dienstag",
	                "Mittwoch", "Donnerstag", "Freitag", "Samstag"))
	        .setWeekdaysShort(Arrays.asList("So", "Mo", "Di", "Mi",
	                "Do", "Fr", "Sa"));

	public static void init(DatePicker field) {
		field.setLocale(Locale.GERMANY);
		
		field.setI18n(datePickerI18n);
	}

}
