package org.mgnl.nicki.vaadin.base.components;

import java.util.ArrayList;

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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.SelectedChangeEvent;

import lombok.Getter;

@SuppressWarnings("serial")
public class NickiTabSheet extends Div {
	private @Getter Tabs tabs;
	private Map<Tab, Component> tabsToPages = new HashMap<>();
	private @Getter Div pagesDiv;
	private Tab activeTab;
	
	
	public NickiTabSheet() {
		tabs = new Tabs();
		tabs.setSizeFull();
		
		tabs.addSelectedChangeListener(event -> {
			if (tabs.getSelectedTab() != activeTab) {
			    Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
			    if (!showSelected(selectedPage, true)) {
			    	Tab previousTab = event.getPreviousTab();
			    	tabs.setSelectedTab(previousTab);
			    } else {
			    	activeTab = tabs.getSelectedTab();
			    }
			}
		});
		pagesDiv = new Div();
		pagesDiv.setSizeFull();
		initLayout();
	}
	
	protected void initLayout() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setMargin(false);
		layout.setSpacing(false);
		layout.setPadding(false);

		add(layout);
		layout.add(tabs, pagesDiv);
		
	}
	

	public void showSelected(Component selectedPage) {
		getPagesDiv().removeAll();
		getPagesDiv().add(selectedPage);		
	}

	public boolean showSelected(Component selectedPage, boolean checkModify) {
		getPagesDiv().removeAll();
		getPagesDiv().add(selectedPage);
		return true;
		
	}
	
	public Tab addTab(Component content, String label) {
		return addTab(content, new Span(label));
	}
	
	public Tab addTab(Component content, String label, VaadinIcon icon) {
		if (icon != null) {
			return addTab(content, icon.create(), new Span(label));
		} else {
			return addTab(content, new Span(label));
		}
	}

	public Tab addTab(Component content, Component... components) {
		Tab tab = new Tab(components);
		tabsToPages.put(tab, content);
		tabs.add(tab);
		return tab;
	}

	@Override
	public void remove(Component... components) {
		if (components != null) {
			List<Tab> toBeRemoved = new ArrayList<Tab>();
			for (Component component : components) {
				for (Tab tab: tabsToPages.keySet()) {
					if (component == tabsToPages.get(tab)) {
						if (component == getSelectedTabComponent()) {
							tabs.setSelectedTab(null);
						}
						toBeRemoved.add(tab);
					}
				}
			}
			if (toBeRemoved.size() > 0) {
				for (Tab tab : toBeRemoved) {
					tabs.remove(tab);
					tabsToPages.remove(tab);
				}
			}
		}
		// TODO Auto-generated method stub
		super.remove(components);
	}

	public void setSelectedTab(Tab tab) {
		tabs.setSelectedTab(tab);
	}

	public void setSelectedTabComponent(Component component) {
		for (Tab tab: tabsToPages.keySet()) {
			if (component == tabsToPages.get(tab)) {
				tabs.setSelectedTab(tab);
			}
		}
	}


	public void addSelectedTabChangeListener(ComponentEventListener<SelectedChangeEvent> listener) {
		tabs.addSelectedChangeListener(listener);
		
	}


	public Tab getSelectedTab() {
		return tabs.getSelectedTab();
	}
	
	public Component getSelectedTabComponent() {
		if (getSelectedTab() != null) {
			return tabsToPages.get(getSelectedTab());
		} else {
			return null;
		}
	}


	public Iterator<Tab> iterator() {
		return tabsToPages.keySet().iterator();
	}


	public Component getPage(Tab tab) {
		return tabsToPages.get(tab);
	}
}
