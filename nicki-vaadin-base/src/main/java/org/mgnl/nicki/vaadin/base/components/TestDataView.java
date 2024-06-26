
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



import java.io.Serializable;
import java.util.Map;

import org.mgnl.nicki.core.i18n.I18n;
import org.mgnl.nicki.vaadin.base.data.DataContainer;
import org.mgnl.nicki.vaadin.base.listener.TestDataValueChangeListener;

import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;


@SuppressWarnings("serial")
public class TestDataView extends VerticalLayout {
	
	private Button newButton;

	
	private VerticalLayout testData;

	public static final String SEPARATOR = "=";

	private DataContainer<Map<String, String>> data;
	private ValueChangeListener<ValueChangeEvent<String>> listener;
	private EnterNameDialog newFieldWindow;
	private String messageKeyBase;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public TestDataView(DataContainer<Map<String, String>> dataContainer, String messageKeyBase) {
		this.data = dataContainer;
		this.messageKeyBase = messageKeyBase + ".testdata";
		buildMainLayout();
		listener = new TestDataValueChangeListener(dataContainer, testData);
		for (String name : data.getValue().keySet()) {
			addField(name, data.getValue().get(name));
		}
		newButton.addClickListener(event -> addNewField());
	}
	
	private void addField(String name, String value) {
		TextField field = new TextField(name, value);
		field.addValueChangeListener(listener);
		field.setWidth("100%");
		testData.add(field);

	}

	protected void addNewField() {
		newFieldWindow = new EnterNameDialog(messageKeyBase,
				I18n.getText(messageKeyBase + ".window.title"));
		newFieldWindow.setHandler(new NewFieldHandler(""));
		newFieldWindow.setWidth("440px");
		newFieldWindow.setHeight("500px");
		newFieldWindow.setModal(true);
		newFieldWindow.open();
	}
	
	public class NewFieldHandler extends EnterNameHandler implements Serializable {

		public NewFieldHandler(String initialName) {
			super(initialName);
		}

		public void setName(String name) {
			addField(name, "");
		}

		public void closeEnterNameDialog() {
			newFieldWindow.close();
		}

	}

	
	private void buildMainLayout() {
		// common part: create layout
		setWidth("100.0%");
		setHeight("-1px");
		setMargin(false);
		
		// testData
		testData = new VerticalLayout();
		testData.setWidth("100.0%");
		testData.setHeight("-1px");
		testData.setMargin(false);
		add(testData);
		
		// newButton
		newButton = new Button();
		newButton.setWidth("-1px");
		newButton.setHeight("-1px");
		newButton.setText("Neu");
		add(newButton);
	}

}
