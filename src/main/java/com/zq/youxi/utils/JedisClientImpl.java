package com.zq.youxi.utils;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alibaba.fastjson.JSON;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

public class JedisClientImpl extends Jedis {

    private static final Logger log = LoggerFactory.getLogger(JedisClientImpl.class);

        @Autowired
        @Qualifier(value = "jedisPool")
        private JedisPool jedisPool;

        public JedisPool getJedisPool() {
            return jedisPool;
        }

        public void setJedisPool(JedisPool jedisPool) {
            this.jedisPool = jedisPool;
        }

        @Override
        public Long del(String key) {
            Long del = null;
            try (Jedis jedis = jedisPool.getResource()) {
                del = jedis.del(key);
            } catch (Exception e) {
                log.error("jedis del failed", e);
            }
            return del;
        }

        @Override
        public Boolean exists(String key) {
            Boolean boolean1 = null;
            try (Jedis jedis = jedisPool.getResource()) {
                boolean1 = jedis.exists(key);
            } catch (Exception e) {
                log.error("jedis exists failed", e);
            }
            return boolean1;
        }

        @Override
        public Boolean hexists(String key, String field) {
            Boolean result = null;
            try (Jedis jedis = jedisPool.getResource()) {
                result = jedis.hexists(key, field);
            } catch (Exception e) {
                log.error("jedis exists failed", e);
            }
            return result;
        }

        @Override
        public Set<String> hkeys(String key) {
            Set<String> hkeys = null;
            try (Jedis jedis = jedisPool.getResource()) {
                hkeys = jedis.hkeys(key);
            } catch (Exception e) {
                log.error("jedis hkeys failed", e);
            }
            return hkeys;
        }

        @Override
        public List<String> hvals(String key) {
            List<String> hvals = null;
            try (Jedis jedis = jedisPool.getResource()) {
                hvals = jedis.hvals(key);
            } catch (Exception e) {
                log.error("jedis hkeys failed", e);
            }
            return hvals;
        }

        public Boolean hexists(Object key, Object field) {
            return hexists(String.valueOf(key), String.valueOf(field));
        }

        @Override
        public Long hdel(String key, String... fields) {
            Long hdel = null;
            try (Jedis jedis = jedisPool.getResource()) {
                hdel = jedis.hdel(key, fields);
            } catch (Exception e) {
                log.error("jedis hdel failed", e);
            }
            return hdel;
        }

        @Override
        public Long expire(String key, int seconds) {
            Long expire = null;
            try (Jedis jedis = jedisPool.getResource()) {
                expire = jedis.expire(key, seconds);
            } catch (Exception e) {
                log.error("jedis expire failed", e);
            }
            return expire;
        }

        @Override
        public String get(String key) {
            String value = null;
            try (Jedis jedis = jedisPool.getResource()) {
                value = jedis.get(key);
            } catch (Exception e) {
                log.error("jedis get failed", e);
            }
            return value;
        }

        @Override
        public byte[] get(byte[] key) {
            byte[] value = null;
            try (Jedis jedis = jedisPool.getResource()) {
                value = jedis.get(key);
            } catch (Exception e) {
                log.error("jedis get failed", e);
            }
            return value;
        }

        @Override
        public String hget(String key, String field) {
            String hget = null;
            try (Jedis jedis = jedisPool.getResource()) {
                hget = jedis.hget(key, field);
            } catch (Exception e) {
                log.error("jedis hget failed", e);
            }
            return hget;
        }

        public String hget(Object key, Object field) {
            return hget(String.valueOf(key), String.valueOf(field));
        }

        @Override
        public Long hset(String key, String field, String value) {
            Long hset = null;
            try (Jedis jedis = jedisPool.getResource()) {
                hset = jedis.hset(key, field, value);
            } catch (Exception e) {
                log.error("jedis hget failed", e);
            }
            return hset;
        }

        public Long hset(String key, Object field, Object value) {
            String jsonString = JSON.toJSONString(value);
            String fieldStr = String.valueOf(field);
            return hset(key, fieldStr, jsonString);
        }

        @Override
        public Long incr(String key) {
            Long result = -1L;
            try (Jedis jedis = jedisPool.getResource()) {
                result = jedis.incr(key);
            } catch (Exception e) {
                log.error("jedis incr fialed", e);
            }
            return result;
        }

        @Override
        public String set(String key, String value) {
            String set = null;
            try (Jedis jedis = jedisPool.getResource()) {
                set = jedis.set(key, value);
            } catch (Exception e) {
                log.error("jedis set failed", e);
            }
            return set;
        }

        @Override
        public Long zadd(String key, double score, String member) {
            Long zadd = null;
            try (Jedis jedis = jedisPool.getResource()) {
                zadd = jedis.zadd(key, score, member);

            } catch (Exception e) {
                log.error("jedis add failed", e);
            }
            return zadd;
        }

        @Override
        public Double zincrby(String key, double score, String member, ZIncrByParams params) {
            Double zincrby = null;
            try (Jedis jedis = jedisPool.getResource()) {
                zincrby = jedis.zincrby(key, score, member, params);
            } catch (Exception e) {
                log.error("jedis zrem failed", e);
            }
            return zincrby;
        }

        @Override
        public Long zrem(String key, String... member) {
            Long zrem = null;
            try (Jedis jedis = jedisPool.getResource()) {
                zrem = jedis.zrem(key, member);
            } catch (Exception e) {
                log.error("jedis zrem failed", e);
            }

            return zrem;
        }

        @Override
        public Long zremrangeByScore(String key, double start, double end) {
            Long zremrangeByScore = null;
            try (Jedis jedis = jedisPool.getResource()) {
                zremrangeByScore = jedis.zremrangeByScore(key, start, end);
            } catch (Exception e) {
                log.error("zremrangeByScore failed", e);
            }
            return zremrangeByScore;
        }

        @Override
        public Set<Tuple> zrangeWithScores(String key, long start, long end) {
            Set<Tuple> tupleSet = null;
            try (Jedis jedis = jedisPool.getResource()) {
                tupleSet = jedis.zrangeWithScores(key, start, end);
            } catch (Exception e) {
                log.error("zremrangeWithScore failed", e);
            }
            return tupleSet;
        }

        @Override
        public Double zscore(String key, String member) {
            Double zscore = null;
            try (Jedis jedis = jedisPool.getResource()) {
                zscore = jedis.zscore(key, member);
            } catch (Exception e) {
                log.error("JedisClient zscore", e);
            }
            return zscore;
        }

        @Override
        public Set<String> zrange(String key, long start, long end) {
            Set<String> zrange = null;
            try (Jedis jedis = jedisPool.getResource()) {
                zrange = jedis.zrange(key, start, end);
            } catch (Exception e) {
                log.error("JedisClient zrange", e);
            }
            return zrange;
        }

        @Override
        public Set<String> zrevrange(String key, long start, long end) {
            Set<String> zrevrange = null;
            try (Jedis jedis = jedisPool.getResource()) {
                zrevrange = jedis.zrevrange(key, start, end);
            } catch (Exception e) {
                log.error("JedisClient zrange", e);
            }
            return zrevrange;
        }

        @Override
        public Long zcount(String key, double min, double max) {
            Long zcount = null;
            try (Jedis jedis = jedisPool.getResource()) {
                zcount = jedis.zcount(key, min, max);
            } catch (Exception e) {
                log.error("zcount failed", e);
            }
            return zcount;
        }

        @Override
        public Long zcard(String key) {
            Long zcard = null;
            try (Jedis jedis = jedisPool.getResource()) {
                zcard = jedis.zcard(key);
            } catch (Exception e) {
                log.error("zcard failed", e);
            }
            return zcard;
        }

        @Override
        public Set<String> zrangeByScore(String key, double min, double max) {
            Set<String> zrangeByScore = null;
            try (Jedis jedis = jedisPool.getResource()) {
                zrangeByScore = jedis.zrangeByScore(key, min, max);
            } catch (Exception e) {
                log.error("zrangeByScore failed", e);
            }
            return zrangeByScore;
        }

        @Override
        public Set<String> keys(String pattern) {
            Set<String> keys = null;
            try (Jedis jedis = jedisPool.getResource()) {
                keys = jedis.keys(pattern);
            } catch (Exception e) {
                log.error("zrangeByScore failed", e);
            }
            return keys;
        }

        @Override
        public Boolean setbit(String key, long offset, boolean value) {
            Boolean setbit = Boolean.FALSE;
            try (Jedis jedis = jedisPool.getResource()) {
                setbit = jedis.setbit(key, offset, value);
            } catch (Exception e) {
                log.error("setbit failed", e);
            }
            return setbit;
        }

        @Override
        public Boolean setbit(String key, long offset, String value) {
            Boolean setbit = Boolean.FALSE;
            try (Jedis jedis = jedisPool.getResource()) {
                setbit = jedis.setbit(key, offset, value);
            } catch (Exception e) {
                log.error("setbit failed", e);
            }
            return setbit;
        }

        @Override
        public Boolean getbit(String key, long offset) {
            Boolean getbit = Boolean.FALSE;
            try (Jedis jedis = jedisPool.getResource()) {
                getbit = jedis.getbit(key, offset);
            } catch (Exception e) {
                log.error("getbit failed", e);
            }
            return getbit;
        }

        @Override
        public String ping() {
            try (Jedis jedis = jedisPool.getResource()) {
                return jedis.ping();
            }
        }
    }

