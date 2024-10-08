
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

import org.apache.commons.lang3.StringUtils;
import org.mgnl.nicki.core.i18n.I18n;
import org.mgnl.nicki.core.objects.DynamicAttribute;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;


public class TextFieldSearchField<T> implements DynamicAttributeSearchField<T> {
	private TextField textField = new TextField();
	private DynamicAttribute dynAttribute;
	private Map<DynamicAttribute, String> map;

	@Override
	public Component getComponent() {
		return textField;
	}

	@Override
	public void init(DynamicAttribute dynamicAttribute,
			Map<DynamicAttribute, String> searchMap) {
		this.dynAttribute = dynamicAttribute;
		this.map = searchMap;
		textField.setLabel(I18n.getText(dynAttribute.getCaption(), dynAttribute.getName()));
		textField.addValueChangeListener(event -> {
				String value = event.getValue();
				if (StringUtils.isNotBlank(value)) {
					map.put(dynAttribute, value);
				} else if (map.containsKey(dynAttribute)) {
					map.remove(dynAttribute);
				}
		});
	}

	@Override
	public void setWidth(String width) {
		textField.setWidth(width);
	}

}
