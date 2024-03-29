
package org.mgnl.nicki.vaadin.base.components;

import java.util.Collection;

import org.mgnl.nicki.core.data.ValuePair;

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



import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import lombok.Getter;
import lombok.Setter;

public class DataView extends VerticalLayout {
	
	private Grid<ValuePair> dataTable;
	
	private Label title;
	private String titleString;
// TODO:	private int length;
	private @Setter @Getter String i18nBase;
	Collection<ValuePair> data;
	
	private static final long serialVersionUID = -1786011895038782304L;

	public DataView(Collection<ValuePair> data, String titleString, int length, String i18nBase) {
		this.titleString = titleString;
		this.data = data;
		// TODO: this.length = length;
		this.i18nBase = i18nBase;

		buildMainLayout();
		init();
	}
	
	private void init() {
		title.setText(titleString);
		dataTable.setItems(data);
		dataTable.addColumn(ValuePair::getName).setWidth("200px");
		dataTable.addColumn(ValuePair::getValue).setWidth("600px");
//TODO:		userTable.setPageLength(this.length);
	}

	
	private void buildMainLayout() {
		// common part: create layout
		setWidth("100%");
		setHeight("100%");
		
		// title
		title = new Label();
		title.setWidth("-1px");
		title.setHeight("-1px");
		title.setText("Verantwortlichkeit aendern");
		add(title);
		
		// userTable
		dataTable = new Grid<ValuePair>();
		dataTable.setWidth("-1px");
		dataTable.setHeight("-1px");
		add(dataTable);
		
	}

}
