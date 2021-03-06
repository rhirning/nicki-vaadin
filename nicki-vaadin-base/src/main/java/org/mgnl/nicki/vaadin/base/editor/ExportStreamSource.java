
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


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;

import org.mgnl.nicki.core.context.NickiContext;
import org.mgnl.nicki.core.data.TreeData;
import org.mgnl.nicki.core.util.XMLBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExportStreamSource implements Serializable {

	private static final long serialVersionUID = -8068031351212191141L;

	public static InputStream getStream(TreeData dynamicObject, NickiContext context, boolean selfOnly) {
		try {
			XMLBuilder builder = new XMLBuilder(context, dynamicObject.getPath(), selfOnly);
			Writer writer = new StringWriter();
			builder.write(writer);
			
			return new ByteArrayInputStream(writer.toString().getBytes("UTF-8"));
		} catch (Exception e) {
			log.error("Error", e);
		}
		return null;
	}

}
