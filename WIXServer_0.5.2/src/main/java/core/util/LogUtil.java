package core.util;

import org.apache.log4j.Logger;

public class LogUtil {

	private Logger logger;

	public LogUtil(Class<?> clazz) {
        if ( logger == null ) {
		    logger = Logger.getLogger(clazz);
        }
	}

	public void info(String message) {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		logger.info("\u001b[33m INFO  [" + ste.getClassName() + ":" + ste.getLineNumber() + "] - " + message + "\u001b[00m");
	}

	public void debug(String message) {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		logger.debug("\u001b[32m DEBUG [" + ste.getClassName() + ":" + ste.getLineNumber() + "] - " + message + "\u001b[00m");
	}

	public void error(String message) {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		logger.error("\u001b[31m ERROR [" + ste.getClassName() + ":" + ste.getLineNumber() + "] - " + message + "\u001b[00m");
	}

	public void fatal(String message) {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		logger.fatal("\u001b[31m FATAL [" + ste.getClassName() + ":" + ste.getLineNumber() + "] - " + message + "\u001b[00m");
	}

	public void warn(String message) {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		logger.warn("\u001b[34m WARN  [" + ste.getClassName() + ":" + ste.getLineNumber() + "] - " + message + "\u001b[00m");
	}

}
