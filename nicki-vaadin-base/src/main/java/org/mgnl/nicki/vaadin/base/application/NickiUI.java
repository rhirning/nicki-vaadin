package org.mgnl.nicki.vaadin.base.application;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.mgnl.nicki.core.context.AppContext;

import com.vaadin.flow.server.WrappedHttpSession;
import com.vaadin.mpr.MprUI;
import com.vaadin.server.VaadinRequest;

@SuppressWarnings("serial")
public class NickiUI extends MprUI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        super.init(vaadinRequest);
		AppContext.setRequest(vaadinRequest);
		WrappedHttpSession wrappedSession = (WrappedHttpSession) vaadinRequest.getWrappedSession();
		HttpSession httpSession = wrappedSession.getHttpSession();
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) httpSession.getAttribute(NickiServlet.NICKI_PARAMETERS);
		AppContext.setRequestParameters(map);

		for (String paramName : map.keySet()) {
			System.out.println(paramName + "=" + map.get(paramName));
		}
    }

}
