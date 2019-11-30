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
    protected int screenHeight;
    protected int screenWidth;

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

        positionX = (gameEngine.width/2) - (width/2);

        screenHeight = gameEngine.height;
        screenWidth = gameEngine.width;

        LowestPosition();
    }

    protected void LowestPosition() {
        while (positionX > screenWidth) {
            positionX -= width;
        }
        while (positionX < screenWidth - width) {
            positionX += width;
        }

        while (positionY > screenHeight) {
            positionY -= height;
        }
        while (positionY < screenHeight - height) {
            positionY += height;
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
                || posX < - width
                || posY < - height) {
            return;
        }
        filledPos.add(new PointF((float)posX, (float)posY));
        matrix.reset();
        matrix.postScale((float) pixelFactor, (float) pixelFactor);
        matrix.postTranslate((float) posX, (float) posY);
        matrix.postRotate((float) rotation, (float) (posX + width/2), (float) (posY + height/2));
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
