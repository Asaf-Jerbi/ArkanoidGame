package game.backgrounds;
import biuoop.DrawSurface;
import game.GameLevel;
import sprites.Sprite;
import java.awt.Color;
import java.util.List;


/**
 * The type Menu back ground.
 */
public class MenuBackGround implements Sprite {
    private String title;
    private List<String> keys;
    private List<String> messages;


    /**
     * Instantiates a new Menu back ground.
     *
     * @param title    the title
     * @param keys     the keys
     * @param messages the messages
     */
    public MenuBackGround(String title, List<String> keys, List<String> messages) {
        this.title = title;
        this.keys = keys;
        this.messages = messages;
    }

    /**
     * draw on.
     * @param d the d
     */
    public void drawOn(DrawSurface d) {
        d.setColor(new Color(255, 255, 255));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(new Color(53, 99, 225));
        d.drawText(100, 100, this.title, 80);
        int y = 300;
        for (int i = 0; i < this.keys.size(); i++) {
            d.drawText(100, y, "(" + this.keys.get(i) + ") " + this.messages.get(i), 30);
            y += 40;
        }

    }

    @Override
    public void timePassed() {
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

}

