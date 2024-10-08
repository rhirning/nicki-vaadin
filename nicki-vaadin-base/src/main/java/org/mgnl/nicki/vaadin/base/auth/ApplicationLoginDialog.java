
package org.mgnl.nicki.vaadin.base.auth;

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



import org.apache.commons.lang3.StringUtils;
import org.mgnl.nicki.core.i18n.I18n;
import org.mgnl.nicki.core.objects.DynamicObjectException;
import org.mgnl.nicki.vaadin.base.application.NickiApplication;
import org.mgnl.nicki.vaadin.base.notification.Notification;
import org.mgnl.nicki.vaadin.base.notification.Notification.Type;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("serial")
public class ApplicationLoginDialog extends FlexLayout implements LoginDialog {
	
	private static int MAX_COUNT = 3;

	private TextField userName;
	private PasswordField password;
	private @Getter @Setter NickiApplication application;
	private int count = 0;

	public ApplicationLoginDialog() {
		buildUI();
	}

    private void buildUI() {
        setSizeFull();
        setClassName("login-screen");
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        /*
        // layout to center login form when there is sufficient screen space
        FlexLayout centeringLayout = new FlexLayout();
        centeringLayout.setSizeFull();
        centeringLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        centeringLayout.setAlignItems(Alignment.CENTER);

    	
        centeringLayout.add(getLoginForm());
        add(centeringLayout);
*/
        add(getLoginForm());
    }
    
    protected Component getLoginForm() {
    	FormLayout loginLayout = new FormLayout();
    	loginLayout.setSizeUndefined();
    	loginLayout.setHeight("-1px");
    	
    	userName = new TextField("User");
    	userName.focus();
    	
    	password = new PasswordField("Passwort");
    	
    	Button loginButton = new Button("Log in");
    	loginButton.addClassName("loginButton");
    	loginButton.setWidth("100%");
    	loginButton.addClickListener(e -> login());
    	loginButton.addClickShortcut(Key.ENTER);
    	
    	loginLayout.add(userName, password, loginButton);
    	return loginLayout;
    }

    protected Component getErrorDialog() {
    	Div div = new Div();
    	H5 title = new H5("Falscher User oder falsches Passwort");
    	title.addClassName("login-error");
    	
    	Paragraph p = new Paragraph("Pr�fen Sie Username und Passwort und versuchen Sie es noch einmal");
    	p.setWidth("300px");
    	p.addClassName("login-error");
    	div.add(title, p);
		return div;
	}

	private void login() {
		count++;
		if (count > MAX_COUNT) {
			Notification.show(I18n.getText("nicki.application.error.too.many.attempts"), Type.HUMANIZED_MESSAGE);
			return;
		}
		if (getApplication().login(StringUtils.stripToEmpty(userName.getValue()), StringUtils.stripToEmpty(password.getValue()))) {
			try {
				getApplication().start();
			} catch (DynamicObjectException e) {
				log.error("Error", e);
			}
		} else {
			new Dialog(getErrorDialog()).open();
		}

	}
}
