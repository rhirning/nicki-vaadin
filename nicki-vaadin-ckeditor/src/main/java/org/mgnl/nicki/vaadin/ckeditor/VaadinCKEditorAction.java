package org.mgnl.nicki.vaadin.ckeditor;
/*-
 * #%L
 * nicki-vaadin-ckeditor
 * %%
 * Copyright (C) 2020 - 2021 Ralf Hirning
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
