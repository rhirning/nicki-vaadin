package org.mgnl.nicki.vaadin.base.components;

/*-
 * #%L
 * nicki-vaadin7-base
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

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tabs.Orientation;

@SuppressWarnings("serial")
public class NickiVerticalTabSheet extends NickiTabSheet {
		
	public NickiVerticalTabSheet() {
		super();
//		setHeightFull();
//		setWidth("-1px");
		getTabs().setOrientation(Orientation.VERTICAL);
	}

	
	protected void initLayout() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSizeFull();
		layout.setMargin(false);
		layout.setSpacing(false);
		layout.setPadding(false);

		add(layout);
		layout.add(getTabs(), getPagesDiv());
		
	}
}
