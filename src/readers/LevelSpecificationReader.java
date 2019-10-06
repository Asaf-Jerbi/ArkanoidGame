package readers;

import game.LevelInformation;
import game.Velocity;
import interfaces.BackGround;
import objects.BackGroundParser;
import sprites.Sprite;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * a LevelSpecificationReader class.
 */
public class LevelSpecificationReader {

    private List<String> lines = new ArrayList<>();
    private List<List<String>> levelDesc = new ArrayList<>();
    private List<String> blocksLayout;
    private Map<String, String> levelInfoMap = new TreeMap<>();
    private List<LevelInformation> levelsInfo = new ArrayList<>();


    /**
     * Read lines.
     *
     * @param reader the reader
     */
    public void readLines(Reader reader) {
        try {
            BufferedReader reader1 = new BufferedReader(reader);
            String line = reader1.readLine();
            while (line != null) {

                if ((!line.isEmpty()) && (!line.startsWith("#"))) {
                    lines.add(line);
                }
                line = reader1.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Extract level info.
     */
    public void extractLevelInfo() {

        //variables
        String levelName;
        int paddleSpeed;
        int paddleWidth;
        int blocksStartX;
        int blocksStartY;
        int rowHeight;
        int numBlocks;

        List<Velocity> initialBallVelocities = new ArrayList<>();
        BackGround background = null;
        List<String> blocksInfo = this.blocksLayout;
        BlocksFromSymbolsFactory blocksFromSymbolsFactory = null;


        if (levelInfoMap.containsKey("level_name")) {
            levelName = levelInfoMap.get("level_name");
        } else {
            throw new RuntimeException("no level_name found");
        }
        if (levelInfoMap.containsKey("ball_velocities")) {
            String[] pairs = levelInfoMap.get("ball_velocities").split(" ");
            Velocity v;
            for (int i = 0; i < pairs.length; i++) {
                String[] angleAndSpeed = pairs[i].split(",");
                double angle = Double.parseDouble(angleAndSpeed[0]);
                double speed = Double.parseDouble(angleAndSpeed[1]);
                v = Velocity.fromAngleAndSpeedPatch(angle, -speed);
                initialBallVelocities.add(v);
            }
        } else {
            throw new RuntimeException("no ball_velocities found");
        }
        if (this.levelInfoMap.containsKey("background")) {
            String bg = levelInfoMap.get("background");
            BackGroundParser backGroundParser = new BackGroundParser();
            background = backGroundParser.backGroundFromString(bg);
        }
        if (this.levelInfoMap.containsKey("paddle_speed")) {
            String padSpeedString = this.levelInfoMap.get("paddle_speed");
            paddleSpeed = Integer.parseInt(padSpeedString);
        } else {
            throw new RuntimeException("no paddle_speed found");
        }
        if (this.levelInfoMap.containsKey("paddle_width")) {
            String padWidthString = this.levelInfoMap.get("paddle_width");
            paddleWidth = Integer.parseInt(padWidthString);
        } else {
            throw new RuntimeException("no paddle_width found");
        }

        if (levelInfoMap.containsKey("blocks_start_x")) {
            blocksStartX = Integer.parseInt(levelInfoMap.get("blocks_start_x"));
        } else {
            throw new RuntimeException("no blocks_start_x found");
        }
        if (levelInfoMap.containsKey("blocks_start_y")) {
            blocksStartY = Integer.parseInt(levelInfoMap.get("blocks_start_y"));
        } else {
            throw new RuntimeException("no blocks_start_y found");
        }
        if (levelInfoMap.containsKey("row_height")) {
            rowHeight = Integer.parseInt(levelInfoMap.get("row_height"));
        } else {
            throw new RuntimeException("no row_height found");
        }
        if (levelInfoMap.containsKey("num_blocks")) {
            numBlocks = Integer.parseInt(levelInfoMap.get("num_blocks"));
        } else {
            throw new RuntimeException("no num_blocks found");
        }
        if (levelInfoMap.containsKey("block_definitions")) {
            String blockDefFile = levelInfoMap.get("block_definitions");

            Reader blockDefReader = null;
            try {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(blockDefFile)));

                blocksFromSymbolsFactory = BlocksDefinitionReader.fromReader(br);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("no block_definitions found");
        }
        List<Object> blocksProperties = new ArrayList<>();
        blocksProperties.add(rowHeight);
        blocksProperties.add(blocksStartX);
        blocksProperties.add(blocksStartY);
        blocksProperties.add(blocksInfo);
        blocksProperties.add(blocksFromSymbolsFactory);
        levelsInfo.add(new LevelFactory(initialBallVelocities, paddleSpeed, paddleWidth,
                levelName, (Sprite) background, blocksProperties, numBlocks));
    }

    /**
     * separateLevels - split the lines to levels.
     */
    public void separateLevels() {
        int k = 0;
        int i = 0;
        while (i < lines.size()) {
            if (lines.get(i).startsWith("START_LEVEL")) {
                ++i;
                this.levelDesc.add(new ArrayList<>());
                while (!lines.get(i).startsWith("END_LEVEL")) {
                    this.levelDesc.get(k).add(lines.get(i));
                    ++i;
                    if (lines.get(i).startsWith("END_LEVEL")) {
                        ++k;
                        break;
                    }
                }
            }
            ++i;
        }
    }

    /**
     * separateToLevelInfos.
     *
     * @param oneLevelInformation the single level info
     */
    public void separateToLevelInfos(List<String> oneLevelInformation) {
        int i = 0;
        this.blocksLayout = null;
        this.blocksLayout = new ArrayList<>();
        if (i < oneLevelInformation.size()) {
            do {
                if (oneLevelInformation.get(i).startsWith("START_BLOCKS")) {
                    i++;
                    while (!oneLevelInformation.get(i).startsWith("END_BLOCKS")) {
                        this.blocksLayout.add(oneLevelInformation.get(i));
                        i++;
                    }
                }
                if (oneLevelInformation.get(i).contains(":")) {
                    String[] afterSplit = oneLevelInformation.get(i).split(":");
                    levelInfoMap.put(afterSplit[0], afterSplit[1]);
                }
                i++;
            } while (i < oneLevelInformation.size());
        }
    }


    /**
     * From reader list.
     *
     * @param reader the reader
     * @return the list
     */
    public List<LevelInformation> fromReader(Reader reader) {
        this.readLines(reader);
        this.separateLevels();
        List<List<String>> levelDescCopy = new ArrayList<>(this.levelDesc);
        for (List<String> list : levelDescCopy) {
            this.separateToLevelInfos(list);
            this.extractLevelInfo();
        }
        return this.levelsInfo;
    }
}
