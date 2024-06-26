
package org.mgnl.nicki.vaadin.base.editor;

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
import java.util.function.Consumer;

import org.mgnl.nicki.core.data.TreeData;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import lombok.Getter;

	@SuppressWarnings("serial")
	public class BaseTreeAction extends VerticalLayout implements TreeAction, Serializable {

		private @Getter Class<? extends TreeData> targetClass;
		private @Getter String name;
		private Consumer<TreeData> command;
		
		public BaseTreeAction(Class<? extends TreeData> classDefinition, String name, Consumer<TreeData> command ) {
			this.targetClass = classDefinition;
			this.name = name;
			this.command = command;
			setSpacing(false);
			setMargin(false);
		}
		
		@Override
		public void close() {
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(getName()).append(": ").append(getTargetClass().getSimpleName());
			return sb.toString();
		}

		@Override
		public void execute(TreeData dynamicObject) {
			this.command.accept(dynamicObject);
			
		}

	}
