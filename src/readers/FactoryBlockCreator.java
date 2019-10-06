package readers;

import geometry.Point;
import geometry.Rectangle;
import interfaces.BackGround;
import interfaces.BlockCreator;
import objects.BackGroundParser;
import sprites.Block;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * a FactoryBlockCreator class.
 */
public class FactoryBlockCreator implements BlockCreator {
    private int width;
    private int height;
    private int hitPoint;
    private Map<String, String> fillStartsMap;

    /**
     * FactoryBlockCreator - constructor.
     *
     * @param width         the block width.
     * @param height        the block height.
     * @param fillStartsMap Strings that start with "fill" and their  map values.
     * @param hitPoint      the block hit point.
     */
    public FactoryBlockCreator(int width, int height, Map<String, String> fillStartsMap, int hitPoint) {
        this.width = width;
        this.height = height;
        this.hitPoint = hitPoint;
        this.fillStartsMap = fillStartsMap;
    }


    /**
     * create.
     *
     * @param xPosition the x position
     * @param yPosition the y position
     * @return block
     */
    public Block create(int xPosition, int yPosition) {

        int size = fillStartsMap.keySet().size();
        String[] fillStarts = new String[fillStartsMap.keySet().size()];
        Map<Integer, BackGround> backGrounds = new TreeMap<>();


        int i = 0;
        while (i < size) {
            fillStarts[i] = (String) fillStartsMap.keySet().toArray()[i];
            i++;
        }

        for (String fillStart : fillStarts) {
            BackGroundParser backGroundParser = new BackGroundParser();
            BackGround bg = backGroundParser.backGroundFromString(fillStartsMap.get(fillStart));
            int hp;
            if (fillStart.length() > "fill".length()) {
                hp = Integer.parseInt(fillStart.substring("fill-".length()));
            } else {
                hp = 1;
            }
            backGrounds.put(hp, bg);
        }

        Block block = new Block(xPosition, yPosition, this.width, this.height);
        block.setHittPoint(this.hitPoint);

        for (Iterator<BackGround> iterator = backGrounds.values().iterator(); iterator.hasNext();) {
            BackGround value = iterator.next();
            Rectangle rectangle = new Rectangle(new Point(xPosition, yPosition), this.width, this.height);
            value.setRec(rectangle);
        }

        block.addBackground(backGrounds);
        return block;
    }
}
