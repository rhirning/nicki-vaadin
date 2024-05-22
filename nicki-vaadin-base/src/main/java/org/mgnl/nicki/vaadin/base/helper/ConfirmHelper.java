package org.mgnl.nicki.vaadin.base.helper;

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
