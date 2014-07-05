package kz.zvezdochet.core.service;

public class DataAccessException extends Exception {
	private static final long serialVersionUID = -2165605561177390569L;

	private Exception exception;
	 
	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException(Exception exception) {
		exception = this.exception;
	}

	public void printStackTrace() {
		exception.printStackTrace();
	}
}
