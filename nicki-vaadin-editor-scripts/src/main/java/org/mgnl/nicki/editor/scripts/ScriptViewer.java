
package org.mgnl.nicki.editor.scripts;

/*-
 * #%L
 * nicki-editor-scripts
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



import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.function.Consumer;

import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.mgnl.nicki.core.data.TreeData;
import org.mgnl.nicki.core.objects.DynamicObjectException;
import org.mgnl.nicki.core.util.ProtocolEntry;
import org.mgnl.nicki.dynamic.objects.objects.Script;
import org.mgnl.nicki.vaadin.base.editor.ClassEditor;
import org.mgnl.nicki.vaadin.base.editor.NickiTreeEditor;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

import bsh.EvalError;
import bsh.Interpreter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("serial")
public class ScriptViewer extends VerticalLayout implements ClassEditor {
	
	private VerticalLayout resultLayout;

	
	private HorizontalLayout horizontalLayout_1;

	
	private Label resultObject;

	
	private Button saveButton;

	
	private Button executeButton;

	
	private TextArea editor;

	private Script script;
	
	private Object request;


	private boolean isInit;
	
	private @Setter Consumer<ProtocolEntry> protocol;

	public ScriptViewer(Object request) {
		this.request = request;
	}

	public void setDynamicObject(NickiTreeEditor nickiEditor,
			TreeData dynamicObject) {
		this.script = (Script) dynamicObject;
		init();
		editor.setValue(StringUtils.trimToEmpty(script.getData()));

	}

	private void init() {
		if (!isInit) {
			buildMainLayout();

			executeButton.addClickListener(event -> {
					try {
						evaluate();
					} catch (IOException e) {
						log.error("Error", e);
					}
			});

			saveButton.addClickListener(event -> {
					try {
						save();
					} catch (Exception e) {
						log.error("Error", e);
					}
			});
			
			isInit = true;
		}
		
	}

	public void save() throws DynamicObjectException, NamingException {
		script.setData((String) editor.getValue());
		script.update("data");
	}

	protected void evaluate() throws IOException {
		resultLayout.removeAll();
		resultObject.setText("");
		String script = (String) editor.getValue();
		if (protocol != null) {
			protocol.accept(new ProtocolEntry("executeScript", null, "script", script));
		}
		try {
			StringBuilder scriptOutput = new StringBuilder();
			Object resultObj = evalScript(script, scriptOutput, false);
			setResult(scriptOutput.toString());
			if (resultObj != null) {
				resultObject.setText(resultObj.toString());
			} else {
				resultObject.setText(null);
			}
		} catch (Exception e) {
			if (e instanceof EvalError) {
				String errString = "ERROR:\n";
				errString += e.getMessage();
				try {
					int lineNo = ((EvalError) e).getErrorLineNumber();
					int contextLines = 4;
					if (lineNo > -1) {
						errString += "\n------------------------------------------------------\n"
								+ showScriptContext(script, lineNo,
										contextLines);
					}
				} catch (Exception e1) {
					log.error("Error", e1);
				}
				setResult(errString);
			} else {
				log.error("Error", e);
			}
		}
	}

	String showScriptContext(String s, int lineNo, int context) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new StringReader(s));

		int beginLine = Math.max(1, lineNo - context);
		int endLine = lineNo + context;
		for (int i = 1; i <= lineNo + context + 1; i++) {
			if (i < beginLine) {
				br.readLine();
				continue;
			}
			if (i > endLine)
				break;

			String line;
			line = br.readLine();

			if (line == null)
				break;
			if (i == lineNo)
				sb.append(i + ": --> " + line + "\n");
			else
				sb.append(i + ":     " + line + "\n");
		}

		return sb.toString();
	}

	private void setResult(String scriptOutput) throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader(
				scriptOutput));
		String line;
		while ((line = reader.readLine()) != null) {
			Label resultLine = new Label(line);
			resultLayout.add(resultLine);
		}

	}

	private Object evalScript(String script, StringBuilder scriptOutput,
			boolean captureOutErr) throws EvalError {
		// Create a PrintStream to capture output
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream pout = new PrintStream(baos);

		// Create an interpreter instance with a null inputstream,
		// the capture out/err stream, non-interactive
		Interpreter bsh = new Interpreter(null, pout, pout, false);
		bsh.set("bsh.httpServletRequest", this.request);

		// Eval the text, gathering the return value or any error.
		Object result = null;
		PrintStream sout = System.out;
		PrintStream serr = System.err;
		if (captureOutErr) {
			System.setOut(pout);
			System.setErr(pout);
		}
		try {
			// Eval the user text
			result = bsh.eval(script);
		} finally {
			if (captureOutErr) {
				System.setOut(sout);
				System.setErr(serr);
			}
		}
		pout.flush();
		scriptOutput.append(baos.toString());
		return result;
	}

	
	private void buildMainLayout() {
		setWidth("100%");
		setHeight("100%");
		setMargin(true);
		setSpacing(true);
		
		// editor
		editor = new TextArea();
		editor.setWidth("100.0%");
		editor.setHeight("300px");
		add(editor);
		
		// horizontalLayout_1
		horizontalLayout_1 = buildHorizontalLayout_1();
		add(horizontalLayout_1);
		
		// result
		resultLayout = buildResult();
		add(resultLayout);
		setFlexGrow(1, resultLayout);
	}

	
	private HorizontalLayout buildHorizontalLayout_1() {
		// common part: create layout
		horizontalLayout_1 = new HorizontalLayout();
		horizontalLayout_1.setWidth("-1px");
		horizontalLayout_1.setHeight("-1px");
		horizontalLayout_1.setMargin(false);
		horizontalLayout_1.setSpacing(true);
		
		// executeButton
		executeButton = new Button();
		executeButton.setText("Execute");
		executeButton.setWidth("-1px");
		executeButton.setHeight("-1px");
		horizontalLayout_1.add(executeButton);
		
		// saveButton
		saveButton = new Button();
		saveButton.setText("Save");
		saveButton.setWidth("-1px");
		saveButton.setHeight("-1px");
		horizontalLayout_1.add(saveButton);
		
		// resultObject
		resultObject = new Label();
		resultObject.setWidth("100.0%");
		resultObject.setHeight("-1px");
		resultObject.setText("");
		horizontalLayout_1.add(resultObject);
		
		return horizontalLayout_1;
	}

	
	private VerticalLayout buildResult() {
		
		// resultLayout
		resultLayout = new VerticalLayout();
		resultLayout.setWidth("100.0%");
		resultLayout.setHeight("-1px");
		resultLayout.setMargin(true);
		
		return resultLayout;
	}

}
