package interfaces;

/**
 * a Task interface.
 *
 * @param <T>
 */
public interface Task<T> {
    /**
     * run.
     *
     * @return a value.
     */
    T run();
}
