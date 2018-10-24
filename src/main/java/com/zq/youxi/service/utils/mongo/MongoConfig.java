/**
 * Copyright 2013-2014 The JMingo Team
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zq.youxi.service.utils.mongo;


import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

import java.util.ArrayList;
import java.util.List;

/**
 * Config is used to collect information necessary to create and set up {@link com.mongodb.Mongo} instance.
 */
public class MongoConfig {
    private final List<ServerAddress> serverAddressList;
    private final String serverAddressInfo;
    private final String dbName;
    private final String dbUserName;
    private final String dbPasswd;

    public MongoConfig() {
        this.serverAddressInfo = SysConfig.getPro("server_address_info");
        this.dbName = SysConfig.getPro("database_name");
        this.serverAddressList = createServerAddressList(serverAddressInfo);
        this.dbUserName = SysConfig.getPro("db_username");
        this.dbPasswd = SysConfig.getPro("db_passwd");
    }

    public MongoConfig(String dbName) {
        this.serverAddressInfo = SysConfig.getPro("server_address_info");
        this.dbName = dbName;
        this.serverAddressList = createServerAddressList(serverAddressInfo);
        this.dbUserName = SysConfig.getPro("db_username");
        this.dbPasswd = SysConfig.getPro("db_passwd");
    }

    public MongoConfig(String serverAddressInfo, String dbName) {
        this.serverAddressInfo = serverAddressInfo;
        this.dbName = dbName;
        this.serverAddressList = createServerAddressList(serverAddressInfo);
        this.dbUserName = SysConfig.getPro("db_username");
        this.dbPasswd = SysConfig.getPro("db_passwd");
    }

    private List<ServerAddress> createServerAddressList(String serverAddressInfo) {
        List<ServerAddress> saList = new ArrayList<ServerAddress>();
        if (serverAddressInfo == null || serverAddressInfo.equals("")) {
            throw new MongoConfigurationException("serverAddressInfo is Empty.");
        } else {
            try {
                String[] sas = serverAddressInfo.split(",");
                for (String serverAddress : sas) {
                    saList.add(new ServerAddress(serverAddress.split(":")[0], Integer.parseInt(serverAddress.split(":")[1])));
                }
            } catch (Exception e) {
                throw new MongoConfigurationException("serverAddressInfo format is Wrong,  For 127.0.0.1:27017,127.0.0.1:27018 .");
            }
        }
        return saList;
    }

    public String getServerAddressInfo() {
        return serverAddressInfo;
    }

    public String getDbName() {
        return dbName;
    }

    public List<ServerAddress> getServerAddressList() {
        return serverAddressList;
    }

    public MongoClientOptions createMongoClientOptions() {
        Builder builder = MongoClientOptions.builder();
        WriteConcern concern = WriteConcern.SAFE;
        String writeConcernType = SysConfig.getPro("writeConcernType");

        if (writeConcernType != null) {
            if (writeConcernType.equals("ACKNOWLEDGED") || writeConcernType.equals("SAFE")) {
                concern = WriteConcern.SAFE;
            }
            if (writeConcernType.equals("UNACKNOWLEDGED") || writeConcernType.equals("NORMAL")) {
                concern = WriteConcern.NORMAL;
            }
            if (writeConcernType.equals("FSYNCED") || writeConcernType.equals("FSYNC_SAFE")) {
                concern = WriteConcern.FSYNC_SAFE;
            }
            if (writeConcernType.equals("JOURNALED") || writeConcernType.equals("JOURNAL_SAFE")) {
                concern = WriteConcern.JOURNAL_SAFE;
            }
            if (writeConcernType.equals("REPLICA_ACKNOWLEDGED") || writeConcernType.equals("REPLICAS_SAFE")) {
                concern = WriteConcern.REPLICAS_SAFE;
            }
            if (writeConcernType.equals("MAJORITY")) {
                concern = WriteConcern.MAJORITY;
            }
        }
        builder = builder.writeConcern(concern);
        builder = builder.minConnectionsPerHost(Integer.parseInt(isNull(SysConfig.getPro("minConnectionsPerHost"), "0")));
        builder = builder.connectionsPerHost(Integer.parseInt(isNull(SysConfig.getPro("maxConnectionsPerHost"), "100")));
        builder = builder.threadsAllowedToBlockForConnectionMultiplier(Integer.parseInt(isNull(SysConfig.getPro("threadsAllowedToBlockForConnectionMultiplier"), "5")));
        builder = builder.serverSelectionTimeout(Integer.parseInt(isNull(SysConfig.getPro("serverSelectionTimeout"), "30000")));
        builder = builder.maxWaitTime(Integer.parseInt(isNull(SysConfig.getPro("maxWaitTime"), "120000")));
        builder = builder.maxConnectionIdleTime(Integer.parseInt(isNull(SysConfig.getPro("maxConnectionIdleTime"), "0")));
        builder = builder.maxConnectionLifeTime(Integer.parseInt(isNull(SysConfig.getPro("maxConnectionLifeTime"), "0")));
        builder = builder.connectTimeout(Integer.parseInt(isNull(SysConfig.getPro("connectTimeout"), "10000")));
        builder = builder.socketTimeout(Integer.parseInt(isNull(SysConfig.getPro("socketTimeout"), "0")));
        builder = builder.socketKeepAlive(Boolean.parseBoolean((isNull(SysConfig.getPro("socketKeepAlive"), "false")).toLowerCase()));
        builder = builder.heartbeatFrequency(Integer.parseInt(isNull(SysConfig.getPro("heartbeatFrequency"), "10000")));
        builder = builder.minHeartbeatFrequency(Integer.parseInt(isNull(SysConfig.getPro("minHeartbeatFrequency"), "500")));
        builder = builder.heartbeatConnectTimeout(Integer.parseInt(isNull(SysConfig.getPro("heartbeatConnectTimeout"), "20000")));
        builder = builder.heartbeatSocketTimeout(Integer.parseInt(isNull(SysConfig.getPro("heartbeatSocketTimeout"), "20000")));
        builder = builder.localThreshold(Integer.parseInt(isNull(SysConfig.getPro("localThreshold"), "15")));
        builder = builder.cursorFinalizerEnabled(Boolean.parseBoolean((isNull(SysConfig.getPro("cursorFinalizerEnabled"), "true")).toLowerCase()));
        return builder.build();
    }

    private static String isNull(String str1, String str2) {
        if (str1 == null || str1.equals("")) {
            return str2;
        }
        return str1;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public String getDbPasswd() {
        return dbPasswd;
    }
}
