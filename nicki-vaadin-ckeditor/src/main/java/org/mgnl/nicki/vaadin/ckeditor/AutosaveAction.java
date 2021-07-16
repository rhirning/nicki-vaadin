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
import java.util.function.BiConsumer;

public abstract class AutosaveAction implements BiConsumer<String, String>, IsAction{
    /**
     * Customise your own autosave action on ckeditor
     * @param editorId Editor id, there is a default id if not set.
     * @param editorData Data need to be saved.
     */
    public abstract void accept(String editorId, String editorData);

}