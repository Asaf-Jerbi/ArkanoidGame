package readers;

import biuoop.KeyboardSensor;
import game.GameFlow;
import game.LevelInformation;
import game.animations.AnimationRunner;

import game.data.scores.HighScoresTable;
import gameutils.MenuAnimation;
import tasks.PlayGameTask;


import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.List;


/**
 * The type Level sets reader.
 */
public class LevelSetsReader {

    //fields

    private File highScores;
    private String fileName;
    private HighScoresTable table;
    private String title;
    private GameFlow gameFlow;
    private AnimationRunner ar;
    private KeyboardSensor ks;

    /**
     * Instantiates a new Level sets reader.
     *
     * @param fileName   the file name
     * @param title      the title
     * @param ks         the ks
     * @param ar         the ar
     * @param gameFlow   the game flow
     * @param table      the table
     * @param highscores the highscores
     */
//Constructor
    public LevelSetsReader(String fileName, String title, KeyboardSensor ks, AnimationRunner ar,
                           GameFlow gameFlow, HighScoresTable table, File highscores) {
        this.fileName = fileName;
        this.title = title;
        this.ks = ks;
        this.ar = ar;
        this.gameFlow = gameFlow;
        this.table = table;
        this.highScores = highscores;
    }


    /**
     * Create sub menu from file menu animation.
     *
     * @return the menu animation
     */
    public MenuAnimation createSubMenuFromFile() {

        //creating sub-menu
        MenuAnimation subMenu = new MenuAnimation(this.title, this.ks, this.ar);

        //saving the input stream (path to source file):
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(this.fileName);

        //creating reader
        Reader levelInfoReader = new InputStreamReader(is);
        try {
            LineNumberReader reader1 = new LineNumberReader(levelInfoReader);
            String line = reader1.readLine();
            while (line != null) {

                if ((!line.isEmpty()) && (!line.startsWith("#"))) {

                    String[] levSymbolAndDecs = line.split(":");

                    line = reader1.readLine();

                    List<LevelInformation> levelInformationList;

                    LevelSpecificationReader levelReader = new LevelSpecificationReader();

                    InputStream anotherReader = ClassLoader.getSystemClassLoader().getResourceAsStream(line);

                    Reader levelSetsInfoReader = new InputStreamReader(anotherReader);

                    levelInformationList = levelReader.fromReader(levelSetsInfoReader);

                    subMenu.addSelection(levSymbolAndDecs[0], levSymbolAndDecs[1], new PlayGameTask(
                            ar, gameFlow, levelInformationList, table, highScores));

                }
                line = reader1.readLine();
            }
            subMenu.setAnimationRunner(ar);
            return subMenu;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
