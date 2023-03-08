package org.mgnl.nicki.vaadin.base.menu.application;

import java.util.ArrayList;
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
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs.SelectedChangeEvent;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("serial")
public class MultiTabViewer extends VerticalLayout implements ConfigurableView {
	
	private NickiTabSheet tabSheet;

	private @Getter @Setter NickiApplication application;
	
	private @Getter @Setter Map<String, String> configuration;
	
	private List<View> viewers = new ArrayList<View>();

	private boolean isInit;
	public MultiTabViewer() {
		buildMainLayout();
		tabSheet.addSelectedTabChangeListener(event -> handleSelectedTab(event));
	}

	private void buildViewerTabs() {
		for (String viewer : getViewers()) {
			Map<String, String> viewerConfiguration = getConfiguration(viewer);
			
			try {
				Component view = getView(viewerConfiguration);
				if (view instanceof View) {
					viewers.add((View) view);					
				}
				tabSheet.addTab(view, I18n.getText(viewerConfiguration.get("title")));
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				log.error("Error creating view: " + viewerConfiguration );
			}
		}
		
	}

	private void handleSelectedTab(SelectedChangeEvent event) {
		Tab tab = event.getSelectedTab(); 
		Component tabComponent = tabSheet.getPage(tab);
		if (tabComponent instanceof View) {
			View view = (View) tabComponent;
			view.init();
		}
	}
	
	private Component getView(Map<String, String> viewerConfiguration) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Component component = Classes.newInstance(viewerConfiguration.get("view"));
		if (component instanceof View) {
			View view = (View) component;			
			view.setApplication(getApplication());
			if (component instanceof ConfigurableView) {
				ConfigurableView configurableView = (ConfigurableView) component;
				configurableView.setConfiguration(viewerConfiguration);
			}
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
		for (View view : viewers) {
			if (view.isModified()) {
				return true;
			}
		}
		return false;
	}

}
