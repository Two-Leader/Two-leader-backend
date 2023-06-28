package com.twoleader.backend.global.common;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {

  @CreatedDate private LocalDateTime createAt;

  @LastModifiedDate private LocalDateTime updateAt;

  @Column(nullable = false)
  private boolean isDeleted;

  public void changeDeleted() {
    this.isDeleted = false;
  }
}
