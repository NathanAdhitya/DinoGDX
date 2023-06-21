package com.pbogdxproject.entities;

public class Offset2D {
    public float top, bottom, right, left;

    public Offset2D(){
        top = 0;
        bottom = 0;
        right = 0;
        left = 0;
    }

    public Offset2D(float top, float bottom, float right, float left){
        this.top = top;
        this.bottom = bottom;
        this.right = right;
        this.left = left;
    }
}
