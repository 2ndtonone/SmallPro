package com.Funcgo.Outline.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.Funcgo.Outline.R;

/**
 * Created by lenovo on 2017/6/24.
 */

public class MyCircleProgressBar extends ProgressBar {
    private static final int CIRCLE_RING_TEXT_COLOR = 0xfff2f2f2;//文本默认颜色为黑色
    private static final String CIRCLE_RING_TEXT = "剩余时间";//默认文本
    private static final String CIRCLE_RING_UNIT = "天";//默认单位
    private static final int CIRCLE_RING_WIDTH = 5;//圆环的默认宽度

    private static final int CIRCLE_RING_BACKGROUND_COLOR = 0xfff2f2f2;//默认背景圆环颜色为灰色
    private static final int CIRCLE_RING_FOREGROUND_COLOR = 0xff00ffff;//默认前景圆环颜色为青色
    private static final int CIRCLE_RING_VALUES_COLOR = 0xfff2f2f2;//值的颜色默认为红色
    /**
     * 声明变量
     */
    private int circle_ring_Side;//包含圆环容器的边长
    private int circle_ring_radius;//圆环的半径
    private int draw_offset;//因为画笔的宽度产生的误差
    private int text_color;//文本的颜色
    private int values_color;//值得颜色
    private String text;//内容
    private String unit;//单位
    private int ring_width;//圆环的宽度
    private int ring_back_color;//默认的颜色
    private int ring_fore_color;//展示进度的颜色
    private Paint textPaint, backPaint, forePaint, testPaint;//画笔
    private RectF drawArcRect;//绘制弧形的区域
    private String lessDay = "0";

    /**
     * @param context
     */
    public MyCircleProgressBar(Context context) {
        this(context, null);
    }

    public MyCircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttributes(attrs);//获取并设置自定义属性
        init();//初始化
        initPaints();//初始化画笔
    }


    /**
     * 获取自定义属性
     *
     * @param attrs
     */
    private void obtainAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs, R.styleable.CircleProgressBar);
        text = typedArray.getString(R.styleable.CircleProgressBar_circle_ring_text);
        text = text == null ? CIRCLE_RING_TEXT : text;
        unit = typedArray.getString(R.styleable.CircleProgressBar_circle_ring_unit);
        unit = unit == null ? CIRCLE_RING_UNIT : unit;
        values_color = typedArray.getColor(
                R.styleable.CircleProgressBar_circle_ring_values_color,CIRCLE_RING_VALUES_COLOR);
        text_color = typedArray.getColor(
                R.styleable.CircleProgressBar_circle_ring_text_color, CIRCLE_RING_TEXT_COLOR);
        ring_width = dip2px((int) typedArray.getDimension(
                R.styleable.CircleProgressBar_circle_ring_width, CIRCLE_RING_WIDTH));
        ring_back_color = typedArray.getColor(
                R.styleable.CircleProgressBar_circle_ring_background_color, CIRCLE_RING_BACKGROUND_COLOR);
        ring_fore_color = typedArray.getColor(
                R.styleable.CircleProgressBar_circle_ring_foreground_color, CIRCLE_RING_FOREGROUND_COLOR);
        typedArray.recycle();
    }

    /**
     * 初始化
     */
    private void init() {
        draw_offset = ring_width / 2 + dip2px(2);//偏移量
        drawArcRect = new RectF();//绘制弧形的区域
    }

    /**
     * 初始化画笔
     */
    private void initPaints() {
//        //测试画笔
//        testPaint = new Paint();
//        testPaint.setStrokeWidth(2);
//        testPaint.setColor(Color.BLACK);
//        testPaint.setStyle(Paint.Style.STROKE);
//        testPaint.setAntiAlias(true);
        //绘制默认圆环的画笔
        backPaint = new Paint();//用于绘制默认的圆环
        backPaint.setColor(ring_back_color);//画笔颜色
        backPaint.setAntiAlias(true);//抗锯齿
        backPaint.setDither(true);//防抖动
        backPaint.setStrokeWidth(ring_width);//设置笔刷宽度
        backPaint.setStyle(Paint.Style.STROKE);//不填充
        //绘制进度的画笔
        forePaint = new Paint();//用于绘制进度的画笔
        forePaint.setColor(ring_fore_color);
        forePaint.setStrokeWidth(ring_width);
        forePaint.setAntiAlias(true);
        forePaint.setDither(true);
        forePaint.setStyle(Paint.Style.STROKE);
        forePaint.setStrokeCap(Paint.Cap.ROUND);//笔刷边界为圆形
        //绘制文本的画笔
        textPaint = new Paint();//绘制文字
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setStyle(Paint.Style.FILL);//填充
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);//文字粗体
        textPaint.setTextAlign(Paint.Align.CENTER);//文字居中
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(0xffffffff);
        circle_ring_radius = circle_ring_Side / 2 - draw_offset;//半径
        //绘制默认的圆环
        canvas.drawCircle(circle_ring_Side / 2, circle_ring_Side / 2, circle_ring_radius, backPaint);
        //绘制弧形
        drawArcRect.left = draw_offset;
        drawArcRect.top = draw_offset;
        drawArcRect.right = circle_ring_Side - draw_offset;
        drawArcRect.bottom = circle_ring_Side - draw_offset;
        canvas.drawArc(drawArcRect, 90, getProgress() * 360 / getMax(), false, forePaint);
        //绘制文本的区域
        int chord_length = (int) Math.sqrt(Math.pow(circle_ring_Side, 2)
                + Math.pow(circle_ring_Side, 2));//获取矩形的对角线长度
        int draw_text_left = (int) ((chord_length / 2 - circle_ring_radius) * Math.sin(45));
        int draw_text_top = draw_text_left;
        int draw_text_right = circle_ring_Side - draw_text_left;
        int draw_text_bottom = circle_ring_Side - draw_text_top;
//        canvas.drawRect(draw_text_left, draw_text_top, draw_text_right, draw_text_bottom, testPaint);
        //绘制文本
        int text_4_side = Math.abs(draw_text_bottom - draw_text_top) / 3;//绘制区域的1/4份
        //固定提示文本
        textPaint.setColor(text_color);
        textPaint.setTextSize((float) (text_4_side * 0.6));
        canvas.drawText(text, circle_ring_Side / 2, draw_text_top + text_4_side, textPaint);
        //固定单位文本
        canvas.drawText(unit,circle_ring_Side/2+circle_ring_Side / 6,
                circle_ring_Side/2 + text_4_side,textPaint);
        //变换的数值
        textPaint.setColor(values_color);
        textPaint.setTextSize((float) (text_4_side)*1.5f);
        canvas.drawText(lessDay,circle_ring_Side *  0.4f,
                circle_ring_Side/2 + text_4_side,textPaint);
    }

    /**
     * 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);//测量宽度
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);//测量高度
        circle_ring_Side = Math.min(widthSize, heightSize);//取最小值
        setMeasuredDimension(circle_ring_Side, circle_ring_Side);//设置包含圆环的容器为正方形
    }

    /**
     * dp-->px
     *
     * @param decisionType
     * @return
     */
    private int dip2px(int decisionType) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                decisionType, getResources().getDisplayMetrics());
    }

    public void setLessDay(String lessDay) {
        this.lessDay = lessDay;
    }
}
