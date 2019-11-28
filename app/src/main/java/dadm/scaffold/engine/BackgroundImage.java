package dadm.scaffold.engine;

import android.graphics.Canvas;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

public abstract class BackgroundImage extends Sprite {

    protected List<PointF> filledPos = new ArrayList<>();

    protected BackgroundImage (GameEngine gameEngine, int drawableRes) {
        super(gameEngine, drawableRes);
        LowestPosition();
    }

    protected void LowestPosition() {
        while (positionX > width) {
            positionX -= width;
        }
        while (positionX < -width) {
            positionX += width;
        }

        while (positionY > height) {
            positionY -= height;
        }
        while (positionY < -height) {
            positionY += height;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        filledPos.clear();
        DrawRecursively(canvas, positionX, positionY);
    }

    protected void DrawRecursively(Canvas canvas, double posX, double posY) {
        if (posX > canvas.getWidth()
                || posY > canvas.getHeight()
                || posX < - width
                || posY < - height) {
            return;
        }
        filledPos.add(new PointF((float)posX, (float)posY));
        matrix.reset();
        matrix.postScale((float) pixelFactor, (float) pixelFactor);
        matrix.postTranslate((float) positionX, (float) positionY);
        matrix.postRotate((float) rotation, (float) (positionX + width/2), (float) (positionY + height/2));
        canvas.drawBitmap(bitmap, matrix, null);
        PointF[] points = {
                new PointF((float)(posX - width), (float)posY),
                new PointF((float)(posX + width), (float)posY),
                new PointF((float)posX, (float)(posY - height)),
                new PointF((float)posX, (float)(posY + height))
        };
        for (PointF p : points) {
            if (!filledPos.contains(p)) {
                DrawRecursively(canvas, p.x, p.y);
            }
        }
    }

}
