package org.mgnl.nicki.vaadin.base.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@SuppressWarnings("serial")
public class GridLayout extends VerticalLayout {

	private int columns;
	private HorizontalLayout lastHorizontalLayout;
	public GridLayout(int columns) {
		this.columns = columns;
	}
	@Override
	public void add(Component... components) {
		if (columns < 2) {
			super.add(components);
			return;
		}
		if (components != null) {
			for (Component component :components) {
				if (lastHorizontalLayout == null || lastHorizontalLayout.getComponentCount() > columns) {
					lastHorizontalLayout = new HorizontalLayout();
					lastHorizontalLayout.setSpacing(true);
					lastHorizontalLayout.setMargin(false);
					add(lastHorizontalLayout);
				}
				lastHorizontalLayout.add(component);
			}
		}
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	

}
