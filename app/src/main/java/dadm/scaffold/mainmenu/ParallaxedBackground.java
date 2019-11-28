package dadm.scaffold.mainmenu;

import dadm.scaffold.engine.BackgroundImage;
import dadm.scaffold.engine.GameEngine;

public class ParallaxedBackground extends BackgroundImage {

    public ParallaxedBackground(GameEngine gameEngine, int drawableId) {
        super(gameEngine, drawableId);
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += 0.2 * elapsedMillis;
    }
}
