package com.example.andres.piechart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * TODO: document your custom view class.
 */
public class MyView extends View {
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private String title;
    private String camp1;
    private String camp2;
    private int colcamp1;
    private int colcamp2;

    private float vlcmp1;
    private float vlcmp2;



    private RectF rect;
    private float percentage1;
    private float percentage2;

    private int clrtitle;
    private int clrcmp1;
    private int clrcmp2;

    private Paint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;


    private Paint circleColor;
    private Paint innerColor;
    private Paint circleRing;

    public MyView(Context context) {
        super(context);
        init(null, 0);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.MyView, defStyle, 0);
        //Textos
        title = a.getString(R.styleable.MyView_titulo);
        camp1 = a.getString(R.styleable.MyView_campo1);
        camp2 = a.getString(R.styleable.MyView_campo2);
        //valor de los campos
        vlcmp1 = a.getFloat(R.styleable.MyView_valorcmp1,0);
        vlcmp2 = a.getFloat(R.styleable.MyView_valorcmp2,0);
        //color quesitos
        clrcmp1 = a.getColor(R.styleable.MyView_colorcampo1,Color.BLUE);
        clrcmp2 = a.getColor(R.styleable.MyView_colorcampo2, Color.RED);
        //color letras campos
        colcamp1 = a.getColor(R.styleable.MyView_colorltscmp1,Color.BLUE);
        colcamp2 = a.getColor(R.styleable.MyView_colorcampo2,Color.RED);
        clrtitle = a.getColor(R.styleable.MyView_clrtitle,Color.BLACK);

        mTextHeight = a.getDimension(R.styleable.MyView_txtheight,0);
        mTextWidth = a.getDimension(R.styleable.MyView_txtwidth,0);


        circleColor = new Paint();
        innerColor = new Paint();
        circleRing = new Paint();

        circleColor.setColor(Color.TRANSPARENT);
        innerColor.setColor(clrcmp1);
        circleRing.setColor(clrcmp2);

        circleColor.setAntiAlias(true);
        innerColor.setAntiAlias(true);
        circleRing.setAntiAlias(true);

        circleColor.setStrokeWidth(50);
        innerColor.setStrokeWidth(50);
        circleRing.setStrokeWidth(50);

        circleColor.setStyle(Paint.Style.FILL);

        a.recycle();

    }

    private void invalidateTextPaintAndMeasurements() {

    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = getWidth();
        int height = getHeight();

        int size = (width > height) ? height : width;

        float cx = width / 2;
        float cy = (height / 2) - 40;
        float radius = (size / 2) - 30;

        float left = cx - radius;
        float right = cx + radius;
        float top = cy - radius;
        float bottom = cy + radius;

        RectF rect = new RectF(left, top, right, bottom);

        Log.d("MyTag", "Left: " + rect.left + "Right: " + rect.right + "Top: " + rect.top + "Bottom: " + rect.bottom);

        canvas.drawCircle(cx, cy, radius, circleRing);
        canvas.drawCircle(cx, cy, radius, innerColor);

        setPercentage();

        float xleyenda = cx/2;
        float yleyenda = cy*2;

        canvas.drawArc(rect, -90, (percentage2/100)*360, true, circleRing);



        super.onDraw(canvas);

        //Titulo
        TextPaint tit = new TextPaint();
        tit.setAntiAlias(true);
        tit.setColor(clrtitle);
        tit.setTextSize(mTextHeight);
        tit.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(title,getWidth()/2, getHeight()/8-mTextHeight, tit );

        //Leyenda letra campo1
        TextPaint cmp1 = new TextPaint();
        cmp1.setAntiAlias(true);
        cmp1.setColor(colcamp1);
        cmp1.setTextSize(35);
        canvas.drawText(camp1 +" = "+ vlcmp1, xleyenda, yleyenda, cmp1);

        //Leyenda leta campo2
        TextPaint cmp2 = new TextPaint();
        cmp2.setAntiAlias(true);
        cmp2.setColor(colcamp2);
        cmp2.setTextSize(35);
        canvas.drawText(camp2 +" = "+ vlcmp2, xleyenda, yleyenda+mTextHeight+20, cmp2);

        float cdrsize= 30;

        //rectangulo aprovados
        RectF rectaproved = new RectF(xleyenda-cdrsize-5,yleyenda-cdrsize,xleyenda-5,yleyenda);
        canvas.drawRect(rectaproved, innerColor);

        //rectangulo suspensos
        RectF rectsuspensos = new RectF(xleyenda-cdrsize-5,yleyenda-cdrsize+mTextHeight+20,xleyenda-5,yleyenda+mTextHeight+20);
        canvas.drawRect(rectsuspensos, circleRing);

        //Percent aproved
        TextPaint percentaprove = new TextPaint();
        percentaprove.setAntiAlias(true);
        percentaprove.setColor(Color.WHITE);
        percentaprove.setTextSize(mTextHeight);
        percentaprove.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(String.format("%.2f",percentage1) + "%",cx-radius/2, cy-radius/2, percentaprove );

        //Percent suspend
        TextPaint percentsuspens = new TextPaint();
        percentsuspens.setAntiAlias(true);
        percentsuspens.setColor(Color.WHITE);
        percentsuspens.setTextSize(mTextHeight);
        percentsuspens.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(String.format("%.2f",percentage2) + "%",cx+radius/2, cy-radius/2, percentaprove );


    }

    public void setPercentage() {
        this.percentage2 = vlcmp2/(vlcmp1+vlcmp2)*100;
        this.percentage1 = vlcmp1/(vlcmp1+vlcmp2)*100;

    }


    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
}
