
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


import org.mgnl.nicki.core.i18n.I18n;
import org.mgnl.nicki.vaadin.base.application.NickiApplication;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

@SuppressWarnings("serial")
public class WelcomeDialog extends HorizontalLayout {
	
	private Button logout;
	
	private Label space;
	
	private Label hello;
	
	private NickiApplication application;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public WelcomeDialog(NickiApplication app) {
		this.application = app;
		buildMainLayout();
		
		hello.setText(I18n.getText("nicki.application.greeting", application.getNickiContext().getUser().getDisplayName()));
		logout.setText(I18n.getText("nicki.application.logout"));
		logout.addClickListener(event -> application.logout());
	}

	
	private void buildMainLayout() {
		// common part: create layout
		//horizontalLayout_1.setWidth("1000px");
		//horizontalLayout_1.setHeight("100px");
		setMargin(false);
		
		// hello
		hello = new Label();
		hello.setWidth("-1px");
		hello.setHeight("-1px");
		hello.setText("Hello");
		add(hello);
		setAlignItems(Alignment.BASELINE);
		
		// space
		space = new Label();
		space.setWidth("10px");
		space.setHeight("-1px");
		add(space);
		
		// logout
		logout = new Button();
		logout.setWidth("-1px");
		logout.setHeight("-1px");
		add(logout);
	}

}
