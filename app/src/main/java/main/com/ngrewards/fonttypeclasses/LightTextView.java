package main.com.ngrewards.fonttypeclasses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by technorizen on 14/6/18.
 */

public class LightTextView extends androidx.appcompat.widget.AppCompatTextView {


    public LightTextView(Context context) {
        super(context);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "raleway_light.ttf");
        this.setTypeface(tf);
    }

    public LightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "raleway_light.ttf");
        this.setTypeface(tf);
    }

    public LightTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "raleway_light.ttf");
        this.setTypeface(tf);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }

}