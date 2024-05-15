package study.datajpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

/**
 * study.datajpa.entity JpaBaseEntity
 *
 * @author : K
 */
@Getter
@Setter
@MappedSuperclass //jpa진짜 상속이 아니고 속성만 내려주는것
public class JpaBaseEntity {
  @Column(updatable = false) // db의 값이 업데이트 안되도록
  private LocalDateTime createdDate;

  private LocalDateTime updatedDate;

  @PrePersist
  public void prePersist() {
    LocalDateTime now = LocalDateTime.now();
    createdDate = now;
    updatedDate = now;
  }

  @PreUpdate
  public void preUpdate() {
    updatedDate = LocalDateTime.now();
  }
}
