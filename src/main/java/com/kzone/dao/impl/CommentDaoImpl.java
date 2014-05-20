package com.kzone.dao.impl;

import com.kzone.bean.Comment;
import com.kzone.dao.CommentDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Jeffy on 14-4-24.
 */
public class CommentDaoImpl extends CommonDaoImpl<Comment> implements CommentDao {
}
