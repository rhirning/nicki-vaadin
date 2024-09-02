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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.mgnl.nicki.core.config.Config;
import org.mgnl.nicki.core.util.Classes;
import org.mgnl.nicki.vaadin.base.application.NickiApplication;
import org.mgnl.nicki.vaadin.base.application.ShowWelcomeDialog;
import org.mgnl.nicki.vaadin.base.data.SystemInfoEntry;
import org.mgnl.nicki.vaadin.base.data.SystemInfoEntry.TYPE;
import org.mgnl.nicki.vaadin.base.menu.application.ConfigurableView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("serial")
@ShowWelcomeDialog(
		configKey="pnw.viewer.log.useWelcomeDialog",
		groupsConfigName="pnw.app.viewer.log.useWelcomeDialogGroups")
public class SystemInfoView extends VerticalLayout implements ConfigurableView {

	private @Getter @Setter NickiApplication application;
	private Grid<SystemInfoEntry> infoGrid;
	private boolean isInit;
	private @Getter @Setter Map<String, String> configuration;

	public SystemInfoView() {
		buildMainLayout();
	}

	private void initInfoGrid() {
		List<SystemInfoEntry> entries = new ArrayList<SystemInfoEntry>();
		entries.add(new SystemInfoEntry(TYPE.VERSION, "SW Version", getApplication().getClass().getPackage().getImplementationVersion()));
		entries.add(new SystemInfoEntry(TYPE.VERSION, "Nicki Vaadin Version", NickiApplication.class.getPackage().getImplementationVersion()));
		entries.add(new SystemInfoEntry(TYPE.VERSION, "Nicki Version", Config.class.getPackage().getImplementationVersion()));

		if (configuration != null) {
			for (String key : configuration.keySet()) {
				if (!StringUtils.equals(key, "view") && StringUtils.countMatches(configuration.get(key), ".") > 2) {
					try {
						Class<?> clazz = Classes.newInstance(configuration.get(key)).getClass();
						entries.add(new SystemInfoEntry(TYPE.VERSION, key, clazz.getPackage().getImplementationVersion()));
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
						log.error("Error creating version entry for " + configuration.get(key));
					}
				}
			}
		}
		
		for (Entry<Object, Object> property : System.getProperties().entrySet()) {
			if (property.getKey() instanceof String && property.getValue() instanceof String) {
				String key = (String ) property.getKey();
				String value = (String ) property.getValue();
				entries.add(new SystemInfoEntry(TYPE.SYSTEM_PROPERTY, key, value));
			}
			
		}
		for (String key : System.getenv().keySet()) {
			entries.add(new SystemInfoEntry(TYPE.SYSTEM_ENVIRONMENT, key, System.getenv().get(key)));
		}
		
		try {
			// Check JNDI environment
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			for (Entry<?, ?> property : envCtx.getEnvironment().entrySet()) {
				if (property.getKey() instanceof String && property.getValue() instanceof String) {
					String key = (String ) property.getKey();
					String value = (String ) property.getValue();
					entries.add(new SystemInfoEntry(TYPE.JNDI_ENVIRONMENT, key, value));
				}
			}
			NamingEnumeration<Binding> bindings = initCtx.listBindings("java:comp/env");
			while (bindings.hasMore()) {
				Binding binding = bindings.next();
				if (binding.getObject() instanceof String) {
					String key = binding.getName();
					String value = (String) binding.getObject();
					entries.add(new SystemInfoEntry(TYPE.JNDI_BINDING, key, value));
				}
			}
		} catch (NamingException e) {
			log.error("Error reading env context java:comp/env", e);
		}
		
		infoGrid.setItems(entries.stream().sorted((v1, v2) -> {
			if (v1.getType() == v2.getType()) {
				return v1.getName().compareTo(v2.getName());
			} else {
				return v1.getType().name().compareTo(v2.getType().name());
			}
		}).collect(Collectors.toList()));
	}

	private void buildMainLayout() {
		setSizeFull();
		setMargin(true);
		setSpacing(true);
		infoGrid = new Grid<SystemInfoEntry>();
		infoGrid.setSizeFull();
		add(infoGrid);
	}

	@Override
	public void init() {
		if (!isInit) {
			infoGrid.addColumn(SystemInfoEntry::getType).setHeader("Type").setSortable(true);
			infoGrid.addColumn(SystemInfoEntry::getName).setHeader("Name").setSortable(true);
			infoGrid.addColumn(SystemInfoEntry::getValue).setHeader("Wert").setSortable(true);
			infoGrid.setItemDetailsRenderer(new ComponentRenderer<>(e -> getDetails(e)));
			initInfoGrid();
			
			
			isInit = true;
		}
	}

	private Component getDetails(SystemInfoEntry entry) {
		StringBuilder sb = new StringBuilder();
		sb.append("<table>");
		sb.append("<tr><td>Typ</td><td>").append(entry.getType()).append("</td>");
		sb.append("<tr><td>Name</td><td>").append(entry.getName()).append("</td>");
		sb.append("<tr><td>Wert</td><td>").append(entry.getValue()).append("</td>");
		sb.append("</table>");
		return new Html(sb.toString());
	}

	@Override
	public boolean isModified() {
		// TODO Auto-generated method stub
		return false;
	}

}
