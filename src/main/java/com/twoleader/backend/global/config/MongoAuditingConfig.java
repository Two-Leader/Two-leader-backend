package com.twoleader.backend.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("com.twoleader.backend.domain.chat.repository")
@EnableMongoAuditing
public class MongoAuditingConfig {
}
