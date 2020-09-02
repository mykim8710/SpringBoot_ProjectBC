package io.mykim.bc.springBoot.domain.posts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// @Reposity의 역할, extends JpaRepository<Entity class, pk type> : 기본 CRUD 메서드 생성
// Entity class와 같은 곳에 위치해야 함 
public interface PostsRepository extends JpaRepository<Posts, Long>{

	@Query("SELECT p FROM Posts p ORDER BY p.id DESC")
	List<Posts> findAllDesc();

}
