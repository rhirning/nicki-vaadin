
package org.mgnl.nicki.editor.log4j2;

import java.io.IOException;
import java.net.URI;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;

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


import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.mgnl.nicki.vaadin.base.application.NickiApplication;
import org.mgnl.nicki.vaadin.base.menu.application.View;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextArea;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Log4jConfigViewer extends Div implements View {
	private static final long serialVersionUID = 5134572850949470423L;
	private TextArea textArea;
	private NickiApplication application;
	private boolean isInit;
	

    /**
     * The root appender.
     */
    public static final String ROOT = "Root";
    
    public static void main(String...args) {
    	new Log4jConfigViewer().refresh();
    }
    
	public Log4jConfigViewer() {
	}

    
	public Log4jConfigViewer(NickiApplication application) {
		this.application = application;
	}

	public String getI18nBase() {
		return this.application.getI18nBase();
	}
	

    private void refresh() {
    	log.debug("start refresh()");

    	ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
    	
    	Configuration configuration = ConfigurationFactory.getInstance().getConfiguration(null, null, URI.create("classpath:/log4j2.xml"));
    	configuration.initialize();
    	
    	for (String loggerName : configuration.getLoggers().keySet()) {
    		LoggerConfig loggerConfig = configuration.getLoggers().get(loggerName);
    		Level level = loggerConfig.getLevel();
    		if (StringUtils.isBlank(loggerName)) {
    	    	RootLoggerComponentBuilder rootLoggerBuilder 
    	    	  = builder.newRootLogger(level);
    		} else {
        		LoggerComponentBuilder loggerbuilder = builder.newLogger(loggerName, level);
        		loggerbuilder.addAttribute("additivity", false);

        		builder.add(loggerbuilder);
    		}
//    		System.out.println(loggerName + ": " + loggerConfig.getLevel());
    	}
		LoggerComponentBuilder loggerbuilder = builder.newLogger("org.mgnl.nicki.editor.log4j2", Level.DEBUG);
		loggerbuilder.addAttribute("additivity", false);

		builder.add(loggerbuilder);
    	
    	try {

			builder.writeXmlConfiguration(System.out);		

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	Configurator.initialize(builder.build());
    	

    	log.debug("end refresh()");
	}
	

    private void refresh2() {
    	log.debug("fill Table()");
    	
    	ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
    	
    	Logger rootLogger = LogManager.getRootLogger();

    	RootLoggerComponentBuilder rootLoggerBuilder 
    	  = builder.newRootLogger(rootLogger.getLevel());
    	
    	builder.add(rootLoggerBuilder);
    	for (Logger logger : LogManager.getContext().getLoggerRegistry().getLoggers()) {
    		LoggerComponentBuilder loggerbuilder = builder.newLogger(logger.getName(), logger.getLevel());
    		loggerbuilder.addAttribute("additivity", false);

    		builder.add(loggerbuilder);
    	}
    	try {
			builder.writeXmlConfiguration(System.out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

    public void fillTable() {
    	refresh();
	}

	@Override
	public void init() {
		if (!isInit) {
			setSizeFull();
			textArea = new TextArea();
			add(textArea);
			isInit = true;
		}
		refresh();
		
	}

	@Override
	public boolean isModified() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setApplication(NickiApplication application) {
		this.application = application;
	}

}
