package org.ploin.pmf.impl;

import org.apache.commons.mail.EmailAttachment;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.ploin.pmf.entity.MailConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * $LastChangedBy: r.reiz $<br>
 * $Revision: 53 $<br>
 * $Date: 2009-11-22 20:37:52 +0100 (Sun, 22 Nov 2009) $<br>
 * <p/>
 * Created by: robert
 * Created date: Nov 18, 2009 - 8:42:44 PM
 * <p/>
 * Description:
 */
public class MailConfigTest {

	@Test
	public void setAndGetAttachements(){
		MailConfig mailConfig = new MailConfig();
		List<EmailAttachment> list = new ArrayList<EmailAttachment>();
		list.add(new EmailAttachment());
		mailConfig.setAttachements(list);
		assertEquals(1, mailConfig.getAttachements().size());
		mailConfig.addAttachement(new EmailAttachment());
		assertEquals(2, mailConfig.getAttachements().size());
	}

	@Test
	public void addAttachements(){
		MailConfig mailConfig = new MailConfig();
		mailConfig.addAttachement(new EmailAttachment());
		assertEquals(1, mailConfig.getAttachements().size());
		mailConfig.addAttachement(new EmailAttachment());
		assertEquals(2, mailConfig.getAttachements().size());
	}
}
