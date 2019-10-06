package sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.GameLevel;
import game.Velocity;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

/**
 * The type sprites.Paddle.
 */
public class Paddle implements Collidable, Sprite {

    //Fields:

    private Block paddleBlock;
    private Point upperLeft;
    private double width, height;
    private GameLevel gameLevel;
    private KeyboardSensor keyboard;
    private int speed;

    /**
     * Instantiates a new sprites.Paddle.
     *
     * @param paddleBlock the paddle block
     * @param speed       the speedd
     * @param gameLevel   the gameLevel
     */
//Constructor
    public Paddle(Block paddleBlock, int speed, GameLevel gameLevel) {
        this.paddleBlock = paddleBlock;
        this.upperLeft = this.paddleBlock.getCollisionRectangle().getUpperLeft();
        this.width = this.paddleBlock.getCollisionRectangle().getWidth();
        this.height = this.paddleBlock.getCollisionRectangle().getHeight();
        this.gameLevel = gameLevel;
        this.keyboard = gameLevel.getKeyboard();
        this.speed = speed;
    }

    //sprites.Collidable

    @Override
    public Rectangle getCollisionRectangle() {
        return this.paddleBlock.getCollisionRectangle();
    }

    /**
     * Gets paddle block.
     *
     * @return the paddle block
     */
    public Block getPaddleBlock() {
        return paddleBlock;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        final double numberOfPieces = 5.0;
        Rectangle paddleRect = this.getCollisionRectangle();
        double distance = collisionPoint.distance(paddleRect.getUpperLeft());
        double pieceSize = this.width / numberOfPieces;
        double speedd = Math.sqrt(Math.pow(currentVelocity.getDx(), 2) + Math.pow(currentVelocity.getDy(), 2));
        //if collision point is from above:
        if (paddleRect.getUpSide().isonLineSegment(collisionPoint)) {
            //1st part of the paddle
            if (distance < pieceSize) {
                return Velocity.fromAngleAndSpeed(240, speedd);
            }
            //2nd part of the paddle
            if (distance > pieceSize && distance < 2 * pieceSize) {
                return Velocity.fromAngleAndSpeed(210, speedd);
            }
            //3st part of the paddle
            if (distance > 2 * pieceSize && distance < 3 * pieceSize) {
                return Velocity.fromAngleAndSpeed(180, speedd);
            }
            //4st part of the paddle
            if (distance > 3 * pieceSize && distance < 4 * pieceSize) {
                return Velocity.fromAngleAndSpeed(150, speedd);

            }
            //5st part of the paddle
            if (distance > 4 * pieceSize && distance < 5 * pieceSize) {
                return Velocity.fromAngleAndSpeed(120, speed);
            }

        }


        return this.paddleBlock.hit(hitter, collisionPoint, currentVelocity);
        //throw new RuntimeException("sprites.Ball hit the paddle at unexpected area? check hit method of paddle");
    }

    //sprites.Sprite
    @Override
    public void drawOn(DrawSurface surface) {
        Rectangle r = this.getCollisionRectangle();

        //filling the paddle
        surface.setColor(new Color(255, 253, 10));
        surface.fillRectangle((int) this.upperLeft.getX(), (int) this.upperLeft.getY(),
                (int) r.getWidth(), (int) r.getHeight());
        surface.setColor(new Color(245, 233, 44));
        surface.drawRectangle((int) this.upperLeft.getX(), (int) this.upperLeft.getY(),
                (int) r.getWidth(), (int) r.getHeight());

        //drawing frame to paddle
        surface.setColor(Color.MAGENTA);
        surface.drawRectangle((int) this.upperLeft.getX(), (int) this.upperLeft.getY(),
                (int) r.getWidth(), (int) r.getHeight());
    }

    @Override
    public void timePassed() {
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)
                && this.getCollisionRectangle().getUpperRight().getX() < 790) {
            moveRight(this.speed);
        }
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY) && this.upperLeft.getX() > 10) {
            moveLeft(this.speed);
        }
    }


    /**
     * Move right.
     *
     * @param speedd the speedd
     */
    public void moveRight(int speedd) {
        Point newUpperLeft = new Point(this.upperLeft.getX() + speed, this.upperLeft.getY());
        Rectangle newRectangle = new Rectangle(newUpperLeft, this.width, this.height);
        this.paddleBlock.setRectangle(newRectangle);
        this.upperLeft = newUpperLeft;
    }

    /**
     * Move left.
     *
     * @param speedd the speedd
     */
    public void moveLeft(int speedd) {
        Point newUpperLeft = new Point(this.upperLeft.getX() - speed, this.upperLeft.getY());
        Rectangle newRectangle = new Rectangle(newUpperLeft, this.width, this.height);
        this.paddleBlock.setRectangle(newRectangle);
        this.upperLeft = newUpperLeft;
    }

    /**
     * Add to gameLevel.
     *
     * @param g the g
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * Remove from gameLevel.
     *
     * @param g the g
     */
    public void removeFromGame(GameLevel g) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }


    /**
     * Sets speedd.
     *
     * @param speedd the speedd
     */
//**NEW NEW NEW**
    public void setSpeedd(int speedd) {
        this.speed = speedd;
    }
}