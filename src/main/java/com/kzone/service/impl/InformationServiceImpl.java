package com.kzone.service.impl;

import com.kzone.bean.Information;
import com.kzone.bo.Article;
import com.kzone.constants.MongoConstants;
import com.kzone.constants.ParamsConstants;
import com.kzone.dao.MongoDao;
import com.kzone.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jeffy on 14-5-17
 */
@Service
public class InformationServiceImpl extends CommonServiceImpl<Information> implements InformationService {
    @Autowired
    private MongoDao mongoDao;

    @Override
    public void validateInformation(Information information) {
    }

    @Override
    public void addArticle(int informationId, String article) {
        Article art = new Article(informationId, article);
        mongoDao.save(art, MongoConstants.MONGO_COLLECTION_INFORMATION);
    }

    @Override
    public Article getArticle(int informationId) {
        Article article = (Article) mongoDao.find(new Query().addCriteria(Criteria.where(ParamsConstants.PARAM_MONGO_ARTICLE_INFORMATION_ID).is(informationId)), Article.class, MongoConstants.MONGO_COLLECTION_INFORMATION).get(0);
        return article;
    }
}
