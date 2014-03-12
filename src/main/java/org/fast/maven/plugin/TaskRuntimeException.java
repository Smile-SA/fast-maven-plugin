package org.fast.maven.plugin;

/**
 * Exception
 */
public class TaskRuntimeException extends RuntimeException {
    /**
     *
     * @param cause cause
     */
    public TaskRuntimeException(Exception cause){
        super(cause);
    }
}
