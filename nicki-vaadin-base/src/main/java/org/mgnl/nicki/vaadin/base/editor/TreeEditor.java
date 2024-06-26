
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


import org.mgnl.nicki.core.context.NickiContext;
import org.mgnl.nicki.core.data.DataProvider;
import org.mgnl.nicki.core.data.TreeData;
import org.mgnl.nicki.vaadin.base.application.NickiApplication;

import com.vaadin.flow.component.treegrid.TreeGrid;

@SuppressWarnings("serial")
public class TreeEditor extends NickiTreeEditor {
	private TreeSelector<TreeData> treeSelector = new TreeSelector<>();
	public TreeEditor(NickiApplication application, NickiContext ctx, DataProvider<TreeData> treeDataProvider, String messageKeyBase) {
		super(application, ctx);
		NickiTreeDataProvider nickiTreeDataProvider = new NickiTreeDataProvider(ctx, treeDataProvider);
		if (treeSelector.getComponent() instanceof TreeGrid) {
			((TreeGrid<TreeData>) treeSelector.getComponent()).setDataProvider(nickiTreeDataProvider);			
		} else {
			treeSelector.getComponent().setItems(nickiTreeDataProvider);
		}
		init(treeSelector, nickiTreeDataProvider, messageKeyBase);
	}
}
