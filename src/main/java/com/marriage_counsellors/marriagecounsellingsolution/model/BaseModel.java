package com.marriage_counsellors.marriagecounsellingsolution.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
public class BaseModel {

    @Column(name = "created_at")
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updatedAt;
}
