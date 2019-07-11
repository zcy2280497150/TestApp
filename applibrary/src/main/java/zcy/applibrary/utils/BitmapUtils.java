package zcy.applibrary.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class BitmapUtils {

    private static float DENSITY;

    public static void init(float density){
        DENSITY = density;
    }

    /**
     * 图片合成
     * @param bitmapBg 底图
     * @param text 邀请码
     * @param bitmapCode 二维码
     * @return 合成之后的bitmap
     */
    public static Bitmap editBitmap(Bitmap bitmapBg, String text, Bitmap bitmapCode) {

        int width = bitmapBg.getWidth();//底图宽
        int height = bitmapBg.getHeight();//底图高

        Bitmap bitmap = Bitmap.createBitmap(width, height, bitmapBg.getConfig());//创建和底图宽高一直的BitMap，这里bitMap只是个框，里面什么都没有填充

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);//画笔
        Canvas canvas = new Canvas(bitmap);//画布
        RectF rectF = new RectF(); //矩形

        canvas.drawBitmap(bitmapBg, 0, 0, paint);//绘制底图

        //绘制邀请码的内容部分
        paint.setColor(Color.WHITE);
        rectF.set(width * 0.22f , height * 0.21f , width * 0.78f ,height * 0.38f);//矩形设置四条边，这一步是邀请码部分所在的位置
        canvas.drawRoundRect(rectF,DENSITY*5,DENSITY*5, paint);//绘制了邀请码部分圆角矩形的白底

        //绘制“邀请码”3个字
        paint.setColor(Color.BLACK);
        paint.setTextSize((rectF.bottom - rectF.top)*0.2f);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("邀请码" ,width/2f , rectF.top  +  (rectF.bottom - rectF.top)*0.3f,paint);

        //绘制下方邀请码
        paint.setColor(Color.BLACK);
        paint.setTextSize((rectF.bottom - rectF.top)*0.3f);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text ,width/2f , rectF.top  +  (rectF.bottom - rectF.top)*0.8f,paint);

        //绘制二维码内容部分
        float top = rectF.bottom + (rectF.bottom - rectF.top) * 0.1f;
        paint.setColor(Color.WHITE);
        rectF.set(rectF.left , top , rectF.right , top + (rectF.right - rectF.left));//左右保持不变，设置顶部坐标后，计算底部坐标，使它成为一个矩形
        canvas.drawRoundRect(rectF,DENSITY*5,DENSITY*5, paint);//绘制了二维码部分圆角矩形的白底

        float padding = (rectF.right - rectF.left) * 0.1f;//此值为二维码鱼白色底之间的Padding
        rectF.set(rectF.left + padding , rectF.top + padding  , rectF.right - padding , rectF.bottom - padding);
        canvas.drawBitmap(bitmapCode , null,rectF,paint);

//        bitmapBg.recycle();
//        bitmapCode.recycle();
        return bitmap;
    }
}