package org.mgnl.nicki.vaadin.base.views;

import com.vaadin.flow.component.Component;

@SuppressWarnings("serial")
public abstract class Editor extends Component {

	abstract void setValue(String data);

	abstract void setReadOnly(boolean b);

	abstract String getValue();

	abstract void setSizeFull();

}
