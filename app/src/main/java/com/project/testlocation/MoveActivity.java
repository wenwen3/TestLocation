package com.project.testlocation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MoveActivity extends AppCompatActivity {

    private int lastY;
    private ImageView image;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.move_layout);
        image = findViewById(R.id.twoImage);
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int y = (int) event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int offsetY = y - lastY;
                        if(image.getTop() + offsetY < getResources().getDimensionPixelSize(R.dimen.padding_20) && y - lastY < 0 ){
                            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) image.getLayoutParams();
                            layoutParams2.topMargin = getResources().getDimensionPixelSize(R.dimen.padding_20);
                            image.setLayoutParams(layoutParams2);
                            return true;
                        }
                        if(image.getTop() + offsetY > getResources().getDimensionPixelSize(R.dimen.padding_180) && y - lastY > 0 ){
                            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) image.getLayoutParams();
                            layoutParams2.topMargin = getResources().getDimensionPixelSize(R.dimen.padding_180);
                            image.setLayoutParams(layoutParams2);
                            return true;
                        }
                        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) image.getLayoutParams();
                        layoutParams2.topMargin = image.getTop() + offsetY;
                        image.setLayoutParams(layoutParams2);
                        break;
                        case MotionEvent.ACTION_UP:
                            RelativeLayout.LayoutParams layoutParams_up = (RelativeLayout.LayoutParams) image.getLayoutParams();
                            final int dp_20 = getResources().getDimensionPixelSize(R.dimen.padding_20);
                            final int dp_180 = getResources().getDimensionPixelSize(R.dimen.padding_180);
                            int topMargin = layoutParams_up.topMargin;
                            if(topMargin != dp_20 && topMargin != dp_180){
                                if(topMargin < ((dp_180 + dp_20) / 2)){
                                    // 创建初始动画的对象  & 结束动画的对象
                                    Point point1 = new Point(0, 0);
                                    Point point2 = new Point(0, dp_20-topMargin);
                                    final ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(),point1,point2);
                                    //组合动画
                                    anim.setDuration(2000);
                                     //设置动画时间
                                    anim.start(); //启动
//                                    final TranslateAnimation scaleAnimation = new TranslateAnimation(0, 0,0,dp_20 - topMargin);
////                                    //动画持续时间
//                                    scaleAnimation.setDuration(200);
//                                    scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
//                                        @Override
//                                        public void onAnimationStart(Animation animation) {
//                                        }
//
//                                        @Override
//                                        public void onAnimationEnd(Animation animation) {
//                                            scaleAnimation.cancel();//解决界面闪动的关键代码
//                                            RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) image.getLayoutParams();
//                                            layoutParams4.topMargin = dp_20;
//                                            image.setLayoutParams(layoutParams4);
//                                        }
//
//                                        @Override
//                                        public void onAnimationRepeat(Animation animation) {
//
//                                        }
//                                    });
//                                    image.startAnimation(scaleAnimation);

                                }else{
                                    final TranslateAnimation scaleAnimation = new TranslateAnimation(0, 0, 0, dp_180 - topMargin);
                                    scaleAnimation.setDuration(200);
                                    scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {
                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            scaleAnimation.cancel();//解决界面闪动的关键代码
                                            RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) image.getLayoutParams();
                                            layoutParams4.topMargin =dp_180;
                                            image.setLayoutParams(layoutParams4);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    });
                                    image.startAnimation(scaleAnimation);

                                }
                            }
                            break;
                }
                return true;
            }
        });
    }

}
