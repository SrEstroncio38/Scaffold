package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.sound.GameEvent;

public class Boss extends Sprite {

    private double speed;
    private boolean movingLeft;
    private SpaceShipPlayer spaceShip;

    public int life = 20;

    public Boss(GameEngine gameEngine, SpaceShipPlayer spaceShip) {
        super(gameEngine, R.drawable.boss);
        this.speed = 200d * pixelFactor/1000d;
        this.movingLeft = true;
        this.spaceShip = spaceShip;
    }

    public void init(GameEngine gameEngine) {
        positionX = gameEngine.width/2;
        positionY = -height;
        life = 50;
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        if (positionY < height) {
            positionY += speed * elapsedMillis;
        } else {

            if (positionX < 0 && movingLeft) {
                movingLeft = false;
            } else if (positionX > gameEngine.width - width && !movingLeft) {
                movingLeft = true;
            }

            if (movingLeft) {
                positionX -= speed * elapsedMillis;
            } else {
                positionX += speed * elapsedMillis;
            }

        }

        if (life <= 0) {
            spaceShip.scoreObj.enemies++;
            spaceShip.endLevel(gameEngine);
        }
    }
}
