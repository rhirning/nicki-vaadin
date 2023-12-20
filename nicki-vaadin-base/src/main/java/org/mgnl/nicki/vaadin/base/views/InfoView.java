package org.mgnl.nicki.vaadin.base.views;



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
import org.mgnl.nicki.vaadin.ckeditor.Constants.EditorType;
import org.mgnl.nicki.vaadin.ckeditor.Constants.ThemeType;
import org.mgnl.nicki.vaadin.ckeditor.VaadinCKEditor;
import org.mgnl.nicki.vaadin.ckeditor.VaadinCKEditorBuilder;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class InfoView extends BaseInfoView implements ConfigurableView {

	private static final long serialVersionUID = -9132765874201737313L;
	private VaadinCKEditor editText;

	@Override
	public void setValue(String data) {
		editText.setValue(data);
	}

	@Override
	public void addComponent(VerticalLayout canvas) {
		// editText
		editText = new VaadinCKEditorBuilder().with(builder -> {
			builder.editorData = "";
			builder.editorType = EditorType.CLASSIC;
//		    builder.theme = ThemeType.DARK;
		}).createVaadinCKEditor();
//		editText.setSizeFull();
		canvas.add(editText);
	}

	@Override
	protected String getValue() {
		return editText.getValue();
	}

}
