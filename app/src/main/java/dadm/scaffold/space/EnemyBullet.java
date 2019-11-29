package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Score;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.sound.GameEvent;

public class EnemyBullet extends Sprite {

    private double speedFactor;

    private Boss parent;

    public EnemyBullet(GameEngine gameEngine){
        super(gameEngine, R.drawable.bullet);
        speedFactor = gameEngine.pixelFactor * 300d / 1000d;
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedFactor * elapsedMillis;
        if (positionY < -height) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBullet(this);
        }
    }


    public void init(Boss parentBoss, double initPositionX, double initPositionY) {
        positionX = initPositionX - width/2;
        positionY = initPositionY - height/2;
        parent = parentBoss;
    }

    public void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        // And return it to the pool
        parent.releaseBullet(this);
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof SpaceShipPlayer) {
            removeObject(gameEngine);
            SpaceShipPlayer s = (SpaceShipPlayer) otherObject;
            s.lifes--;
            s.lifeObj.totalLifes = s.lifes;
            if (s.lifes <= 0) {
                s.endLevel(gameEngine);
            }
            gameEngine.onGameEvent(GameEvent.AsteroidHit);
        }
    }
}
