package org.mgnl.nicki.vaadin.base.components;

import org.vaadin.klaudeta.quill.QuillEditor;

/*-
 * #%L
 * nicki-vaadin-base
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


import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Pre;

@SuppressWarnings("serial")
public class RichTextArea extends Div {
	
	private QuillEditor editText;
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
		editText = new QuillEditor();
		//editText.setSizeFull();
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
		return editText.getValue();
	}
}
