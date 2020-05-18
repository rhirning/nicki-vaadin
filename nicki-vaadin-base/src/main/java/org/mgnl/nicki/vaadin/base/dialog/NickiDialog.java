package org.mgnl.nicki.vaadin.base.dialog;

import org.mgnl.nicki.vaadin.base.application.NickiApplication;

import com.vaadin.flow.component.html.Div;

@SuppressWarnings("serial")
public abstract class NickiDialog extends Div {

	abstract public void setApplication(NickiApplication application);
}
