
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


import org.mgnl.nicki.vaadin.base.data.DataContainer;
import org.mgnl.nicki.vaadin.base.editor.DynamicObjectValueChangeListener;

import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;

@SuppressWarnings("serial")
public class BooleanAttributeInputListener implements ValueChangeListener<ValueChangeEvent<Boolean>> {

	private DataContainer<String> property;
	private DynamicObjectValueChangeListener<Boolean> objectListener;

	public BooleanAttributeInputListener(DataContainer<String> property, DynamicObjectValueChangeListener<Boolean> objectListener) {
		this.property = property;
		this.objectListener = objectListener;
	}

	@Override
	public void valueChanged(ValueChangeEvent<Boolean> event) {
		Boolean value = event.getValue();
		property.setValue(Boolean.toString(value));
		property.getDynamicObject().setModified(true);
		if (objectListener != null) {
			objectListener.valueChange(property.getDynamicObject(), property.getAttributeName(), value);
		}
	}

}
