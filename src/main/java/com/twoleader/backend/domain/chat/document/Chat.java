package com.twoleader.backend.domain.chat.document;

import com.twoleader.backend.global.common.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Document(collection = "chat")
public class Chat{
    @Id
    private String id;

    @Field
    private UUID roomUuid;

    @Field
    private Long userId;

    @Field
    private String message;

    @Field
    @CreatedDate
    private LocalDateTime createdAt;

}