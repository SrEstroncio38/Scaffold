package dadm.scaffold.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Lifes extends GameObject {

    private final float textWidth;
    private final float textHeight;

    private Paint paint;
    public int totalLifes;
    private int draws;


    public Lifes(GameEngine gameEngine) {
        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        textHeight = (float) (40 * gameEngine.pixelFactor);
        textWidth = (float) (25 * gameEngine.pixelFactor);
        paint.setTextSize(textHeight / 2);
    }

    @Override
    public void startGame() {
        totalLifes = 3;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }

    @Override
    public void onDraw(Canvas canvas) {
        if (totalLifes <= 0) totalLifes = 0;
        String stringLifes = ((Integer)totalLifes).toString();
        /*paint.setColor(Color.BLACK);
        canvas.drawRect(0, (int) (canvas.getHeight() - textHeight), textWidth, canvas.getHeight(), paint);*/
        paint.setColor(Color.WHITE);
        canvas.drawText(stringLifes, (int) (135), (int) (90), paint);
        draws++;
    }
}
