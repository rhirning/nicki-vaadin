
package org.mgnl.nicki.vaadin.base.fields;

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


import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.mgnl.nicki.core.objects.DynamicObject;
import org.mgnl.nicki.vaadin.base.data.AttributeDataContainer;
import org.mgnl.nicki.vaadin.base.data.DataContainer;
import org.mgnl.nicki.vaadin.base.editor.DynamicObjectValueChangeListener;
import org.mgnl.nicki.vaadin.base.listener.AttributeInputListener;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;

@SuppressWarnings("serial")
public class AttributeTextField  extends BaseDynamicAttributeField implements DynamicAttributeField<String>, Serializable {

	private TextField field;
	private DataContainer<String> property;
	public void init(String attributeName, DynamicObject dynamicObject, DynamicObjectValueChangeListener<String> objectListener) throws AttributeFieldException {

		property = new AttributeDataContainer<String>(dynamicObject, attributeName);
		field = new TextField(getName(dynamicObject, attributeName));
//		field.setHeight("2em");
//		field.setWidth("600px");
		try {
			field.setValue(StringUtils.stripToEmpty(property.getValue()));
		} catch (Exception e) {
			throw new AttributeFieldException(e);
		}
		field.addValueChangeListener(new AttributeInputListener<String>(property, objectListener));
	}

	public Component getComponent(boolean readOnly) {
		field.setReadOnly(readOnly);
		return field;
	}
	
}
