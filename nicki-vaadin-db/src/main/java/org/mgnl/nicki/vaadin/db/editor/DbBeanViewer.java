
package org.mgnl.nicki.vaadin.db.editor;

import java.lang.reflect.Field;

import org.mgnl.nicki.core.helper.DataHelper;

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



import org.mgnl.nicki.core.i18n.I18n;
import org.mgnl.nicki.core.util.Classes;
import org.mgnl.nicki.db.annotation.Attribute;
import org.mgnl.nicki.db.annotation.Table;
import org.mgnl.nicki.db.context.DBContext;
import org.mgnl.nicki.db.context.DBContextManager;
import org.mgnl.nicki.db.helper.BeanHelper;
import org.mgnl.nicki.db.verify.BeanUpdater;
import org.mgnl.nicki.db.verify.BeanVerifier;
import org.mgnl.nicki.db.verify.BeanVerifyError;
import org.mgnl.nicki.db.verify.UpdateBeanException;
import org.mgnl.nicki.vaadin.base.notification.Notification;
import org.mgnl.nicki.vaadin.base.notification.Notification.Type;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("serial")
public class DbBeanViewer extends VerticalLayout implements NewClassEditor, ClassEditor {

	private @Getter @Setter Object bean;
	private HorizontalLayout buttonsLayout;
	private Button saveButton;
	private Button closeButton;
	private @Getter boolean create;
	private @Getter DbBeanValueChangeListener listener;
	private @Getter @Setter String dbContextName;
	private String[] hiddenAttributes;
	private FormLayout formLayout;
	private @Getter @Setter boolean readOnly;

	public void setDbBean(Object bean, String...hiddenAttributes) {
		log.debug("Bean: " + bean);
		this.bean = bean;
		this.hiddenAttributes = hiddenAttributes;
		this.create = false;
		buildMainLayout();
	}

	@Override
	public void setDbBean(Object bean) {
		log.debug("Bean: " + bean);
		this.bean = bean;
		this.create = false;
		buildMainLayout();
	}
	
	public DbBeanViewer(DbBeanValueChangeListener listener) {
		this.listener = listener;
	}
	
	public void init(Class<?> classDefinition, Object... foreignObjects ) throws InstantiationException, IllegalAccessException {
		this.bean = Classes.newInstance(classDefinition);
		if (foreignObjects != null && foreignObjects.length > 0) {
			for (Object foreignObject : foreignObjects) {
				BeanHelper.addForeignKey(bean, foreignObject);
			}
		}
		this.create = true;
		buildMainLayout();
	}


	public void buildMainLayout() {
		
		setSizeUndefined();
		Label label = new Label(I18n.getText(bean.getClass().getName()));
		formLayout = new FormLayout();
		buttonsLayout = new HorizontalLayout();
		add(label, formLayout, buttonsLayout);
		DbBeanFieldFactory factory = new DbBeanFieldFactory(listener, dbContextName);
		factory.addFields(formLayout, bean, create, hiddenAttributes, isReadOnly());
		
		if (!isReadOnly()) {
			saveButton = new Button(I18n.getText("nicki.editor.generic.button.save"));
			saveButton.addClickListener(event -> save());
	
			buttonsLayout.add(saveButton);
		}
		if (listener != null) {
			closeButton = new Button(I18n.getText("nicki.editor.generic.button.close"));
			closeButton.addClickListener(event -> listener.close(null));
			buttonsLayout.add(closeButton);
		}
	}

	public void save() {
		if (!verifyMandatory()) {
			Notification.show("Bitte Pflichtfelder füllen", Type.ERROR_MESSAGE);
			return;
		}
		if (getBean().getClass().isAnnotationPresent(Table.class) && getBean().getClass().getAnnotation(Table.class).verifyClass() != void.class) {
			try {
				BeanVerifier verifier = (BeanVerifier) Classes.newInstance(getBean().getClass().getAnnotation(Table.class).verifyClass());
				verifier.verify(getBean());
			} catch (BeanVerifyError e1) {
				
				Notification.show(DataHelper.getAsString(e1.getErrors(), "\n"), Type.ERROR_MESSAGE);
				return;
			} catch (Exception e) {
				Notification.show("Invalid verifier for Class " + getBean().getClass(), Type.ERROR_MESSAGE);
				return;
			}
		}
		try {
			try (DBContext dbContext = DBContextManager.getContext(dbContextName)) {
				dbContext.beginTransaction();
				if (isCreate()) {
					setBean(dbContext.create(getBean()));
				} else if (getBean().getClass().isAnnotationPresent(Table.class) && getBean().getClass().getAnnotation(Table.class).updateClass() != void.class) {
					try {
						BeanUpdater updater = (BeanUpdater) Classes.newInstance(getBean().getClass().getAnnotation(Table.class).updateClass());
						updater.update(dbContext, getBean());
					} catch (UpdateBeanException e) {
						Notification.show("Error updating " + getBean().getClass() + ": " + e.getMessage(), Type.ERROR_MESSAGE);
						return;
					} catch (Exception e) {
						Notification.show("Invalid updater for Class " + getBean().getClass(), Type.ERROR_MESSAGE);
						return;
					}
				} else {
					setBean(dbContext.update(getBean()));
				}
				dbContext.commit();
				Notification.show(I18n.getText("nicki.editor.save.info"));
			}
			if (listener != null) {
				listener.close(this);
				listener.refresh(this.bean);
			}
		} catch (Exception e) {
			log.error("Error", e);
		}
	}

	public boolean verifyMandatory() {
		for (Field field : bean.getClass().getDeclaredFields()) {
			Attribute attribute = field.getAnnotation(Attribute.class);
			if (attribute != null && attribute.mandatory()) {
				Object value = BeanHelper.getValue(this.bean, field.getName());
				if (value == null) {
					return false;
				}
			}
		}
		return true;
	}
	
	public String getI18nBase() {
		return "nicki.vaadin.db.";
	}

}
