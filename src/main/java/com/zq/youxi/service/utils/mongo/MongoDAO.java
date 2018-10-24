package com.zq.youxi.service.utils.mongo;


import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.List;

/**
 * Mongo数据访问接口，定义了mongo访问的通用方法，包括增删改查等操作。
 */
public interface MongoDAO {

    /**
     * 添加一个文档
     *
     * @param collectionName 数据集名称
     * @param doc            添加的文档
     */
    public void insertOne(String collectionName, Document doc) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 添加多个文档
     *
     * @param collectionName 数据集名称
     * @param docs           文档列表
     */
    public void insertMany(String collectionName, List<Document> docs) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 添加多个文档
     *
     * @param collectionName 数据集名称
     * @param docs           文档列表
     */
    public void insertMany(String collectionName, List<Document> docs, InsertManyOptions options) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 删除一个文档
     *
     * @param collectionName 数据集名称
     * @param doc            删除文档的条件
     * @return
     */
    public DeleteResult deleteOne(String collectionName, Document doc) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 删除多个文档
     *
     * @param collectionName 数据集名称
     * @param doc            删除文档的条件
     * @return
     */
    public DeleteResult deleteMany(String collectionName, Document doc) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;


    /**
     * 更新一个文档
     *
     * @param collectionName 数据集名称
     * @param condition      需要更新文档的过滤条件
     * @param updateDoc
     */
    public UpdateResult updateOne(String collectionName, Document condition, Document updateDoc, String instruct) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 更新一个文档
     *
     * @param collectionName 数据集名称
     * @param condition      需要更新文档的过滤条件
     * @param updateDoc
     */
    public UpdateResult updateOne(String collectionName, Document condition, Document updateDoc, String instruct, boolean options) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 更新一个文档
     *
     * @param collectionName 数据集名称
     * @param condition      需要更新文档的过滤条件
     * @param updateDoc
     */
    public UpdateResult updateOne(String collectionName, Document condition, Document updateDoc) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 更新一个文档
     *
     * @param collectionName 数据集名称
     * @param condition      需要更新文档的过滤条件
     * @param updateDoc
     * @param options        没有更新文档，是否需要新建
     */
    public UpdateResult updateOne(String collectionName, Document condition, Document updateDoc, boolean options) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 更新一个文档
     *
     * @param collectionName 数据集名称
     * @param condition      需要更新文档的过滤条件
     * @param updateDoc
     */
    public UpdateResult updateMany(String collectionName, Document condition, Document updateDoc, String instruct) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 更新一个文档
     *
     * @param collectionName 数据集名称
     * @param condition      需要更新文档的过滤条件
     * @param updateDoc
     */
    public UpdateResult updateMany(String collectionName, Document condition, Document updateDoc) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 通过_id查找文档
     *
     * @param collectionName
     * @param _id
     * @return
     * @throws MongoException
     */
    public Document findOneByID(String collectionName, long _id) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 查找一个文档
     *
     * @param collectionName 数据集名称
     * @param condition      需要查找文档的过滤条件
     * @return
     */
    public Document findOne(String collectionName, Document condition) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 查找一个文档
     *
     * @param collectionName 数据集名称
     * @param condition      需要查找文档的过滤条件
     * @return
     */
    public Document findOne(String collectionName, Document condition, Document projection) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 判断某个条件下是否存在文档
     *
     * @param collectionName
     * @param condition
     * @return
     */
    public boolean isExist(String collectionName, Document condition) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 查找多个文档
     *
     * @param collectionName 数据集名称
     * @param condition      需要查找文档的过滤条件
     * @return
     */
    public List<Document> find(String collectionName, Document condition) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 分页查找;每次先记住上次的最后一条记录条件，再根据条件过滤，取top limit
     *
     * @param collectionName 数据集名称
     * @param condition      需要查找文档的过滤条件
     * @return
     */
    public List<Document> find(String collectionName, Document condition, int limit) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 分页查找;跳过Top条数，获取制定limit条数信息
     *
     * @param collectionName 数据集名称
     * @param condition      需要查找文档的过滤条件
     * @return
     */
    public List<Document> find(String collectionName, Document condition, Document sort, int skipNum, int limit) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 分页查找;每次先记住上次的最后一条记录条件，再根据条件过滤，取top limit
     *
     * @param collectionName 数据集名称
     * @param condition      需要查找文档的过滤条件
     * @param sort           排序条件
     * @param limit
     * @return
     */
    public List<Document> find(String collectionName, Document condition, Document sort, int limit) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 查询多个文档，并且限制返回文档的字段
     *
     * @param collectionName
     * @param condition
     * @param projection     限制返回文档字段
     * @param limit
     * @return
     * @throws MongoException
     */
    public List<Document> findWithProjection(String collectionName, Document condition, Document projection, int limit) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 生成唯一ID
     *
     * @param channelName 渠道名称
     * @return
     */
    public long fetchId(String channelName) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 生成唯一ID
     *
     * @param channelName 渠道名称
     * @param step
     * @return
     */
    public long fetchId(String channelName, int step) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 初始化Seq ID
     *
     * @param channelName
     * @param initSeqId
     * @throws MongoException
     */
    public void initSeqId(String channelName, long initSeqId) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 聚合查询,select count(*)
     *
     * @param collectionName
     * @return
     */
    public long count(String collectionName) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 聚合查询，select count(*) from table where condition
     *
     * @param collectionName
     * @param condition
     * @return
     */
    public long count(String collectionName, Document condition) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 不限制返回记录个数，慎用
     *
     * @param collectionName
     * @param condition
     * @return
     * @throws MongoException
     */
    public FindIterable<Document> findMany(String collectionName, Document condition) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 查找并更新字段 $set
     *
     * @param collectionName
     * @param condition
     * @param updateDoc
     * @return
     * @throws MongoException
     */
    public Document findOneAndUpdate(String collectionName, Document condition, Document updateDoc) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 查找并更新
     *
     * @param collectionName
     * @param condition
     * @param updateDoc
     * @param instruct
     * @return
     * @throws MongoException
     */
    public Document findOneAndUpdate(String collectionName, Document condition, Document updateDoc, String instruct) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;

    /**
     * 不限制返回记录个数，慎用
     *
     * @param collectionName
     * @param condition
     * @param sort
     * @return
     * @throws MongoException
     */
    public FindIterable<Document> findMany(String collectionName, Document condition, Document sort) throws MongoException, com.zq.youxi.service.utils.mongo.MongoException;
}
