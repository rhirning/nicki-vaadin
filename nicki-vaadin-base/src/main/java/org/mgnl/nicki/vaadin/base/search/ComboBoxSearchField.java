
package org.mgnl.nicki.vaadin.base.search;

/*-
 * #%L
 * nicki-vaadin-base
 * %%
 * Copyright (C) 2017 Ralf Hirning
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


import java.util.Map;

import org.mgnl.nicki.core.objects.DynamicAttribute;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;

import lombok.Getter;


public class ComboBoxSearchField<T> implements DynamicAttributeSearchField<T> {
	private @Getter ComboBox<String> comboBox = new ComboBox<>();

	@Override
	public Component getComponent() {
		return comboBox;
	}

	@Override
	public void init(DynamicAttribute dynAttribute,
			Map<DynamicAttribute, String> map) {
		comboBox.setLabel(dynAttribute.getName());
	}

	@Override
	public void setWidth(String width) {
		comboBox.setWidth(width);
	}


}
