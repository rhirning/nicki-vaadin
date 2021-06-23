package org.mgnl.nicki.vaadin.base.components;

import org.vaadin.pekka.WysiwygE;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Pre;

@SuppressWarnings("serial")
public class RichTextArea extends Div {
	
	private WysiwygE editText;
	private Pre pre;
	private String text;
	
	public RichTextArea(String text) {
		init();
		this.text = text;
		pre.setText(text);
	}

	private void init() {
		pre = new Pre();
		pre.setSizeFull();
		editText = new WysiwygE();
		editText.setSizeFull();
		editText.addValueChangeListener(event -> {
			this.text = event.getValue();
		});
		add(pre);
	}

	public void setReadOnly(boolean b) {
		removeAll();
		if (b) {
			pre.setText(text);
			add(pre);
		} else {
			editText.setValue(text);
			add(editText);
		}
		
	}

	public String getValue() {
		return this.text;
	}
}
