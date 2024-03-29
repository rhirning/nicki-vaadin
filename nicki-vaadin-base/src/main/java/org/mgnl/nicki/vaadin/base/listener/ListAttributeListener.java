
package org.mgnl.nicki.vaadin.base.listener;

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


import java.util.List;

import org.mgnl.nicki.core.objects.DynamicObject;
import org.mgnl.nicki.vaadin.base.data.DataContainer;
import org.mgnl.nicki.vaadin.base.editor.DynamicObjectValueChangeListener;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;

@SuppressWarnings("serial")
public class ListAttributeListener extends BaseAttributeListener<String> implements ValueChangeListener<ValueChangeEvent<String>> {

	private DataContainer<List<String>> property;
	private Component container;
	private DynamicObjectValueChangeListener<String> objectListener;
	
	private ListAttributeListener(Component container,
			DynamicObject dynamicObject, String name) {
		super(dynamicObject, name);
		this.container = container;
	}
	public void valueChanged(ValueChangeEvent<String> event) {
		List<String> values = collectValues(this.container);
		property.setValue(values);
		if (values.size() > 0) {
			getDynamicObject().put(getName(), values);
		} else {
			getDynamicObject().remove(getName());
		}
		if (objectListener != null) {
			objectListener.valueChange(property.getDynamicObject(), getName(), values);
		}
		property.getDynamicObject().setModified(true);

		
		
	}
	public ListAttributeListener(DynamicObject dynamicObject, String attributeName,
			DataContainer<List<String>> property, Component container, DynamicObjectValueChangeListener<String> objectListener) {
		super(dynamicObject, attributeName);
		this.property = property;
		this.container = container;
		this.objectListener = objectListener;
	}
}
