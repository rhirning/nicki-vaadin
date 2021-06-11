package org.mgnl.nicki.vaadin.base.components;

import com.vaadin.flow.component.textfield.TextField;

@SuppressWarnings("serial")
public class NickiLabel extends TextField {

	public NickiLabel() {
		super();
		setReadonly(true);
	}

	public NickiLabel(String value) {
		this();
		setValue(value);
	}
	
	public void setCaption(String label) {
		setLabel(label);
	}

	public void setStyleName(String className) {
		setClassName(className, true);
	}
	
}
