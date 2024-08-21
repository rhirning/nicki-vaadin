package org.mgnl.nicki.vaadin.base.helper;

/*-
 * #%L
 * nicki-vaadin-base
 * %%
 * Copyright (C) 2020 - 2024 Ralf Hirning
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

import java.util.function.Consumer;

import org.mgnl.nicki.vaadin.base.command.Command;
import org.mgnl.nicki.vaadin.base.command.CommandException;
import org.mgnl.nicki.vaadin.base.components.ConfirmDialog;

public class ConfirmHelper {
	
	public static <T> void confirm(String title, T bean, Consumer<T> action) {
		showConfirmDialog(title, bean, action);
	}	
	
	private static <T> void showConfirmDialog(String windowTitle, T bean, Consumer<T> action) {
		ConfirmDialog editWindow = new ConfirmDialog();
		editWindow.setCommand(getCommand(windowTitle, bean, b -> {
			action.accept(b);
			editWindow.close();
		}));

		editWindow.setModal(true);
		editWindow.setWidth("600px");
		editWindow.setHeight("600px");
		editWindow.open();
	}	private static <T> Command getCommand(String windowTitle, T bean, Consumer<T> action) {
		return new Command() {
			
			@Override
			public String getTitle() {
				return windowTitle;
			}
			
			@Override
			public String getHeadline() {
				return "Sind Sie sicher";
			}
			
			@Override
			public String getErrorText() {
				return "";
			}
			
			@Override
			public String getConfirmCaption() {
				return "Ja";
			}
			
			@Override
			public String getCancelCaption() {
				return "Abbrechen";
			}
			
			@Override
			public void execute() throws CommandException {
				action.accept(bean);				
			}
		};
	}
}
