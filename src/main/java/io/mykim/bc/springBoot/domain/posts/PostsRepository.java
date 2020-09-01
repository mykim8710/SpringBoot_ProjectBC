package io.mykim.bc.springBoot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// @Reposity의 역할, extends JpaRepository<Entity class, pk type> : 기본 CRUD 메서드 생성
// Entity class와 같은 곳에 위치해야 함 
public interface PostsRepository extends JpaRepository<Posts, Long>{

}
