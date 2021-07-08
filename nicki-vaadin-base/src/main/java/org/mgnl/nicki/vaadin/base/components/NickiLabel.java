package org.mgnl.nicki.vaadin.base.components;

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
