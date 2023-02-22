package org.mgnl.nicki.vaadin.base.menu.application;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.mgnl.nicki.core.helper.DataHelper;
import org.mgnl.nicki.core.i18n.I18n;
import org.mgnl.nicki.core.util.Classes;
import org.mgnl.nicki.vaadin.base.application.NickiApplication;
import org.mgnl.nicki.vaadin.base.components.NickiTabSheet;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
public class MultiTabViewer extends VerticalLayout implements ConfigurableView {

	private NickiTabSheet tabSheet;

	private @Getter NickiApplication application;

	private @Getter @Setter Map<String, String> configuration;

	private boolean isInit;

	public MultiTabViewer() {
		buildMainLayout();
	}

	private void buildViewerTabs() {
		for (String viewer : getViewers()) {
			Map<String, String> viewerConfiguration = getConfiguration(viewer);

			try {
				Component view = getView(viewerConfiguration);
				tabSheet.addTab(view, I18n.getText(viewerConfiguration.get("title")));
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private Component getView(Map<String, String> viewerConfiguration)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Component component = Classes.newInstance(viewerConfiguration.get("view"));
		if (component instanceof View) {
			View view = (View) component;
			view.setApplication(getApplication());
			if (component instanceof ConfigurableView) {
				ConfigurableView configurableView = (ConfigurableView) component;
				configurableView.setConfiguration(viewerConfiguration);
			}
			view.init();
		}
		return component;
	}

	private List<String> getViewers() {
		return DataHelper.getList(getConfiguration().get("viewers"), ",");
	}

	private Map<String, String> getConfiguration(String viewer) {
		Map<String, String> config = new HashMap<String, String>();
		for (String key : getConfiguration().keySet()) {
			if (StringUtils.startsWith(key, viewer + ".")) {
				String viewerKey = StringUtils.substringAfter(key, viewer + ".");
				config.put(viewerKey, getConfiguration().get(key));
			}
		}
		return config;
	}

	private void buildMainLayout() {
		setWidth("100%");
		setHeight("100%");
		setMargin(false);
		setSpacing(false);

		tabSheet = new NickiTabSheet();
		tabSheet.setSizeFull();
		add(tabSheet);
		setFlexGrow(1, tabSheet);
	}

	@Override
	public void init() {
		if (!isInit) {
			buildViewerTabs();
			isInit = true;
		}

	}

	@Override
	public boolean isModified() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setApplication(NickiApplication application) {
		this.application = application;
	}

}
