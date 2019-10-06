package game.animations;

import biuoop.DrawSurface;
import game.data.scores.HighScoresTable;

import java.awt.Color;

/**
 * The type High scores animation.
 */
public class HighScoresAnimation implements Animation {

    private HighScoresTable highScoresTable;

    /**
     * Instantiates a new High scores animation.
     *
     * @param scoresTable the scores table
     */
    public HighScoresAnimation(HighScoresTable scoresTable) {
        this.highScoresTable = scoresTable;
    }

    @Override
    public void doOneFrame(DrawSurface d) {


        if (this.highScoresTable.getSize() == 0) {
            d.setColor(new Color(255, 255, 255));
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
            d.setColor(new Color(1, 1, 1));
            int headLineX = (int) (d.getWidth() * 0.05);
            int headLineY = (int) (d.getHeight() * 0.10);
            d.drawText(headLineX, headLineY, "High Scores", 30);
        } else {
            d.setColor(new Color(255, 255, 255));
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
            d.setColor(new Color(1, 1, 1));
            int headLineX = (int) (d.getWidth() * 0.05);
            int headLineY = (int) (d.getHeight() * 0.10);
            d.drawText(headLineX, headLineY, "High Scores", 30);
            int subHeadLine1X = (int) (d.getWidth() * 0.10);
            int subHeadLineY = (int) (d.getHeight() * 0.20);
            int subHeadLine2X = (int) (d.getWidth() * 0.65);
            int endOfLineX = (int) (d.getWidth() * 0.90);
            int lineY = (subHeadLineY + 10);
            d.setColor(Color.darkGray);
            d.drawText(subHeadLine1X, subHeadLineY, "Player Name", 25);
            d.drawText(subHeadLine2X, subHeadLineY, "Score", 25);
            d.drawLine(subHeadLine1X, lineY, endOfLineX, lineY);
            int namesX = subHeadLine1X;
            int scoresX = subHeadLine2X;
            int firstNameY = lineY + 40;
            int linesArea = d.getHeight() - firstNameY;
            int lineHeight = linesArea / this.highScoresTable.getMaxAllowedSize();
            for (int i = 0; i < this.highScoresTable.getSize(); i++) {
                d.setColor(Color.blue);
                d.drawText(namesX, (firstNameY + (i * lineHeight)),
                        this.highScoresTable.getHighScores().get(i).getName(), 25);
                Integer someScore = this.highScoresTable.getHighScores().get(i).getScore();
                d.drawText(scoresX, (firstNameY + (i * lineHeight)),
                        someScore.toString(), 25);
            }
        }

    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
