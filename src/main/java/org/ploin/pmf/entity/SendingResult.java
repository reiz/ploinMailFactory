package org.ploin.pmf.entity;

import javax.mail.internet.MimeMessage;
import java.io.Serializable;

/**
 * $LastChangedBy: r.reiz $<br>
 * $Revision: 77 $<br>
 * $Date: 2010-03-15 13:13:45 +0100 (Mon, 15 Mar 2010) $<br>
 * <p/>
 * Created by: robert
 * Created date: Nov 14, 2009 - 9:40:25 PM
 */
public class SendingResult implements Serializable {

	private static final long serialVersionUID = 6940835531651943282L;
	private String result;
	private MimeMessage mimeMessage;

	@Override
	public String toString() {
		return result;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public MimeMessage getMimeMessage() {
		return mimeMessage;
	}

	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}
}