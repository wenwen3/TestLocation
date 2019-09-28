package com.project.testlocation;

public class Point {
    //记录坐标位置
    private float x;
    private float y;

    //通过构造方法设置坐标，因此不需要额外的set方法
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    //get方法，获取当前坐标值
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
