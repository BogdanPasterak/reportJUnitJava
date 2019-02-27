package reportJUnitJava;

public class ReportJUnitException extends Exception {

	private static final long serialVersionUID = 1L;

	public ReportJUnitException() {
		super();
	}

	public ReportJUnitException(String message) {
		super(message);
	}

	public ReportJUnitException(Throwable cause) {
		super(cause);
	}

	public ReportJUnitException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReportJUnitException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
