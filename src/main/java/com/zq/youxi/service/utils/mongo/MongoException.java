package com.zq.youxi.service.utils.mongo;

public class MongoException extends Exception {

    public MongoException(String message) {
        super(message);
    }

    public MongoException(String message, Throwable t) {
        super(message, t);
    }

    public MongoException(Throwable t) {
        super(t);
    }
}
