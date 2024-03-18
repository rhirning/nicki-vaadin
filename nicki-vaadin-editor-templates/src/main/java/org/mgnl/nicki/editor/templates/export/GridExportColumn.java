package org.mgnl.nicki.editor.templates.export;

import com.vaadin.flow.function.ValueProvider;

import lombok.Data;

@Data
public class GridExportColumn<T> {

	private ValueProvider<T, String> valueProvider;
	private String header;
	public GridExportColumn(ValueProvider<T, String> valueProvider) {
		this.valueProvider = valueProvider;
	}
	
	public GridExportColumn<T> setHeader(String header) {
		this.header = header;
		return this;
	}
	
	public String get(T item) {
		return valueProvider.apply(item);
	}

	public GridExportColumn<T> setSortable(boolean b) {
		return this;
	}

}
