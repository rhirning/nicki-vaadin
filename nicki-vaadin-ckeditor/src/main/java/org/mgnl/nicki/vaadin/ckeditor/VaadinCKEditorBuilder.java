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
import java.util.function.Consumer;

import org.mgnl.nicki.vaadin.ckeditor.Constants.*;

public class VaadinCKEditorBuilder {

    public String editorId;
    public String editorData;
    public String width;
    public String height;
    public String margin;
    public Config config;
    public ThemeType theme;
    public Boolean readOnly = false;
    public Boolean autosave = false;

    public EditorType editorType = EditorType.CLASSIC;

    public VaadinCKEditorBuilder with(Consumer<VaadinCKEditorBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }

    public VaadinCKEditor createVaadinCKEditor() {
        VaadinCKEditor editor = new VaadinCKEditor(editorData);
        editor.setId(editorId);
        editor.setWidth(width);
        editor.setHeight(height);
        editor.setEditorMargin(margin);
        editor.setEditorTheme(theme);
        editor.setConfig(config);
        editor.setReadOnly(readOnly);
        editor.setAutosave(autosave);
        editor.setEditorType(editorType);
        return editor;
    }

}
