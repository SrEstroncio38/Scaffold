package dadm.scaffold.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Score extends GameObject {

    private final float textWidth;
    private final float textHeight;

    private Paint paint;
    public int totalPoints;
    private int draws;

    public Score(GameEngine gameEngine) {
        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        textHeight = (float) (40 * gameEngine.pixelFactor);
        textWidth = (float) (25 * gameEngine.pixelFactor);
        paint.setTextSize(textHeight / 2);
    }

    @Override
    public void startGame() {
        totalPoints = 0;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }

    @Override
    public void onDraw(Canvas canvas) {
        if (totalPoints <= 0) totalPoints = 0;
        String stringPoints = ((Integer)totalPoints).toString();
        paint.setColor(Color.WHITE);
        canvas.drawText(stringPoints, (int) (canvas.getWidth() - 65), (int) (canvas.getHeight() - 25), paint);
        draws++;
    }
}
