package com.kzone.service.impl;

import com.kzone.bean.Comment;
import com.kzone.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jeffy on 14-5-17
 */
@Service
public class CommentServiceImpl extends CommonServiceImpl<Comment> implements CommentService {
    @Override
    public void validateComment(Comment comment) {
    }
}
