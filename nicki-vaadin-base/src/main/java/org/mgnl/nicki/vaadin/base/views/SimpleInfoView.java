package org.mgnl.nicki.vaadin.base.views;

/*-
 * #%L
 * nicki-vaadin-base
 * %%
 * Copyright (C) 2020 - 2023 Ralf Hirning
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
