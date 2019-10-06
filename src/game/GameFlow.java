package game;

import biuoop.DialogManager;
import game.animations.GameOverScreen;
import game.animations.HighScoresAnimation;
import game.animations.KeyPressStoppableAnimation;
import game.animations.YouWinScreen;
import game.data.scores.HighScoresTable;
import game.data.scores.ScoreInfo;
import helpers.Counter;

import biuoop.KeyboardSensor;
import game.animations.AnimationRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The type Game flow.
 */
public class GameFlow {


    /**
     * The enum Status.
     */
    public enum Status {
        /**
         * Failure status.
         */
        LOSE,
        /**
         * Success status.
         */
        WIN
    }

    //Fields
    private static final int LIVES = 7;
    private static final int SCORES_TABLE_SIZE = 10;

    private Counter score;
    private Counter lives;
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private Status status;
    private HighScoresTable highScoresTable;

    //**new new new**
    private File file;


    /**
     * Instantiates a new Game flow.
     *
     * @param highScoresTable the high scores table
     * @param ar              the ar
     * @param ks              the ks
     * @param file            the file
     */
//Constructor
    public GameFlow(HighScoresTable highScoresTable, AnimationRunner ar, KeyboardSensor ks, File file) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.score = new Counter(0);
        //create 7 lives
        this.lives = new Counter(0);
        this.status = Status.WIN;
        this.highScoresTable = highScoresTable;

        //**new new new**
        this.file = file;
    }


    /**
     * Run levels.
     *
     * @param levels the levels
     */
//Methods
    public void runLevels(List<LevelInformation> levels) {

        //initialize the game's lives stock:
        this.lives.increase(LIVES);

        //load scoresTable:
        try {
            this.highScoresTable.load(file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //Run levels:
        for (LevelInformation levelInfo : levels) {

            //create level using level information:
            GameLevel level = new GameLevel(levelInfo, this.keyboardSensor,
                    this.animationRunner, this.score, this.lives);

            //initialize the level:
            level.initialize();

            //As long as the lives stock in not empty and not all of the blocks were wrecked:
            while (this.lives.getValue() > 0 && level.getRemainingBlocks().getValue() > 0) {
                level.playOneTurn();
                this.lives = level.getLives();
                this.score = level.getScore();
            }

            //if lives stock is empty, game status is LOSE:
            if (this.lives.getValue() == 0) {
                this.status = Status.LOSE;
                break;
            }
        }

        //show game status screen (win / lose):
        showGameStatus();

        //In case user's performances were good enough, ask for his name and save his score to high scores table:
        if (userPlayedWell()) {
            addUserToHighScores(getUserDetails());
        }

        //In any case, at the end of the game, show the highScores table:
        this.animationRunner.run(new KeyPressStoppableAnimation(new
                HighScoresAnimation(this.highScoresTable), this.keyboardSensor, "space"));

        this.score = new Counter(0);
    }

    /**
     * User played well boolean.
     *
     * @return the boolean
     */
//check if user score is good to be inserted to highestScores table (higher then the last entry)
    private boolean userPlayedWell() {

        int lastIndex = this.highScoresTable.getHighScores().size() - 1;

        return (this.highScoresTable.getHighScores().size() < this.highScoresTable.getMaxAllowedSize()
                || (this.score.getValue() >= this.highScoresTable.getHighScores().get(lastIndex).getScore()));
    }

    /**
     * Gets animation runner.
     *
     * @return the animation runner
     */
    public AnimationRunner getAnimationRunner() {
        return animationRunner;
    }

    /**
     * Show game status.
     */
    private void showGameStatus() {
        if (this.status == Status.WIN) {
            this.animationRunner.run(new KeyPressStoppableAnimation(new
                    YouWinScreen(this.score), this.keyboardSensor, "space"));
        } else {
            this.animationRunner.run(new KeyPressStoppableAnimation(new
                    GameOverScreen(this.score), this.keyboardSensor, "space"));
        }
    }

    /**
     * Gets user details.
     *
     * @return the user details
     */
    private String getUserDetails() {
        DialogManager dialogManager = this.getAnimationRunner().getGui().getDialogManager();
        String name = dialogManager.showQuestionDialog("Name", "What is your name?", "Anonymous");
        return name;
    }

    /**
     * Add user to high scores.
     *
     * @param name the name
     */
    private void addUserToHighScores(String name) {
        this.highScoresTable.add(new ScoreInfo(name, this.score.getValue()));
        try {
            this.highScoresTable.save(new File("highscores"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
