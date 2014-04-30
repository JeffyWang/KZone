package com.kzone.dao;

import java.io.InputStream;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.gridfs.GridFSDBFile;

public interface MongoDao {
	
	public void store(InputStream inputStream, String fileName, String contentType, Object obj);
	
	public void deleteFile(Query query);
	
	public List<GridFSDBFile> find(Query query);
}
