import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.GameFlow;
import game.LevelInformation;
import game.animations.AnimationRunner;
import game.animations.HighScoresAnimation;
import game.animations.KeyPressStoppableAnimation;
import game.data.scores.HighScoresTable;
//import gameutils.AnimationRunner;
//import gameutils.GameFlow;
//import gameutils.KeyPressStoppableAnimation;
import gameutils.MenuAnimation;
//import interfaces.LevelInformation;
import interfaces.Task;
import readers.LevelSetsReader;
//import scoreuses.HighScoresAnimation;
//import scoreuses.HighScoresTable;
import tasks.ExitTask;
import tasks.ShowHiScoresTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * an Ass6Game class.
 */
public class Ass7Game {
    /**
     * main - the main function that initializes "GameLevel" class.
     *
     * @param args an array that contains the program arguments.
     */
    public static void main(String[] args) {

        List<LevelInformation> levelInformationList = new ArrayList<>();

        GUI gui = new GUI("Arkanoid", 800, 600);

        AnimationRunner ar = new AnimationRunner(gui, 60);

        KeyboardSensor ks = gui.getKeyboardSensor();

        String levelSet;
        if (args.length > 0) {
            levelSet = args[0];
        } else {
            levelSet = "level_sets.txt";
        }


        int topScores = 5;
        HighScoresTable table = new HighScoresTable(topScores);
        File highscoresFile = new File("highscores");
        GameFlow gameFlow = new GameFlow(table, ar, ks, highscoresFile);

        try {
            table.load(highscoresFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        MenuAnimation<Task<Void>> menu = new MenuAnimation<Task<Void>>("Arkanoid", ks, ar);

        KeyPressStoppableAnimation score = new KeyPressStoppableAnimation(
                new HighScoresAnimation(table), ks, "space");

        menu.addSelection("h", "High Scores", new ShowHiScoresTask(ar, score));

        LevelSetsReader levelSetsReader =
                new LevelSetsReader(levelSet, "Choose Mode", ks, ar, gameFlow, table, highscoresFile);

        menu.addSubMenu("s", "Let The Party Begin", levelSetsReader.createSubMenuFromFile());

        menu.addSelection("q", "Quit", new ExitTask(ar));


        while (true) {
            ar.run(menu);
            //get user selection
            Task<Void> task = menu.getStatus();
            task.run();
            score.reset();
            menu.reset();
        }
    }
}
