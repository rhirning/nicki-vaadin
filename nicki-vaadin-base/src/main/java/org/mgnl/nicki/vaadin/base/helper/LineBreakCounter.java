package org.mgnl.nicki.vaadin.base.helper;

/*-
 * #%L
 * nicki-vaadin-base
 * %%
 * Copyright (C) 2020 - 2024 Ralf Hirning
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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.vaadin.flow.component.upload.Receiver;

import lombok.Getter;

public class LineBreakCounter implements Receiver {
	private static final long serialVersionUID = -3996405615850977850L;
	private @Getter int total;
    private boolean sleep;
    private @Getter ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    private @Getter String filename;

    /**
     * return an OutputStream that simply counts lineends
     */
    @Override
    public OutputStream receiveUpload(final String filename, final String MIMEType) {
    	this.filename = filename;
        total = 0;
        return new OutputStream() {

            @Override
            public void write(final int b) {
            	byteOut.write(b);
                total++;
                if (sleep && total % 1000 == 0) {
                    try {
                        Thread.sleep(100);
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    public void setSlow(boolean value) {
        sleep = value;
    }
}
