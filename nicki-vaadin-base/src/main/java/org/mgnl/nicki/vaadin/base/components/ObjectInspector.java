
package org.mgnl.nicki.vaadin.base.components;

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


import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.ExpandEvent;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;

public class ObjectInspector extends CustomComponent {

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private TreeTable tree;
	
	private HierarchicalContainer container;

	private static final long serialVersionUID = 3256191025469832300L;
	public ObjectInspector(Object... objects) {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		initTree(objects);

	}

	@SuppressWarnings("unchecked")
	private void initTree(Object... objects) {
		container = new HierarchicalContainer();
		container.addContainerProperty("name", String.class, null);
		container.addContainerProperty("type", String.class, null);
		container.addContainerProperty("value", String.class, null);
		
		for (Object object : objects) {
			if (object != null) {
				ObjectWrapper objectWrapper = new ObjectWrapper(object);
				container.addItem(objectWrapper);
				container.getContainerProperty(objectWrapper, "name").setValue("Object");
				container.getContainerProperty(objectWrapper, "type").setValue(object.getClass().getSimpleName());
				try {
					container.getContainerProperty(objectWrapper, "value").setValue(object.toString());
				} catch (Exception e) {
					container.getContainerProperty(objectWrapper, "value").setValue("null");
				}
				if (hasChildren(objectWrapper)) {
					tree.setChildrenAllowed(objectWrapper, true);
				} else {
					tree.setChildrenAllowed(objectWrapper, false);
				}
			}
		}
		tree.setContainerDataSource(container);
		tree.setColumnWidth("name", 200);
		tree.setColumnWidth("type", 120);
		
		
		tree.addExpandListener(new Tree.ExpandListener() {
			private static final long serialVersionUID = 8148298861638159882L;

			@Override
			public void nodeExpand(ExpandEvent event) {
				ObjectWrapper objectWrapper = (ObjectWrapper) event.getItemId();
				if (hasChildren(objectWrapper) && !container.hasChildren(objectWrapper)) {
					addChildren(objectWrapper);
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	protected void addChildren(ObjectWrapper objectWrapper) {
		Map<String, Object> attributes = getAttributes(objectWrapper);
		for (String name : attributes.keySet()) {
			Object child = attributes.get(name);
			ObjectWrapper o = new ObjectWrapper(child);
			if (child != null) {
				container.addItem(o);
				container.setParent(o, objectWrapper);
				container.getContainerProperty(o, "name").setValue(name);
				container.getContainerProperty(o, "type").setValue(child.getClass().getSimpleName());
				try {
					container.getContainerProperty(o, "value").setValue(child.toString());
				} catch (Exception e) {
					container.getContainerProperty(o, "value").setValue("null");
				}
			}
		}
	}

	private boolean hasChildren(ObjectWrapper objectWrapper) {
		return getAttributes(objectWrapper).size() > 0;
	}

	private Map<String, Object> getAttributes(ObjectWrapper objectWrapper) {
		Map<String, Object> map = new HashMap<>();
		AccessibleObject.setAccessible(objectWrapper.getObject().getClass().getDeclaredFields(), true);
		for (Field field : objectWrapper.getObject().getClass().getDeclaredFields()) {
			try {
				field.setAccessible(true);
				map.put(field.getName(), field.get(objectWrapper.getObject()));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (objectWrapper.getObject().getClass().isArray()) {
			int length = Array.getLength(objectWrapper.getObject());
		    for (int i = 0; i < length; i ++) {
				map.put("" + i, Array.get(objectWrapper.getObject(), i));
			}
		}
		return map;
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// tree
		tree = new TreeTable();
		tree.setImmediate(true);
		tree.setWidth("100.0%");
		tree.setHeight("100.0%");
		mainLayout.addComponent(tree);
		
		return mainLayout;
	}
	
	private class ObjectWrapper {
		private Object object;

		public ObjectWrapper(Object object) {
			this.object = object;
		}

		public Object getObject() {
			return object;
		}
	}

}
