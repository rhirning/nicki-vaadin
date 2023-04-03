package org.mgnl.nicki.vaadin.base.components;

/*-
 * #%L
 * nicki-vaadin-base
 * %%
 * Copyright (C) 2020 - 2021 Ralf Hirning
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

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import lombok.Setter;

@SuppressWarnings("serial")
public class GridLayout extends VerticalLayout {

	private @Setter int columns;
	private HorizontalLayout lastHorizontalLayout;
	public GridLayout(int columns) {
		this.columns = columns;
		
	}
	@Override
	public void add(Component... components) {
		if (columns == 1) {
			super.add(components);
			return;
		}
		if (components != null) {
			for (Component component :components) {
				if (lastHorizontalLayout == null || (columns > -1 && lastHorizontalLayout.getComponentCount() >= columns)) {
					lastHorizontalLayout = new HorizontalLayout();
					lastHorizontalLayout.setSpacing(true);
					lastHorizontalLayout.setMargin(false);
					lastHorizontalLayout.setPadding(false);
					super.add(lastHorizontalLayout);
				}
				lastHorizontalLayout.add(component);
			}
		}
	}
}
