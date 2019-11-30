package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Score;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.sound.GameEvent;

public class Canon extends Sprite {

    private double speedFactor;

    private SpaceShipPlayer parent;

    public Score scoreObj;
    //Si es false va hacia la izq
    private boolean dir;

    public Canon(GameEngine gameEngine, Score score){
        super(gameEngine, R.drawable.canon2);
        this.scoreObj = score;
        speedFactor = gameEngine.pixelFactor * -300d / 2000d;
        dir = false;
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        if(dir){
            positionY += speedFactor * elapsedMillis;
            positionX -= speedFactor * elapsedMillis;
        } else {
            positionY += speedFactor * elapsedMillis;
            positionX += speedFactor * elapsedMillis;
        }

        if (positionY < -height) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseCanon(this);
        }
    }


    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY, boolean dir) {
        this.dir = dir;
        positionX = initPositionX - width/2;
        positionY = initPositionY - height/2;
        parent = parentPlayer;
    }

    private void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        // And return it to the pool
        parent.releaseCanon(this);
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
            scoreObj.totalPoints += 20;
            removeObject(gameEngine);
            gameEngine.onGameEvent(GameEvent.BossHit);
            Boss b = (Boss) otherObject;
            b.life -= 2;
        }
    }
}
