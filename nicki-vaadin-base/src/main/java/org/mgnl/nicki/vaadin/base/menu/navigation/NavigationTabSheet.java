package org.mgnl.nicki.vaadin.base.menu.navigation;

import java.util.HashMap;

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

import java.util.List;
import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

@SuppressWarnings("serial")
public class NavigationTabSheet extends Tabs  {
	private NavigationMainView appLayout;
	private Tab activeTab;
	private Map<Tab, Component> tabsToPages = new HashMap<>();
	
	public NavigationTabSheet(NavigationMainView appLayout) {
		this.appLayout = appLayout;

		setOrientation(Orientation.VERTICAL);
		setWidthFull();
		addSelectedChangeListener(event -> {
			if (getSelectedTab() != activeTab) {
			    Component selectedPage = tabsToPages.get(getSelectedTab());
			    if (!showSelected(selectedPage, true)) {
			    	Tab previousTab = event.getPreviousTab();
			    	setSelectedTab(previousTab);
			    } else {
			    	activeTab = getSelectedTab();
			    }
			}
		});
	}

	public boolean showSelected(Component selectedPage, boolean checkModify) {
		return appLayout.showView(selectedPage, checkModify);
	}

	public void init(List<NavigationFolder> navigationFolders) {
		Tab tab;
		for (NavigationFolder folder : navigationFolders) {
			if (folder.isSeparator()) {
				tab = addTab((Component) null, new NavigationSeparator());
				tab.addClassName("nav_separator");
			} else {
				tab = addTab(null, folder.getText());
				tab.addClassName("nav_folder");
				for (NavigationEntry entry : folder.getEntries()) {
					tab = addTab(entry.getView(), entry.getCaption());
					tab.addClassName("nav_entry");
				}
			}
		}
	}

	public void selectInNavigation(NavigationEntry entry) {
		// TODO Auto-generated method stub
		
	}
	public Tab addTab(Component content, String label) {
		return addTab(content, new Span(label));
	}
	
	public Tab addTab(Component content, String label, VaadinIcon icon) {
		return addTab(content, new Icon(icon), new Span(label));
	}

	public Tab addTab(Component content, Component... components) {
		Tab tab = new Tab(components);
		tabsToPages.put(tab, content);
		add(tab);
		return tab;
	}
	/*
	protected void initLayout() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSizeFull();
		layout.setMargin(false);
		layout.setSpacing(false);
		layout.setPadding(false);

		add(layout);
		layout.add(getTabs());
	}
		*/


}
