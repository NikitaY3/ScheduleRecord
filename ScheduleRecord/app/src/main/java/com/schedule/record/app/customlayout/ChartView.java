package com.schedule.record.app.customlayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.schedule.record.app.R;

import java.util.LinkedList;
import java.util.Objects;

/**
 * 折线图
 */
public class ChartView extends SurfaceView implements SurfaceHolder.Callback , Runnable
{
    private Context mContext;
    private Paint mPaint;
    private DisplayMetrics dm;

    private int canvasWidth;
    private int bHeight = 0;
    private int bWidth;
    private boolean isMeasure = true;
    private boolean canScrollRight = true;
    private boolean canScrollLeft = true;

    //y轴最大值
    private int maxValue;
    //y轴间隔值
    private int averageValue;
    private int marginTop = 20;
    private int marginBottom = 80;

    //曲线上的总点数
    private Point[] mPoints;
    //纵坐标值
    private LinkedList<Double> yRawData;
    //横坐标值
    private LinkedList<String> xRawData;
    //根据间隔计算出的每个X的值
    private LinkedList<Integer> xList = new LinkedList<>();
    private LinkedList<String> xPreData = new LinkedList<>();
    private LinkedList<Double> yPreData = new LinkedList<>();

    private LinkedList<String> xLastData = new LinkedList<>();
    private LinkedList<Double> yLastData = new LinkedList<>();
    private int spacingHeight;

    private SurfaceHolder holder;
    private boolean isRunning = true;
    private int lastX;
    private int offSet;

    private int xAverageValue = 0;


    public ChartView(Context context)
    {
        this(context , null);
    }

    public ChartView(Context context , AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView()
    {
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Objects.requireNonNull(wm).getDefaultDisplay().getMetrics(dm);

        xPreData.add("05-18");
        xPreData.add("05-17");
        xPreData.add("05-16");
        xPreData.add("05-15");
        xPreData.add("05-14");
        xPreData.add("05-13");

        yPreData.add(4.53);
        yPreData.add(3.45);
        yPreData.add(6.78);
        yPreData.add(5.21);
        yPreData.add(2.34);
        yPreData.add(6.32);

        xLastData.add("05-26");
        xLastData.add("05-27");
        xLastData.add("05-28");
        xLastData.add("05-29");
        xLastData.add("05-30");
        xLastData.add("05-31");

        yLastData.add(2.35);
        yLastData.add(5.43);
        yLastData.add(6.23);
        yLastData.add(7.33);
        yLastData.add(3.45);
        yLastData.add(2.45);

        holder = this.getHolder();
        holder.addCallback(this);
    }

    @Override
    protected void onSizeChanged(int w , int h , int oldW , int oldH)
    {
        if (isMeasure)
        {
            int canvasHeight = getHeight();
            this.canvasWidth = getWidth();
            if (bHeight == 0)
            {
                bHeight = canvasHeight - marginBottom;
            }
            bWidth = dip2px(30);
            xAverageValue = (canvasWidth - bWidth) / 7;
            isMeasure = false;
        }
    }


    @Override
    public void run()
    {
        while (isRunning)
        {
            drawView();
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void drawView()
    {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        mPaint.setColor(getResources().getColor(R.color.gray2));
        drawAllXLine(canvas);
        Rect mRect = new Rect(bWidth - 3, marginTop - 5,
                bWidth + (canvasWidth - bWidth) / yRawData.size() * (yRawData.size() - 1) + 3, bHeight + marginTop + marginBottom);
        //锁定画图区域
        canvas.clipRect(mRect);
        drawAllYLine(canvas);

        mPoints = getPoints();

        mPaint.setColor(getResources().getColor(R.color.my6));
        mPaint.setStrokeWidth(dip2px(2.5f));
        mPaint.setStyle(Paint.Style.STROKE);
        drawLine(canvas);

        mPaint.setStyle(Paint.Style.FILL);
        int i = 0 ;
        while (i < mPoints.length) {
            canvas.drawCircle(mPoints[i].x , mPoints[i].y , 5 , mPaint);
            i++;
        }

        holder.unlockCanvasAndPost(canvas);
    }

    //绘制折线图
    private void drawLine(Canvas canvas)
    {
        Point startP, endP ;
        for (int i = 0 ; i < mPoints.length - 1; i++)
        {
            startP = mPoints[i];
            endP = mPoints[i + 1];
            canvas.drawLine(startP.x , startP.y , endP.x , endP.y , mPaint);
        }
    }

    //绘制所有的纵向分割线
    private void drawAllYLine(Canvas canvas)
    {
        for (int i = 0 ; i < yRawData.size() ; i++)
        {
            if (i == 0)
            {
                canvas.drawLine(bWidth, marginTop , bWidth, bHeight + marginTop , mPaint);
            }
            if (i == yRawData.size() - 1)
            {
                canvas.drawLine(bWidth + xAverageValue * i, marginTop , bWidth + xAverageValue * i , bHeight + marginTop , mPaint);
            }
            xList.add(bWidth + xAverageValue * i);
            canvas.drawLine(bWidth + xAverageValue * i + offSet, marginTop , bWidth + xAverageValue * i + offSet , bHeight + marginTop , mPaint);
            drawText(xRawData.get(i) , bWidth + xAverageValue * i - 30 + offSet, bHeight + dip2px(26) , canvas);

        }
    }

    //绘制所有的横向分割线
    private void drawAllXLine(Canvas canvas)
    {
        for (int i = 0 ; i < spacingHeight + 1 ; i++)
        {
            canvas.drawLine(bWidth , bHeight - (bHeight / spacingHeight) * i + marginTop ,
                    bWidth + xAverageValue * (yRawData.size() - 1) , bHeight - (bHeight / spacingHeight) * i + marginTop , mPaint);
            drawText(String.valueOf(averageValue * i) , bWidth / 2 , bHeight - (bHeight / spacingHeight) * i + marginTop, canvas);
        }
    }

    //绘制坐标值
    private void drawText(String text , int x , int y , Canvas canvas)
    {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextSize(dip2px(12));
        p.setColor(getResources().getColor(R.color.gray3));
        p.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text , x , y , p);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        new Thread(this).start();
        Log.d("OOK" , "Created");
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2)
    {
        Log.d("OOK" , "Changed");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder)
    {
        isRunning = false;
        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        int rawX = (int) event.getX();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                lastX = rawX;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = rawX - lastX;
                if (xPreData.size() == 0 && offSet > 0)
                {
                    offSet = 0;
                    canScrollRight = false;
                }
                if (xLastData.size() == 0 && offSet < 0)
                {
                    offSet = 0;
                    canScrollLeft = false;
                }
                offSet = offSet + offsetX;
                if (offSet > xAverageValue && canScrollRight)
                {
                    offSet = offSet % xAverageValue;
                    xRawData.addFirst(xPreData.pollFirst());
                    yRawData.addFirst(yPreData.pollFirst());
                    xLastData.addFirst(xRawData.removeLast());
                    yLastData.addFirst(yRawData.removeLast());
                    canScrollLeft = true;
                }


                if (offSet < -xAverageValue && canScrollLeft)
                {
                    offSet = offSet % xAverageValue;
                    xRawData.addLast(xLastData.pollFirst());
                    yRawData.addLast(yLastData.pollFirst());
                    xPreData.addFirst(xRawData.removeFirst());
                    yPreData.addFirst(yRawData.removeFirst());
                    canScrollRight = true;
                }
                lastX = rawX;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private Point[] getPoints()
    {
        Point[] points = new Point[yRawData.size()];
        for (int i = 0 ; i < yRawData.size() ; i++)
        {
            int ph = bHeight - (int)(bHeight * (yRawData.get(i) / maxValue));

            points[i] = new Point(xList.get(i) + offSet , ph + marginTop);
        }
        return points;
    }

    public void setData(LinkedList<Double> yRawData , LinkedList<String> xRawData , int maxValue , int averageValue)
    {
        this.maxValue = maxValue;
        this.averageValue = averageValue;
        this.mPoints = new Point[yRawData.size()];
        this.yRawData = yRawData;
        this.xRawData = xRawData;
        this.spacingHeight = maxValue / averageValue;
    }

    private int dip2px(float dpValue)
    {
        return (int) (dpValue * dm.density + 0.5f);
    }
}
