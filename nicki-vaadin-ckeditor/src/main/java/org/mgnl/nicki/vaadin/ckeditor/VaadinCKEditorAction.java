package org.mgnl.nicki.vaadin.ckeditor;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VaadinCKEditorAction {

    public static String AUTOSAVE="autosave";

    private static final Map<String, IsAction> actionRegister = new HashMap<>();

    private static final Logger vaddinCKEditorActionLog = Logger.getLogger(VaadinCKEditorAction.class.getName());

    /**
     * Should be used with method setAutosave. This is a default action.
     */
    private static final AutosaveAction autosaveAction = new AutosaveAction() {
        public void accept(String editorId, String editorData) {
            vaddinCKEditorActionLog.log(Level.WARNING, "Saved for ["+editorId+"]," +
                    " This is a sample of autosave action. You need to fulfill your own action by " +
                    "extending AutosaveAction. And then register it by " +
                    "registerAction(VaadinCKEditorAction.AUTOSAVE, AutosaveAtion).");
        }
    };

    public static void registerAction(String name, IsAction action){
        actionRegister.put(name, action);
    }

    static Map<String, IsAction> getActionRegister() {
        actionRegister.putIfAbsent(AUTOSAVE, autosaveAction);
        return actionRegister;
    }

}
