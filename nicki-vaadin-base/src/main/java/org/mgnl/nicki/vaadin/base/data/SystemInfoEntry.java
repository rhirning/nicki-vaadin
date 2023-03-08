package org.mgnl.nicki.vaadin.base.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SystemInfoEntry {
	public enum TYPE {VERSION, SYSTEM_PROPERTY, SYSTEM_ENVIRONMENT, JNDI_ENVIRONMENT, JNDI_BINDING}
	private TYPE type;
	private String name;
	private String value;
}
