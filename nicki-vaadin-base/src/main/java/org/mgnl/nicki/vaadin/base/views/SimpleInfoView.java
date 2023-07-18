package org.mgnl.nicki.vaadin.base.views;

import org.mgnl.nicki.vaadin.base.menu.application.ConfigurableView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

public class SimpleInfoView extends BaseInfoView implements ConfigurableView {

	private static final long serialVersionUID = 8889337517687030124L;
	private TextArea textArea;


	@Override
	public void setValue(String data) {
		textArea.setValue(data);
	}

	@Override
	public void addComponent(VerticalLayout canvas) {
		textArea = new TextArea();
		textArea.setSizeFull();
		canvas.add(textArea);
		
	}

	@Override
	protected String getValue() {
		return textArea.getValue();
	}

}