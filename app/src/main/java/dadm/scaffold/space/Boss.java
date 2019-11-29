package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;

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

    private static final int INITIAL_BULLET_POOL_AMOUNT = 25;
    private static final long TIME_BETWEEN_BULLETS = 350;
    List<EnemyBullet> bullets = new ArrayList<EnemyBullet>();
    private float timeSinceLastFire;

    public Boss(GameEngine gameEngine, SpaceShipPlayer spaceShip) {
        super(gameEngine, R.drawable.boss);
        this.speed = 200d * pixelFactor/1000d;
        this.movingLeft = true;
        this.spaceShip = spaceShip;

        initBulletPool(gameEngine);
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

        checkFiring(elapsedMillis, gameEngine);
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (timeSinceLastFire > (TIME_BETWEEN_BULLETS)) {
            EnemyBullet bullet = getBullet();
            if (bullet == null) {
                return;
            }
            bullet.init(this, positionX + width/2, positionY);
            gameEngine.addGameObject(bullet);
            timeSinceLastFire = 0;
            gameEngine.onGameEvent(GameEvent.LaserFired);
        }

        else {
            timeSinceLastFire += elapsedMillis;
        }
    }

    private EnemyBullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new EnemyBullet(gameEngine));
        }
    }

    void releaseBullet(EnemyBullet bullet) {
        bullets.add(bullet);
    }
}
