package org.ploin.pmf.impl;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.ploin.pmf.entity.Recipient;

/**
 * $LastChangedBy: r.reiz $<br>
 * $Revision: 53 $<br>
 * $Date: 2009-11-22 20:37:52 +0100 (Sun, 22 Nov 2009) $<br>
 * <p/>
 * Created by: robert
 * Created date: Nov 18, 2009 - 8:37:44 PM
 * <p/>
 * Description:
 */
public class RecipientTest {

	@Test
	public void toString1(){
		Recipient recipient = new Recipient();
		recipient.setName("Robert Reiz");
		recipient.setEmail("reiz@ploin.de");
		assertEquals("<Robert Reiz>reiz@ploin.de", recipient.toString());
	}

}
