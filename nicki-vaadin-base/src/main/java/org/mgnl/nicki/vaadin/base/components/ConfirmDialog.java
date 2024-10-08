
package org.mgnl.nicki.vaadin.base.components;

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


import org.mgnl.nicki.vaadin.base.command.Command;
import org.mgnl.nicki.vaadin.base.notification.Notification;
import org.mgnl.nicki.vaadin.base.notification.Notification.Type;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@SuppressWarnings("serial")
public class ConfirmDialog extends DialogBase {

	
	private VerticalLayout mainLayout;

	
	private HorizontalLayout horizontalLayout_1;

	
	private Button confirmButton;

	
	private Button cancelButton;

	
	private Label headline;
	
	Command command;
	
	boolean isInit;

	public ConfirmDialog() {
	}
	
	public void setCommand(Command confirmCommand) {
		this.command = confirmCommand;
		super.setHeaderTitle(confirmCommand.getTitle());

		if (!isInit) {
			buildMainLayout();
			setCompositionRoot(mainLayout);
	
			applyI18n();
			
			confirmButton.addClickListener(event -> {
					try {
						command.execute();
					} catch (Exception e) {
						Notification.show(command.getErrorText(),
								e.getMessage(), Type.ERROR_MESSAGE);
					}
					close();
				}
			);
			
			cancelButton.addClickListener(event -> close());
			
			isInit = true;
		}
	}

	public ConfirmDialog(Command confirmCommand) {
		setCommand(confirmCommand);
	}

	private void applyI18n() {
		headline.setText(command.getHeadline());
		cancelButton.setText(command.getCancelCaption());
		confirmButton.setText(command.getConfirmCaption());
	}
		
	
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		
		// headline
		headline = new Label();
		headline.setWidth("-1px");
		headline.setHeight("-1px");
		headline.setText("Headline");
		mainLayout.add(headline);
		
		// horizontalLayout_1
		horizontalLayout_1 = buildHorizontalLayout_1();
		mainLayout.add(horizontalLayout_1);
		
		return mainLayout;
	}

	
	private HorizontalLayout buildHorizontalLayout_1() {
		// common part: create layout
		horizontalLayout_1 = new HorizontalLayout();
		horizontalLayout_1.setWidth("-1px");
		horizontalLayout_1.setHeight("-1px");
		horizontalLayout_1.setMargin(false);
		
		// cancelButton
		cancelButton = new Button();
		cancelButton.setWidth("-1px");
		cancelButton.setHeight("-1px");
		cancelButton.setText("Cancel");
		horizontalLayout_1.add(cancelButton);
		
		// confirmButton
		confirmButton = new Button();
		confirmButton.setWidth("-1px");
		confirmButton.setHeight("-1px");
		confirmButton.setText("Confirm");
		horizontalLayout_1.add(confirmButton);
		
		return horizontalLayout_1;
	}
}
