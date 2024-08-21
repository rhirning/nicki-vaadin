package org.mgnl.nicki.vaadin.base.views;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/*-
 * #%L
 * nicki-vaadin-base
 * %%
 * Copyright (C) 2017 - 2018 Ralf Hirning
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

import org.mgnl.nicki.vaadin.base.menu.application.ConfigurableView;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

public class InfoView extends BaseInfoView implements ConfigurableView {

	private static final long serialVersionUID = -9132765874201737313L;
//	private VaadinCKEditor editText;
	
	private TextArea editText;
	public InfoView() {
		super();
	}

	@Override
	public void setValue(String data) {
        Document doc = Jsoup.parse(data);   // pretty print HTML
		editText.setValue(doc.toString());
	}

	@Override
	public void addComponent(VerticalLayout canvas) {
		// editText
		/**
		editText = new VaadinCKEditorBuilder().with(builder -> {
			builder.editorData = "";
			builder.editorType = EditorType.CLASSIC;
//		    builder.theme = ThemeType.DARK;
		}).createVaadinCKEditor();
		*/
		editText = new TextArea();
		editText.setSizeFull();
		canvas.add(editText);
	}

	@Override
	protected String getValue() {
		return editText.getValue();
	}

}
