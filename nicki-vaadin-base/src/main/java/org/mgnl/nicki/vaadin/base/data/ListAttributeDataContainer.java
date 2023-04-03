
package org.mgnl.nicki.vaadin.base.data;

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


import org.mgnl.nicki.core.objects.DynamicObject;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
public class ListAttributeDataContainer<T> implements DataContainer<T> {

	private @Getter DynamicObject dynamicObject;
	private @Getter String attributeName;
	private @Getter @Setter boolean readOnly = false;
	
	public ListAttributeDataContainer(DynamicObject dynamicObject, String attributeName) {
		this.dynamicObject = dynamicObject;
		this.attributeName = attributeName;
	}

	@SuppressWarnings("unchecked")
	public T getValue() {
		return (T) dynamicObject.get(attributeName);
	}

	public void setValue(Object newValue) {
		dynamicObject.put(attributeName, newValue);
	}

	@SuppressWarnings("unchecked")
	public Class<? extends T> getType() {
		return (Class<? extends T>) dynamicObject.getModel().getDynamicAttribute(attributeName).getClass();
	}

	@Override
	public String getDisplay() {
		return (String) getValue();
	}

}
