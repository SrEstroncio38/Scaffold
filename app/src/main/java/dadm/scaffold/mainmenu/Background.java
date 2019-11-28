package dadm.scaffold.mainmenu;

import dadm.scaffold.R;
import dadm.scaffold.engine.BackgroundImage;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;

public class Background extends BackgroundImage {

    public Background(GameEngine gameEngine) {
        super(gameEngine, R.drawable.menubg);
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }
}
