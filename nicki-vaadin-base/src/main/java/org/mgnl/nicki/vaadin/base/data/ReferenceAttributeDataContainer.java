
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
public class ReferenceAttributeDataContainer implements DataContainer<DynamicObject> {


	private @Getter DynamicObject dynamicObject;
	private @Getter String attributeName;
	private @Getter @Setter boolean readOnly = false;
	
	public ReferenceAttributeDataContainer(DynamicObject dynamicObject, String attributeName) {
		this.dynamicObject = dynamicObject;
		this.attributeName = attributeName;
	}

	public DynamicObject getValue() {
		return (DynamicObject) dynamicObject.get(attributeName);
	}

	public void setValue(DynamicObject newValue)  {
		dynamicObject.put(attributeName, newValue);
	}

	@Override
	public String getDisplay() {
		return dynamicObject.getPath();
	}
}
