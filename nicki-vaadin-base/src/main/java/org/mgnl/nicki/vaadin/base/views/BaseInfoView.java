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

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mgnl.nicki.core.util.Classes;
import org.mgnl.nicki.dynamic.objects.objects.Person;
import org.mgnl.nicki.vaadin.base.application.NickiApplication;
import org.mgnl.nicki.vaadin.base.menu.application.ConfigurableView;
import org.mgnl.nicki.vaadin.base.notification.Notification;
import org.mgnl.nicki.vaadin.base.notification.Notification.Type;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Ralf Hirning
 *
 * configuration:
 * 
 * editorGroup					Group for editors
 * storeClass					FQN
 * targetName (optional)		Name of Target where to store the script
 * configPath					Config key for Script path
 * 
 */
@Slf4j
public abstract class BaseInfoView extends VerticalLayout implements ConfigurableView {
	
	private VerticalLayout canvas;
	private HorizontalLayout buttonLayout;

	private Button saveInfoButton;

	private Button editInfoButton;

	private static final long serialVersionUID = -4894326575778098227L;
	
	private @Setter NickiApplication application;
	
	private boolean isInit;
	
	private InfoStore infoStore;
	
	private String data;
	
	private @Getter @Setter Map<String, String> configuration;
	
	public BaseInfoView() {
		buildMainLayout();
	}

	@Override
	public void init() {

		if (!isInit) {
			
			try {
				infoStore = Classes.newInstance(configuration.get("storeClass"));
				infoStore.setConfiguration(configuration);
				if (infoStore.getData() != null ) {
					data = infoStore.getData();
					canvas.add(new Html("<div>" + data + "</div>"));
				}
			} catch (Exception e) {
				log.error("could not load Info", e);
				Notification.show("could not load Info", e.getMessage(), Type.ERROR_MESSAGE);
				infoStore = null;
			}
			editInfoButton.setVisible(false);
			saveInfoButton.setVisible(false);

			if (isEditor()) {
				editInfoButton.addClickListener(event -> {
					canvas.removeAll();
					addComponent(canvas);
					setValue(data);
					saveInfoButton.setVisible(true);
					editInfoButton.setVisible(false);
				});
	
				saveInfoButton.addClickListener(event -> {
					saveInfo();
					canvas.removeAll();
					canvas.add(new Html("<div>" + data + "</div>"));
					saveInfoButton.setVisible(false);
					editInfoButton.setVisible(true);
				});
				editInfoButton.setVisible(true);
				saveInfoButton.setVisible(false);
			} else {
				editInfoButton.setVisible(false);
				saveInfoButton.setVisible(false);				
			}
		}
		
		isInit = true;
	}

	public abstract void setValue(String data);

	public abstract void addComponent(VerticalLayout canvas);

	private boolean isEditor() {
		String editorGroups = configuration.get("editorGroup");
		String editorRoles = configuration.get("editorRole");
		return isGroupMember(getPerson(), StringUtils.split(editorGroups, ","))
				|| hasRole(getPerson(), StringUtils.split(editorRoles, ","));
	}

	private Person getPerson() {
		return (Person) application.getDoubleContext().getLoginContext().getUser();
	}
	
	public boolean isGroupMember(Person person, String[] groups) {
		if (groups != null && groups.length > 0) {
			for (String group : groups) {
				if (person.isMemberOf(group)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasRole(Person person, String[] roles) {
		if (roles != null) {
			for (String role : roles) {
				if (person.hasRole(role)) {
					return true;
				}
			}
		}
		return false;
	}

	protected void saveInfo() {
		data = getValue();
		if (infoStore != null) {
			try {
				infoStore.setData(data);
				infoStore.save();
			} catch (Exception e) {
				Notification.show("could not update Info", e.getMessage(), Type.ERROR_MESSAGE);
			}
		} else {
			Notification.show("could not create Info", Type.ERROR_MESSAGE);
		}

	}

	protected abstract String getValue();

	@Override
	public boolean isModified() {
		return false;
	}

	private void buildMainLayout() {
		setSizeFull();
		setMargin(false);
		setSpacing(false);
		setPadding(false);
		
		// buttonLayout
		buttonLayout = buildButtonLayout();
		add(buttonLayout);

		canvas = new VerticalLayout();
		//canvas.setSizeFull();
		add(canvas);
		
		setFlexGrow(1, canvas);
	}

	private HorizontalLayout buildButtonLayout() {
		// common part: create layout
		buttonLayout = new HorizontalLayout();
		buttonLayout.setMargin(false);
		buttonLayout.setSpacing(true);
		
		// editButton
		editInfoButton = new Button("Edit");
		buttonLayout.add(editInfoButton);
		
		// saveButton
		saveInfoButton = new Button("Save");
		buttonLayout.add(saveInfoButton);
		
		return buttonLayout;
	}

}
