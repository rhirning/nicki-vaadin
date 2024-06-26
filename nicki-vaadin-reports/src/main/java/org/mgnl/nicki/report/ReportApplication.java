
package org.mgnl.nicki.report;

/*-
 * #%L
 * nicki-reports
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


import org.mgnl.nicki.editor.templates.TemplateEditor;

import com.vaadin.flow.component.Component;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
public class ReportApplication extends TemplateEditor {
	private @Getter @Setter ReportComponent reportApplicationComponent;

	@Override
	public Component getEditor() {
		if (reportApplicationComponent == null) {
			reportApplicationComponent = new ReportComponent();
		}
		reportApplicationComponent.setApplication(this);
		reportApplicationComponent.init();
		return reportApplicationComponent;

	}

	@Override
	public String getI18nBase() {
		return "nicki.application.reports";
	}

}
