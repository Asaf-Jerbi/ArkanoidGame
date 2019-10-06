package tasks;


import game.GameFlow;
import game.LevelInformation;
import game.animations.AnimationRunner;
import game.data.scores.HighScoresTable;
//import gameutils.AnimationRunner;
//import gameutils.GameFlow;
//import interfaces.LevelInformation;
import interfaces.Task;
//import scoreuses.HighScoresTable;

import java.io.File;
import java.util.List;

/**
 * aa PlayGameTask class.
 */
public class PlayGameTask implements Task<Void> {
    //private AnimationRunner runner;
    private GameFlow gameFlow;
    private List<LevelInformation> levelInformationList;
    private HighScoresTable table;
    private File file;

    /**
     * PlayGameTask.
     *
     * @param runner               - the AnimationRunner.
     * @param gameFlow             - the gameFlow class that starts the game.
     * @param levelInformationList - the level list.
     * @param table                - HighScoresTable.
     * @param file                 - File.
     */
    public PlayGameTask(AnimationRunner runner, GameFlow gameFlow,
                        List<LevelInformation> levelInformationList, HighScoresTable table, File file) {
        //this.runner = runner;
        this.gameFlow = gameFlow;
        this.levelInformationList = levelInformationList;
        this.table = table;
        this.file = file;
    }

    /**
     * run.
     *
     * @return null.
     */
    public Void run() {
        try {
            this.table.load(file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.gameFlow.runLevels(this.levelInformationList);
        return null;
    }
}

