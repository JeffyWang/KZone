package com.kzone.dao;

import java.io.InputStream;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.data.mongodb.core.query.Update;

public interface MongoDao {
    public void save(Object objectToSave, String collectionName);

    public void delete(Object obj, String collectionName);

    public void update(Query query, Update update, Class entityClass);

    public List<Object> find(Query query, Class clazz, String collectionName);
	
	public void store(InputStream inputStream, String fileName, String contentType, Object obj);
	
	public void deleteFile(Query query);
	
	public List<GridFSDBFile> find(Query query);
}
