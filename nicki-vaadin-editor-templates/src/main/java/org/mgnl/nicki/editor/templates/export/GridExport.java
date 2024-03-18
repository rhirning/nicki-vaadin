package org.mgnl.nicki.editor.templates.export;

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

import com.itextpdf.text.DocumentException;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.server.StreamResource;

import freemarker.template.TemplateException;
import lombok.Data;
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

	public GridExportColumn<T> addColumn(ValueProvider<T, String> valueProvider) {
		GridExportColumn<T> column = new GridExportColumn<T>(valueProvider);
		columns.add(column);
		return column;
	}
		
	public StreamResource getXlsStreamResource(String filename, Collection<T> items) {

		return new StreamResource(filename + "_" + DataHelper.getMilli(new Date()) + ".xlsx",
				() -> {
					try {
						return getXlsDoc(items);
					} catch (IOException | TemplateException
							| InvalidPrincipalException | ParserConfigurationException | SAXException
							| DocumentException e) {
						log.error("Error reading protocol");
						return null;
					}
				});
	}
	
	public InputStream getXlsDoc(Collection<T> items) throws IOException, TemplateException, InvalidPrincipalException, ParserConfigurationException, SAXException, DocumentException {
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put("columns", columns);
		dataModel.put("items", items);
		return XlsDocuHelper.generateXlsx(TYPE.CLASSPATH, "export/export", dataModel);
	}
	
	
}
