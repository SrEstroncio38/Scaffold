package dadm.scaffold.space;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;

public class GameController extends GameObject {

    private static final int TIME_BETWEEN_ENEMIES = 500;
    private long currentMillis;
    private List<Asteroid> asteroidPool = new ArrayList<Asteroid>();
    private int enemiesSpawned;
    private SpaceShipPlayer spaceShip;
    private GameEngine gameEngine;
    private Boss boss;
    private boolean bossSpawned = false;

    private final int NUMBER_OF_ASTEROIDS = 25;

    public GameController(GameEngine gameEngine, SpaceShipPlayer spaceShip) {
        this.gameEngine = gameEngine;
        this.spaceShip = spaceShip;
        // We initialize the pool of items now
        for (int i=0; i<10; i++) {
            asteroidPool.add(new Asteroid(this, gameEngine));
        }
        boss = new Boss(gameEngine, spaceShip);
    }

    @Override
    public void startGame() {
        currentMillis = 0;
        enemiesSpawned = 0;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        if (!bossSpawned) {
            currentMillis += elapsedMillis;

            long waveTimestamp = enemiesSpawned * TIME_BETWEEN_ENEMIES;
            if (currentMillis > waveTimestamp) {
                // Spawn a new enemy
                Asteroid a = asteroidPool.remove(0);
                a.init(gameEngine);
                gameEngine.addGameObject(a);
                enemiesSpawned++;
                return;
            }

            if (enemiesSpawned > NUMBER_OF_ASTEROIDS) {
                boss.init(gameEngine);
                gameEngine.addGameObject(boss);
                bossSpawned = true;
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        // This game object does not draw anything
    }

    public void returnToPool(Asteroid asteroid) {
        asteroidPool.add(asteroid);
    }
}
