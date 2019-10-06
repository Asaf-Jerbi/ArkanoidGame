package interfaces;

import game.animations.Animation;

/**
 * a Menu interface.
 *
 * @param <T>
 */
public interface Menu<T> extends Animation {
    /**
     * addSelection.
     *
     * @param key       the key to wait for.
     * @param message   the line to print.
     * @param returnVal what to return.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * getStatus.
     *
     * @return returnVal.
     */
    T getStatus();

    /**
     * @param key     the key to wait for.
     * @param message the line to print.
     * @param subMenu the sub menu to add.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}
