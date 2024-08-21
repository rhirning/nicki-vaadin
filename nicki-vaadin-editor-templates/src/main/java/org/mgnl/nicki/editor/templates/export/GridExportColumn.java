package org.mgnl.nicki.editor.templates.export;

/*-
 * #%L
 * nicki-vaadin-editor-templates
 * %%
 * Copyright (C) 2020 - 2024 Ralf Hirning
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

import com.vaadin.flow.function.ValueProvider;

import lombok.Data;

@Data
public class GridExportColumn<T> {

	private ValueProvider<T,?> valueProvider;
	private String header;
	public GridExportColumn(ValueProvider<T, ?> valueProvider) {
		this.valueProvider = valueProvider;
	}
	
	public GridExportColumn<T> setHeader(String header) {
		this.header = header;
		return this;
	}
	
	public Object get(T item) {
		return valueProvider.apply(item);
	}

	public GridExportColumn<T> setSortable(boolean b) {
		return this;
	}

	public GridExportColumn<T> setFlexGrow(int i) {
		return this;
	}

	public GridExportColumn<T> setWidth(String string) {
		return this;
	}

}
