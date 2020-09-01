package io.mykim.bc.springBoot.domain;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass		// 이 클래스를 상속받는 다른 Entity 클래스들이 createdTime, modifiedTime 필드를 컬럼으로 인식되게 함
@EntityListeners(AuditingEntityListener.class)		// BaseTimeEntity 클래스에 Auditing기능을 포함
public abstract class BaseTimeEntity {
	@CreatedDate		// entity가 생성되고 저장될때 시간이 자동으로 저장
	private LocalDateTime createdTime;
	
	@LastModifiedDate	// entity의 값을 변경할 때 시간이 자동으로 저장
	private LocalDateTime modifiedTime;
}
