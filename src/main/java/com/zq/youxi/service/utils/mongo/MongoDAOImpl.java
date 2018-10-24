package com.zq.youxi.service.utils.mongo;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang3.Validate;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MongoDAOImpl implements MongoDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDAOImpl.class);
    private static final String sequence_table = SysConfig.getPro("sequence_table");

    public boolean isExist(String collectionName, Document condition) throws MongoException {
        try {
            Document projection = new Document();
            projection.put("_id", 1);
            Document doc = findOne(collectionName, condition, projection);
            if (doc == null) return false;
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }

    public void insertOne(String collectionName, Document doc) throws MongoException {
        try {
            Validate.notNull(doc, "doc to remove cannot be null or empty");
            Validate.notBlank(collectionName, "collectionName cannot be null or empty");
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(collectionName);
            collection.insertOne(doc);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }

    public void insertMany(String collectionName, List<Document> docs) throws MongoException {
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(collectionName);
            collection.insertMany(docs);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }

    public void insertMany(String collectionName, List<Document> docs, InsertManyOptions options) throws MongoException {
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(collectionName);
            collection.insertMany(docs, options);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }

    public DeleteResult deleteOne(String collectionName, Document doc) throws MongoException {
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(collectionName);
            return collection.deleteOne(doc);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }

    public DeleteResult deleteMany(String collectionName, Document doc) throws MongoException {
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(collectionName);
            return collection.deleteMany(doc);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }

    public UpdateResult updateOne(String collectionName, Document condition, Document updateDoc) throws MongoException {
        return updateOne(collectionName, condition, updateDoc, false);
    }

    public UpdateResult updateOne(String collectionName, Document condition, Document updateDoc, boolean options) throws MongoException {
        return updateOne(collectionName, condition, updateDoc, "$set", options);
    }

    public UpdateResult updateOne(String collectionName, Document condition, Document updateDoc, String instruct) throws MongoException {
        return updateOne(collectionName, condition, updateDoc, instruct, false);
    }

    public UpdateResult updateOne(String collectionName, Document condition, Document updateDoc, String instruct, boolean options) throws MongoException {
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(collectionName);
            Document updateFind = new Document(instruct, updateDoc);
            return collection.updateOne(condition, updateFind, new UpdateOptions().upsert(options));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }

    public UpdateResult updateMany(String collectionName, Document condition, Document updateDoc, String instruct) throws MongoException {
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(collectionName);
            Document updateFind = new Document(instruct, updateDoc);
            return collection.updateMany(condition, updateFind);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }

    public UpdateResult updateMany(String collectionName, Document condition, Document updateDoc) throws MongoException {
        return updateMany(collectionName, condition, updateDoc, "$set");
    }

    public Document findOneByID(String collectionName, long _id) throws MongoException {
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(collectionName);
            Document condition = new Document();
            condition.put("_id", _id);
            FindIterable<Document> iterable = collection.find(condition);
            return iterable.first();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }

    public Document findOne(String collectionName, Document condition) throws MongoException {
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(collectionName);
            FindIterable<Document> iterable = collection.find(condition);
            return iterable.first();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }

    public Document findOne(String collectionName, Document condition, Document projection) throws MongoException {
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(collectionName);
            FindIterable<Document> iterable = collection.find(condition);
            iterable = iterable.projection(projection);
            return iterable.first();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }

    public List<Document> find(String collectionName, Document condition) throws MongoException {
        return find(collectionName, condition, 10);
    }

    public List<Document> find(String collectionName, Document condition, int limit) throws MongoException {
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(collectionName);
            FindIterable<Document> iterable = collection.find(condition).limit(limit);
            List<Document> list = new ArrayList<Document>(limit);
            MongoCursor<Document> cursor = iterable.iterator();
            while (cursor.hasNext()) {
                list.add(cursor.next());
            }
            return list;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }

    public List<Document> find(String collectionName, Document condition, Document sort, int limit) throws MongoException {
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(collectionName);
            FindIterable<Document> iterable = collection.find(condition).sort(sort).limit(limit);
            List<Document> list = new ArrayList<Document>(limit);
            MongoCursor<Document> cursor = iterable.iterator();
            while (cursor.hasNext()) {
                list.add(cursor.next());
            }
            return list;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }


    public List<Document> findWithProjection(String collectionName, Document condition, Document projection, int limit) throws MongoException{
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(collectionName);
            FindIterable<Document> iterable = collection.find(condition).limit(limit);
            iterable.projection(projection);
            List<Document> list = new ArrayList<Document>(limit);
            MongoCursor<Document> cursor = iterable.iterator();
            while (cursor.hasNext()) {
                list.add(cursor.next());
            }
            return list;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }

    public List<Document> find(String collectionName, Document condition, Document sort, int skipNum, int limit) throws MongoException {
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(collectionName);
            FindIterable<Document> iterable = collection.find(condition).sort(sort).skip(skipNum).limit(limit);
            List<Document> list = new ArrayList<Document>(limit);
            MongoCursor<Document> cursor = iterable.iterator();
            while (cursor.hasNext()) {
                list.add(cursor.next());
            }
            return list;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }

    public long fetchId(String channelName) throws MongoException {
        return fetchId(channelName, 1);
    }

    public long fetchId(String channelName, int step) throws MongoException {
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(sequence_table);
            Document bson = (Document) collection.findOneAndUpdate(new Document().append("_id", channelName),
                    new Document().append("$inc", new Document("seqid", step)));
            return bson.getLong("seqid");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }

    public void initSeqId(String channelName, long initSeqId) throws MongoException {
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(sequence_table);
            Document document = new Document();
            document.put("_id", channelName);
            document.put("seqid", initSeqId);
            collection.insertOne(document);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }

    public long count(String collectionName) throws MongoException {
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(collectionName);
            return collection.count();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }

    public long count(String collectionName, Document condition) throws MongoException {
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(collectionName);
            if (condition == null)
                return collection.count();
            return collection.count(condition);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }

    public FindIterable<Document> findMany(String collectionName, Document condition) throws MongoException {
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(collectionName);
            FindIterable<Document> iterable = collection.find(condition);
            return iterable;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }

    /**
     * 查找并更新字段
     *
     * @param collectionName
     * @param condition
     * @param updateDoc
     * @return
     * @throws MongoException
     */

    public Document findOneAndUpdate(String collectionName, Document condition, Document updateDoc) throws MongoException {
        return findOneAndUpdate(collectionName, condition, updateDoc, "$set");
    }

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

    public Document findOneAndUpdate(String collectionName, Document condition, Document updateDoc, String instruct) throws MongoException {
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(collectionName);
            Document updateFind = new Document(instruct, updateDoc);
            return (Document) collection.findOneAndUpdate(condition, updateFind);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }


    public FindIterable<Document> findMany(String collectionName, Document condition, Document sort) throws MongoException {
        try {
            MongoCollection collection = MongoDBFactory.getInstance().getDB().getCollection(collectionName);
            FindIterable<Document> iterable = collection.find(condition).sort(sort);
            return iterable;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MongoException(e);
        }
    }
}
