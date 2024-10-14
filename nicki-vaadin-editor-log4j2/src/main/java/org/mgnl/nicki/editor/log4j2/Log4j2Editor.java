package org.mgnl.nicki.editor.log4j2;

/*-
 * #%L
 * nicki-editor-log4j
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

import org.mgnl.nicki.core.context.Target;
import org.mgnl.nicki.core.context.TargetFactory;
import org.mgnl.nicki.vaadin.base.application.AccessGroup;
import org.mgnl.nicki.vaadin.base.application.NickiApplication;
import org.mgnl.nicki.vaadin.base.application.ShowWelcomeDialog;
import com.vaadin.flow.component.Component;

@SuppressWarnings("serial")
@AccessGroup(name = {"nickiAdmins", "IDM-Development"})
@ShowWelcomeDialog(
		configKey="nicki.app.editor.log4j.useWelcomeDialog",
		groupsConfigName="nicki.app.editor.log4j.useWelcomeDialogGroups")
public class Log4j2Editor extends NickiApplication {
	
	public Log4j2Editor() {
		super();
	}

	@Override
	public Component getEditor() {
		Log4jConfigViewer editor = new Log4jConfigViewer(this);
		editor.setSizeFull();
		editor.init();
		return editor;
	}

	@Override
	public Target getTarget() {
		return TargetFactory.getDefaultTarget();
	}

	@Override
	public String getI18nBase() {
		return "nicki.editor.log4j";
	}
	
}
