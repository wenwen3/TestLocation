package com.project.testlocation;

import android.animation.TypeEvaluator;

//实现TypeEvaluator接口
public class PointEvaluator implements TypeEvaluator {

    //重写evaluate()方法
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {

        //始末值强转为Point对象
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;

        //通过fraction计算当前动画的坐标值x,y
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());

        //返回以上述x,y组装的新的Point对象
        Point point = new Point(x,y);
        return point;
    }
}