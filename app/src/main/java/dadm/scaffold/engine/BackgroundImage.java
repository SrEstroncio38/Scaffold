package dadm.scaffold.engine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public abstract class BackgroundImage extends GameObject {

    protected double positionX;
    protected double positionY;

    protected int width;
    protected int height;

    protected double rotation;

    protected double pixelFactor;

    protected final Bitmap bitmap;

    protected final Matrix matrix = new Matrix();

    protected List<PointF> filledPos = new ArrayList<>();

    protected BackgroundImage (GameEngine gameEngine, int drawableRes) {
        Resources r = gameEngine.getContext().getResources();
        Drawable spriteDrawable = r.getDrawable(drawableRes);

        this.pixelFactor = gameEngine.pixelFactor;

        this.width = (int) (spriteDrawable.getIntrinsicWidth() * this.pixelFactor);
        this.height = (int) (spriteDrawable.getIntrinsicHeight() * this.pixelFactor);

        this.bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();

        LowestPosition();
    }

    protected void LowestPosition() {
        while (positionX > width / pixelFactor) {
            positionX -= width / pixelFactor;
        }
        while (positionX < 0) {
            positionX += width / pixelFactor;
        }

        while (positionY > height / pixelFactor) {
            positionY -= height / pixelFactor;
        }
        while (positionY < 0) {
            positionY += height / pixelFactor;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        filledPos.clear();
        LowestPosition();
        DrawRecursively(canvas, positionX, positionY);
    }

    protected void DrawRecursively(Canvas canvas, double posX, double posY) {
        if (posX > canvas.getWidth()
                || posY > canvas.getHeight()
                || posX < - width / pixelFactor
                || posY < - height / pixelFactor) {
            return;
        }
        filledPos.add(new PointF((float)posX, (float)posY));
        matrix.reset();
        matrix.postScale((float) pixelFactor, (float) pixelFactor);
        matrix.postTranslate((float) posX, (float) posY);
        matrix.postRotate((float) rotation, (float) (posX + width/2), (float) (posY + height/2));
        canvas.drawBitmap(bitmap, matrix, null);
        PointF[] points = {
                new PointF((float)(posX - width / pixelFactor), (float)posY),
                new PointF((float)(posX + width / pixelFactor), (float)posY),
                new PointF((float)posX, (float)(posY - height / pixelFactor)),
                new PointF((float)posX, (float)(posY + height / pixelFactor))
        };
        for (PointF p : points) {
            if (!filledPos.contains(p)) {
                DrawRecursively(canvas, p.x, p.y);
            }
        }
    }

}
