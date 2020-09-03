package io.mykim.bc.springBoot.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	// 소셜로그인으로 반환되는 값 중 email을 통해 이미 생성된 사용자인지 처음 가입하는 사용자인지 판단하는 메서드
	// Spring Data JPA 사용 시 Repository에서 리턴 타입을 Optional로 바로 받을 수 있도록 지원 : 자바 8에서는 Optional<T> 클래스를 이용해서 NullPointerException을 방지
	// Optional<T> 클래스는 한 마디로 null 이 올 수 있는 값을 감싸는 래퍼 클래스로 참조하더라도 null 이 일어나지 않도록 해주는 클래스
	
	Optional<User> findByEmail(String email);
}
