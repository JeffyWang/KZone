package com.kzone.service;

import com.kzone.bean.Information;
import com.kzone.bo.Article;

/**
 * Created by Jeffy on 14-5-17
 */
public interface InformationService extends CommonService<Information> {
    public void validateInformation(Information information);

    public void addArticle(int informationId,String article);

    public Article getArticle(int informationId);
}
