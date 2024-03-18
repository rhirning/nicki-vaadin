package org.mgnl.nicki.editor.templates.export;

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
