package com.kzone.dao.impl;

import com.kzone.dao.MongoDao;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class MongoDaoImpl implements MongoDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private GridFsTemplate gridTemplate;

	// /**
	// * 保存，若存在则自动更新
	// */
	// public void save(Object objectToSave) {
	// log.info("mongoDb保存对象:"+objectToSave);
	// mongoTemplate.save(objectToSave);
	// }

	/**
	 * 保存，若存在则自动更新
	 *
	 * @param objectToSave
	 * @param collectionName
	 */
	public void save(Object objectToSave, String collectionName) {
		mongoTemplate.save(objectToSave, collectionName);
	}

	public void delete(Object obj, String collectionName) {
	    mongoTemplate.remove(obj, collectionName);
	}


	public void update(Query query, Update update, Class entityClass) {
	    mongoTemplate.updateFirst(query, update, entityClass);
	}

	/**
	 * @category mogoDb中fs.files中结构关键项说明<br>
	 * <p>
	 * filename:附件文件名（不包含后缀名）
	 * contentType:附件文件格式（后缀名）
	 * metadata中pkg_pathname子项：文件全路径（文件路径+文件名+文件后缀名）
	 * </p>
	 */
	public void store(InputStream inputStream, String fileName, String contentType, Object obj) {
		GridFsOperations gridOperations = (GridFsOperations) gridTemplate;
		gridOperations.store(inputStream, fileName, contentType, obj);
	}

	public void deleteFile(Query query) {
		GridFsOperations gridOperations = (GridFsOperations)gridTemplate;
		gridOperations.delete(query);
	}

	public List<GridFSDBFile> find(Query query) {
		GridFsOperations gridOperations = (GridFsOperations)gridTemplate;
		return gridOperations.find(query);
	}

}
