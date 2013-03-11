package helper;

import org.apache.commons.logging.LogFactory;

/**
 * Log Utilities.
 * 
 * This class is a wrapper for the underlying logging framework.
 *
 */
public class LogUtils {
	/**
     * <p> Is debug logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than debug. </p>
     * 
     * @param c - Class that logs this message.
     *
     * @return true if debug is enabled in the underlying logger.
     */
	public static boolean isDebugEnabled(Class c) {
		return LogFactory.getLog(c).isDebugEnabled();
	}
	
	/**
     * <p> Is info logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than info. </p>
     *
     * @return true if info is enabled in the underlying logger.
     */
	public static boolean isInfoEnabled(Class c) {
		return LogFactory.getLog(c).isInfoEnabled();
	}
	
	/**
     * <p> Log a message with debug log level. </p>
     *
     * @param c Class that logs this message.
     * @param message log this message
     */
	public static void debug(Class c, Object message) {
		LogFactory.getLog(c).debug(message);
	}
	
	/**
     * <p> Log a message with info log level. </p>
     *
     * @param c Class that logs this message.
     * @param message log this message
     */
	public static void info(Class c, Object message) {
		LogFactory.getLog(c).info(message);
	}
	
	/**
     * <p> Log a message with warn log level. </p>
     *
     * @param c Class that logs this message.
     * @param message log this message
     */
	public static void warn(Class c, Object message) {
		LogFactory.getLog(c).warn(message);
	}
	
	/**
     * <p> Log a message with error log level. </p>
     *
     * @param c Class that logs this message.
     * @param message log this message
     */
	public static void error(Class c, Object message) {
		LogFactory.getLog(c).error(message);
	}
	
	/**
     * <p> Log a message with debug log level. </p>
     *
     * @param c Class that logs this message.
     * @param message log this message
     * @param t log this cause
     */
	public static void debug(Class c, Object message, Throwable t) {
		LogFactory.getLog(c).debug(message, t);
	}

	/**
     * <p> Log a message with info log level. </p>
     *
     * @param c Class that logs this message.
     * @param message log this message
     * @param t log this cause
     */
	public static void info(Class c, Object message, Throwable t) {
		LogFactory.getLog(c).info(message, t);
	}
	
	/**
     * <p> Log a message with warn log level. </p>
     *
     * @param c Class that logs this message.
     * @param message log this message
     * @param t log this cause
     */
	public static void warn(Class c, Object message, Throwable t) {
		LogFactory.getLog(c).warn(message, t);
	}
	
	/**
     * <p> Log a message with error log level. </p>
     *
     * @param c Class that logs this message.
     * @param message log this message
     * @param t log this cause
     */
	public static void error(Class c, Object message, Throwable t) {
		LogFactory.getLog(c).error(message, t);
	}
	
	/**
     * <p> Is debug logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than debug. </p>
     * 
     * @param o - Object Instance that logs this message.
     *
     * @return true if debug is enabled in the underlying logger.
     */
	public static boolean isDebugEnabled(Object o) {
		return LogFactory.getLog(o.getClass()).isDebugEnabled();
	}
	
	/**
     * <p> Is info logging currently enabled? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than info. </p>
     * 
     * @param o - Object Instance that logs this message.
     *
     * @return true if info is enabled in the underlying logger.
     */
	public static boolean isInfoEnabled(Object o) {
		return LogFactory.getLog(o.getClass()).isInfoEnabled();
	}
	
	/**
     * <p> Log a message with debug log level. </p>
     *
     * @param o - Object Instance that logs this message.
     * @param message log this message
     */
	public static void debug(Object o, Object message) {
		LogFactory.getLog(o.getClass()).debug(message);
	}
	
	/**
     * <p> Log a message with info log level. </p>
     *
     * @param o - Object Instance that logs this message.
     * @param message log this message
     */
	public static void info(Object o, Object message) {
		LogFactory.getLog(o.getClass()).info(message);
	}
	
	/**
     * <p> Log a message with warn log level. </p>
     *
     * @param o - Object Instance that logs this message.
     * @param message log this message
     */
	public static void warn(Object o, Object message) {
		LogFactory.getLog(o.getClass()).warn(message);
	}
	
	/**
     * <p> Log a message with error log level. </p>
     *
     * @param o - Object Instance that logs this message.
     * @param message log this message
     */
	public static void error(Object o, Object message) {
		LogFactory.getLog(o.getClass()).error(message);
	}
	
	/**
     * <p> Log a message with debug log level. </p>
     *
     * @param o - Object Instance that logs this message.
     * @param message log this message
     * @param t log this cause
     */
	public static void debug(Object o, Object message, Throwable t) {
		LogFactory.getLog(o.getClass()).debug(message, t);
	}

	/**
     * <p> Log a message with info log level. </p>
     *
     * @param o - Object Instance that logs this message.
     * @param message log this message
     * @param t log this cause
     */
	public static void info(Object o, Object message, Throwable t) {
		LogFactory.getLog(o.getClass()).info(message, t);
	}
	
	/**
     * <p> Log a message with warn log level. </p>
     *
     * @param c Class that logs this message.
     * @param message log this message
     * @param t log this cause
     */
	public static void warn(Object o, Object message, Throwable t) {
		LogFactory.getLog(o.getClass()).warn(message, t);
	}
	
	/**
     * <p> Log a message with error log level. </p>
     *
     * @param c Class that logs this message.
     * @param message log this message
     * @param t log this cause
     */
	public static void error(Object o, Object message, Throwable t) {
		LogFactory.getLog(o.getClass()).error(message, t);
	}
}
