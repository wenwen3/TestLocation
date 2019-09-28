package com.project.testlocation;

public class Utils  {

    private static Utils utils;

    public static Utils getInstance(){
        if( utils == null ){
            utils = new Utils();
        }
        return utils;
    }

    private int minute;

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
