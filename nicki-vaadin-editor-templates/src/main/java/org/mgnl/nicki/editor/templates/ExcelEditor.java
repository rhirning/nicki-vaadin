
package org.mgnl.nicki.editor.templates;

/*-
 * #%L
 * nicki-editor-templates
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


import org.mgnl.nicki.dynamic.objects.objects.Template;
import org.mgnl.nicki.vaadin.base.components.SimpleEditor;
import org.mgnl.nicki.vaadin.base.components.SimpleUploadEditor;
import org.mgnl.nicki.vaadin.base.data.PartDataContainer;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ExcelEditor extends VerticalLayout {
	private static final long serialVersionUID = -4999034916650372313L;

	public ExcelEditor(String messageBase, Template template) {
		setWidth("100%");
		setHeight("100%");
		setMargin(false);
		
		SimpleUploadEditor simpleUploadEditor = new SimpleUploadEditor(messageBase,
				template, Template.ATTRIBUTE_FILE,
			"ExcelMaster.xls", "application/msexcel");
//		SimpleUploadEditor simpleUploadEditor = new SimpleUploadEditor(
//				new AttributeDataContainer<byte[]>(template, Template.ATTRIBUTE_FILE),
//			"ExcelMaster.xls", "application/msexcel");
		add(simpleUploadEditor);

		SimpleEditor simpleEditor = new SimpleEditor(new PartDataContainer(
				template, Template.ATTRIBUTE_PARTS, "xls", "="));
		add(simpleEditor);
		setFlexGrow(1, simpleEditor);
	}

}
