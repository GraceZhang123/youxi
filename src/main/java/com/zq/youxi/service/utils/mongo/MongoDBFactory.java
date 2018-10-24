/**
 * Copyright 2013-2014 The JMingo Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zq.youxi.service.utils.mongo;


import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This factory creates and configures {@link DB}.
 */
public class MongoDBFactory {

    private String dbName;
    private String userName;
    private String password;
    private static MongoDBFactory factory;
    private MongoClient mongo;
    private MongoDAO dao = new MongoDAOImpl();
    /*private static final Set<String> UNSUPPORTED_OPTIONS = Sets.newHashSet("dbDecoderFactory", "dbEncoderFactory");*/

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBFactory.class);

    /**
     * Constructor to set config. Creates and configures {@link Mongo} instances based on the config.
     *
     * @param config the mongo configuration
     */
    private MongoDBFactory(MongoConfig config) {
        dbName = config.getDbName();
        userName = config.getDbUserName();
        password = config.getDbPasswd();
        mongo = create(config);
    }

    /**
     * Constructor with parameters.
     *
     * @param config the mongo configuration
     * @param mongo  the mongo instance
     */
    private MongoDBFactory(MongoConfig config, MongoClient mongo) {
        dbName = config.getDbName();
        userName = config.getDbUserName();
        password = config.getDbPasswd();
        this.mongo = mongo;
    }

    public synchronized static boolean createInstance(MongoConfig config) {
        if (factory == null) {
            factory = new MongoDBFactory(config);
            LOGGER.info("Create MongoDBFactory Successful!");
            return true;
        }
        return true;
    }

    public synchronized static void createInstance (MongoConfig config, MongoClient mongo) {
        if (factory == null) {
            factory = new MongoDBFactory(config, mongo);
            LOGGER.info("Create MongoDBFactory Successful!");
        }
    }

    public static MongoDBFactory getInstance() {
        if (factory  == null) {
            throw new MongoConfigurationException("MongoDBFactory not init, Please invok createInstance First!");
        }
        return factory;
    }

    /**
     * Gets mongo {@link DB}.
     *
     * @return mongo {@link DB}
     */
    public MongoDatabase getDB() {
        if (factory != null) {
            return factory.mongo.getDatabase(factory.dbName);
        }
        return null;
    }

    /**
     * Gets mongo instance.
     *
     * @return current mongo instance
     */
    public MongoClient getMongo() {
        return factory.mongo;
    }

    public MongoDAO getMongoDao() { return factory.dao; }

    private MongoClient create(MongoConfig config) {
        MongoClient mongo;
        if(StringUtils.isNotEmpty(userName)) {
            MongoCredential credential = MongoCredential.createCredential(userName, dbName, password.toCharArray());
            List<MongoCredential> credentialList = new ArrayList<MongoCredential>(1);
            credentialList.add(credential);
            mongo = new MongoClient(config.getServerAddressList(), credentialList, config.createMongoClientOptions());
            mongo.setReadPreference(ReadPreference.secondaryPreferred());
        }else{
            mongo = new MongoClient(config.getServerAddressList(), config.createMongoClientOptions());
        }
        return mongo;
    }
}
