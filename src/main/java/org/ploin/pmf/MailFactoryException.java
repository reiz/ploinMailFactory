package org.ploin.pmf;

/**
 * Only exception that can be thrown by methods of the {@link IMailFactory} interface.
 */
public class MailFactoryException extends Exception {

	private static final long serialVersionUID = 7011758088416534502L;

	public MailFactoryException() {
		super();
	}
	
	public MailFactoryException(String message) {
		super(message);
	}
	
	public MailFactoryException(Throwable cause) {
		super(cause);
	}
	
	public MailFactoryException(String message, Throwable cause) {
		super(message, cause);
	}
}
