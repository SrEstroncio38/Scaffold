package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Score;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.sound.GameEvent;

public class Bullet extends Sprite {

    private double speedFactor;

    private SpaceShipPlayer parent;

    public Score scoreObj;

    public Bullet(GameEngine gameEngine, Score score){
        super(gameEngine, R.drawable.canon);
        this.scoreObj = score;
        speedFactor = gameEngine.pixelFactor * -300d / 1000d;
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


    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - width/2;
        positionY = initPositionY - height/2;
        parent = parentPlayer;
    }

    public void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        // And return it to the pool
        parent.releaseBullet(this);
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Asteroid) {
            // Remove both from the game (and return them to their pools)
            scoreObj.totalPoints += 10;
            scoreObj.enemies++;
            removeObject(gameEngine);
            Asteroid a = (Asteroid) otherObject;
            a.removeObject(gameEngine);
            gameEngine.onGameEvent(GameEvent.AsteroidHit);
            // Add some score
        } else if (otherObject instanceof Boss) {
            scoreObj.totalPoints += 10;
            removeObject(gameEngine);
            gameEngine.onGameEvent(GameEvent.BossHit);
            Boss b = (Boss) otherObject;
            b.life -= 1;
        }
    }
}
