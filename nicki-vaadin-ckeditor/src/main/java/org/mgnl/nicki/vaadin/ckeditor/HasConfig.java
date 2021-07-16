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
import com.vaadin.flow.component.HasElement;
import elemental.json.Json;
import elemental.json.JsonObject;

public interface HasConfig extends HasElement {

    default void setConfig(Config config) {
        getElement().setPropertyJson("config", config!=null?config.getConfigJson():new Config().getConfigJson());
    }

    default Config getConfig() {
        String configJson = getElement().getProperty("config");
        JsonObject jsonObject = Json.parse(configJson);
        return new Config(jsonObject);
    }

}
