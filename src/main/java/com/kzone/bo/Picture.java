package com.kzone.bo;

import java.util.List;
import java.util.Map;

/**
 * Created by Jeffy on 14-5-17
 */
public class Picture {
    private List<String> smallPictures;
    private List<String> middlePictures;
    private List<String> bigPictures;

    public List<String> getSmallPictures() {
        return smallPictures;
    }

    public void setSmallPictures(List<String> smallPictures) {
        this.smallPictures = smallPictures;
    }

    public List<String> getMiddlePictures() {
        return middlePictures;
    }

    public void setMiddlePictures(List<String> middlePictures) {
        this.middlePictures = middlePictures;
    }

    public List<String> getBigPictures() {
        return bigPictures;
    }

    public void setBigPictures(List<String> bigPictures) {
        this.bigPictures = bigPictures;
    }
}
