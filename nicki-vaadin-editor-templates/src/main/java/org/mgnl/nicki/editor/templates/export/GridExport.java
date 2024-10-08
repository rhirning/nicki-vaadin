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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.mgnl.nicki.core.auth.InvalidPrincipalException;
import org.mgnl.nicki.core.helper.DataHelper;
import org.mgnl.nicki.template.engine.ConfigurationFactory.TYPE;
import org.mgnl.nicki.template.report.helper.XlsDocuHelper;
import org.xml.sax.SAXException;

import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.server.StreamResource;

import freemarker.template.TemplateException;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @apiNote
 * <pre>
 *	List<MyBean> data = loadData();
 *	GridExport gridExport = new GridExport<MyBean>();
 *	Button exportButton = new Button("Export");
 *	exportButton.addClickListener(e -> {
 *		Downloader.showDownload("Export",
 *		gridExport.getXlsStreamResource("Dateiname", data));
 *	});
 *
 *	gridExport.addColumn(entry -> entry.getUserId()).setHeader("User ID");
 *	gridExport.addColumn(entry -> entry.getSn()).setHeader("Nachname");
 *
  * </pre>
 *
 *
 *
 * @param <T> Beanklasse des Grids
 */
@Slf4j
@Data
public class GridExport<T> {
	private List<GridExportColumn<T>> columns = new ArrayList<GridExportColumn<T>>();
	private String name;
	private @Setter String templatePath = "export/export";

	public GridExportColumn<T> addColumn(ValueProvider<T, ?> valueProvider) {
		GridExportColumn<T> column = new GridExportColumn<T>(valueProvider);
		columns.add(column);
		return column;
	}

	public GridExportColumn<T> addColumn(GridExportColumn<T> column) {
		columns.add(column);
		return column;
	}
		
	public StreamResource getXlsStreamResource(String filename, Collection<T> items) {

		return new StreamResource(filename + "_" + DataHelper.getMilli(new Date()) + ".xlsx",
				() -> {
					try {
						return getXlsDoc(items);
					} catch (IOException | TemplateException
							| InvalidPrincipalException | ParserConfigurationException | SAXException e) {
						log.error("Error reading protocol");
						return null;
					}
				});
	}
	
	public InputStream getXlsDoc(Collection<T> items) throws IOException, TemplateException, InvalidPrincipalException, ParserConfigurationException, SAXException {
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put("columns", columns);
		dataModel.put("items", items);
		return XlsDocuHelper.generateXlsx(TYPE.CLASSPATH, templatePath, dataModel);
	}
	
	
}
