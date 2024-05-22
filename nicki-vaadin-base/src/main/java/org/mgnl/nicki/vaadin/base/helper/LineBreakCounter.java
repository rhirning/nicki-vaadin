package org.mgnl.nicki.vaadin.base.helper;

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
