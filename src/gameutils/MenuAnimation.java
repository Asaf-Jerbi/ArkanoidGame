package gameutils;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.animations.AnimationRunner;
import game.backgrounds.MenuBackGround;
import interfaces.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Menu animation.
 *
 * @param <T> the type parameter
 */
public class MenuAnimation<T> implements Menu<T> {
    private List<String> keys;
    private List<String> messages;
    private T status;
    private Map<String, T> keyStatusMap;
    private String menuTitle;
    private AnimationRunner animationRunner = null;
    private KeyboardSensor ks;
    private boolean stop;

    /**
     * Instantiates a new Menu animation.
     *
     * @param menuTitle the menu title
     * @param keyboard  the keyboard
     * @param ar        the ar
     */
    public MenuAnimation(String menuTitle, KeyboardSensor keyboard, AnimationRunner ar) {
        this.keys = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.keyStatusMap = new TreeMap<>();
        this.menuTitle = menuTitle;
        this.animationRunner = ar;
        this.ks = keyboard;
    }

    /**
     * add selection.
     *
     * @param key       the key to wait for.
     * @param message   the line to print.
     * @param returnVal what to return.
     */
    public void addSelection(String key, String message, T returnVal) {
        this.keys.add(key);
        this.messages.add(message);
        this.keyStatusMap.put(key, returnVal);
    }

    /**
     * get status.
     *
     * @return T
     */
    public T getStatus() {
        return this.status;
    }

    /**
     * do one frame.
     *
     * @param d the d
     */
    public void doOneFrame(DrawSurface d) {
        MenuBackGround menuBackGround = new MenuBackGround(this.menuTitle, this.keys, this.messages);
        menuBackGround.drawOn(d);
        for (String key : this.keyStatusMap.keySet()) {
            if (this.ks.isPressed(key)) {
                this.status = this.keyStatusMap.get(key);
                this.stop = true;
                break;
            }
        }
    }

    /**
     * should stop.
     *
     * @return bool
     */
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     * Reset.
     */
    public void reset() {
        this.stop = false;
    }

    /**
     * add the sub menu.
     * @param key     the key to wait for.
     * @param message the line to print.
     * @param secondMenu the interior menu.
     */
    public void addSubMenu(String key, String message, Menu<T> secondMenu) {
        SecondMenuTask secondMenuTask = new SecondMenuTask<>(this.animationRunner, secondMenu);
        addSelection(key, message, (T) secondMenuTask);
    }

    /**
     * Sets animation runner.
     *
     * @param ar the ar
     */
    public void setAnimationRunner(AnimationRunner ar) {
        this.animationRunner = ar;
    }
}
