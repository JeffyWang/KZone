package com.kzone.bo;

import java.util.List;
import java.util.Map;

/**
 * Created by Jeffy on 14-5-17
 */
public class Picture {
    private List<String> mobileSmallPictures;
    private List<String> mobileBigPictures;
    private List<String> commonSmallPictures;
    private List<String> commonBigPictures;

    public List<String> getMobileSmallPictures() {
        return mobileSmallPictures;
    }

    public void setMobileSmallPictures(List<String> mobileSmallPictures) {
        this.mobileSmallPictures = mobileSmallPictures;
    }

    public List<String> getMobileBigPictures() {
        return mobileBigPictures;
    }

    public void setMobileBigPictures(List<String> mobileBigPictures) {
        this.mobileBigPictures = mobileBigPictures;
    }

    public List<String> getCommonSmallPictures() {
        return commonSmallPictures;
    }

    public void setCommonSmallPictures(List<String> commonSmallPictures) {
        this.commonSmallPictures = commonSmallPictures;
    }

    public List<String> getCommonBigPictures() {
        return commonBigPictures;
    }

    public void setCommonBigPictures(List<String> commonBigPictures) {
        this.commonBigPictures = commonBigPictures;
    }
}
