package dadm.scaffold.space;

import android.content.Context;

import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.counter.GameFragment;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Lifes;
import dadm.scaffold.engine.Score;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;
import dadm.scaffold.sound.GameEvent;

public class SpaceShipPlayer extends Sprite {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 25;
    private static final long TIME_BETWEEN_BULLETS = 400;
    private static final int CANON_OFFSET = 80;
    List<Bullet> bullets = new ArrayList<Bullet>();
    List<Canon> canons = new ArrayList<Canon>();
    private float timeSinceLastFire;
    private float timeSinceLastFireAA;
    private int currentShip;

    private int maxX;
    private int maxY;
    private double speedFactor;
    public int lifes;
    public int score;
    public Lifes lifeObj;
    public Score scoreObj;
    public ScaffoldActivity scaffold;



    public SpaceShipPlayer(GameEngine gameEngine, int shipDrawable, Lifes lifes, Score score, ScaffoldActivity activity){
        super(gameEngine, shipDrawable);
        currentShip = shipDrawable;
        speedFactor = pixelFactor * 150d / 1000d; // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width - width;
        maxY = gameEngine.height - height;
        this.lifes = 3;
        this.score = 0;
        this.lifeObj = lifes;
        this.scoreObj = score;
        this.scaffold = activity;

        initBulletPool(gameEngine);
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine, scoreObj));
        }

        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            canons.add(new Canon(gameEngine, scoreObj));
        }
    }

    private Bullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    void releaseBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    private Canon getCanon() {
        if (canons.isEmpty()) {
            return null;
        }
        return canons.remove(0);
    }

    void releaseCanon(Canon canon) {
        canons.add(canon);
    }


    @Override
    public void startGame() {
        positionX = maxX / 2;
        positionY = maxY * 2 / 3;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        // Get the info from the inputController
        updatePosition(elapsedMillis, gameEngine.theInputController);
        checkFiring(elapsedMillis, gameEngine);
    }

    private void updatePosition(long elapsedMillis, InputController inputController) {
        positionX += speedFactor * inputController.horizontalFactor * elapsedMillis;
        if (positionX < 0) {
            positionX = 0;
        }
        if (positionX > maxX) {
            positionX = maxX;
        }
        positionY += speedFactor * inputController.verticalFactor * elapsedMillis;
        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (gameEngine.theInputController.isFiring && timeSinceLastFire > (TIME_BETWEEN_BULLETS + CANON_OFFSET)) {
            Canon canonI = getCanon();
            if (canonI == null) {
                return;
            }
            Canon canonD = getCanon();
            if (canonD == null) {
                return;
            }
            canonI.init(this, positionX + width/2, positionY,false);
            canonD.init(this, positionX + width/2, positionY,true);
            gameEngine.addGameObject(canonI);
            gameEngine.addGameObject(canonD);
            timeSinceLastFire = 0;
            gameEngine.onGameEvent(GameEvent.CanonFired);
        }

        else {
            timeSinceLastFire += elapsedMillis;
        }

        if (timeSinceLastFireAA > (TIME_BETWEEN_BULLETS)) {
            Bullet bullet = getBullet();
            if (bullet == null) {
                return;
            }
            bullet.init(this, positionX + width/2, positionY);
            gameEngine.addGameObject(bullet);
            timeSinceLastFireAA = 0;
            gameEngine.onGameEvent(GameEvent.LaserFired);
        }

        else {
            timeSinceLastFireAA += elapsedMillis;
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Asteroid) {
            Asteroid a = (Asteroid) otherObject;
            a.removeObject(gameEngine);
            lifes--;
            lifeObj.totalLifes = lifes;
            if (lifes <= 0) {
                endLevel(gameEngine);
            }
            gameEngine.onGameEvent(GameEvent.SpaceshipHit);
        }
    }

    public void endLevel(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        scaffold.endGame(lifeObj, scoreObj, currentShip);
        gameEngine.stopGame();
    }
}
