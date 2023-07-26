package org.dizena.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoDatabaseFactorySupport;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

import javax.annotation.Resource;

@Configuration
public class MongoConfig {
    @Resource
    private MongoDatabaseFactorySupport mongoDatabaseFactory;

    @Resource
    private MappingMongoConverter mappingMongoConverter;

    @Bean
    public MongoTemplate mongoTemplate() {
        //配置存储不添加class
        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDatabaseFactory, mappingMongoConverter);
        return mongoTemplate;
    }

    /**
     * 事务支持，replica set member or mongo
     */
    @Bean
    public MongoTransactionManager transactionManager() {
        return new MongoTransactionManager(mongoDatabaseFactory);
    }

}
