package com.emiancang.emiancang.base;

/**
 * Created by wuxu on 15/5/16.
 */
public class SelectImagInfo {
    String imagePath;
    int type;

    public void setType(int type) {
        this.type = type;
    }
    public int getType(){
        return type;
    }

    public SelectImagInfo(String imagePath, int type){
        this.imagePath = imagePath;this.type =type;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
