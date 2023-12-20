package org.mgnl.nicki.vaadin.ckeditor.mention;

/*-
 * #%L
 * nicki-vaadin-ckeditor
 * %%
 * Copyright (C) 2020 - 2023 Ralf Hirning
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

import java.util.List;

/**
 * @author Ryan Pang (ryan.pang@wontlost.com)
 */
public class MentionFeed {

    List<MentionFeedItem> feed;

    String marker;

    Integer minimumCharacters;

    public List<MentionFeedItem> getFeed() {
        return feed;
    }

    public void setFeed(List<MentionFeedItem> feed) {
        this.feed = feed;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public Integer getMinimumCharacters() {
        return minimumCharacters;
    }

    public void setMinimumCharacters(Integer minimumCharacters) {
        this.minimumCharacters = minimumCharacters;
    }

}
