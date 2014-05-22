package com.kzone.service;

import com.kzone.bean.Comment;
import com.kzone.bean.KTV;
import com.mongodb.gridfs.GridFSDBFile;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Jeffy on 14-5-17
 */
public interface KTVService extends CommonService<KTV> {
    public void validateKTV(KTV ktv);

    public float countScore(List<Comment> commentList);
}
