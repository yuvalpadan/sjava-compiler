package oop.ex6.main;

/**
 * this class is an abstract class represent an exception in sJava file.
 * this class will create an error message if the s-java file code is not valid.
 */
public abstract class SJavaException extends Exception {

	/**
	 * constructs a new s-java exception object
	 * @param message the message of the exception.
	 */
	public SJavaException(String message) {
		super(message);
	}

	/**
	 * this class represent a syntax exception in s-java file.
	 */
	public static class SyntaxException extends SJavaException {

		/**
		 * Construct a new SyntaxException
		 * @param message the message of the exception.
		 */
		public SyntaxException(String message) {
			super(message);
		}
	}

	/**
	 * this class represents an exception related to methods.
	 */
	public static class MethodException extends SJavaException {

		/**
		 * constructs a new method exception object
		 * @param message the message of the exception.
		 */
		public MethodException(String message) {
			super(message);
		}
	}

	/**
	 * This class represents an exception related to variable.
	 */
	public static class VariableException extends SJavaException {

		/**
		 * constructs a new variable exception object
		 * @param message the message of the exception.
		 */
		public VariableException(String message) {
			super(message);
		}
	}
}
