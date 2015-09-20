package com.google.fpl.liquidfunpaint;

import android.view.View;

/**
 * Created by abc on 15-09-19.
 */
public interface ILiquidWorld {

    void pausePhysics();
    void resumePhysics();

    void createSolidShape(float[] vertices);
    void emptyShape(float[] vertices);
    void createLiquidShape(float[] vertices);

    void setOnTouchListener(View.OnTouchListener listener);
}