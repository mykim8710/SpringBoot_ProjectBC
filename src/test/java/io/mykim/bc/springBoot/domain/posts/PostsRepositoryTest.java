// findAll(SELECT), save(INSERT) 기능 Test
// BaseTimeEntity 기능 test
package io.mykim.bc.springBoot.domain.posts;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest // @SpringBootTest 사용할 경우, h2 db를 자동으로 실행

public class PostsRepositoryTest {
	@Autowired
	private PostsRepository postsRepository;

	@After
	public void cleanUp() {
		postsRepository.deleteAll();
	}

	@Test
	public void 게시글저장후_불러오기() {
		// given
		String title = "test title";
		String content = "test content";

		// DB insert
		postsRepository.save(Posts.builder().title(title).content(content).author("author").build());

		// when
		List<Posts> postsList = postsRepository.findAll();

		// then
		Posts posts = postsList.get(0);
		assertThat(posts.getTitle()).isEqualTo(title);
		assertThat(posts.getContent()).isEqualTo(content);

	}

	@Test
	public void BaseTimeEntity_등록된다() {
		// given
		LocalDateTime nowTime = LocalDateTime.of(2020, 9, 1, 0, 0, 0);
		postsRepository.save(Posts.builder()
													.title("title")
													.content("content")
													.author("author").build());
		
		// when
		List<Posts> postsList = postsRepository.findAll();
		
		// then
		Posts posts = postsList.get(0);
		
		System.out.println("create Time : " +posts.getCreatedTime());
		System.out.println("modified Time : " +posts.getModifiedTime());
		
		assertThat(posts.getCreatedTime()).isAfter(nowTime);
		assertThat(posts.getModifiedTime()).isAfter(nowTime);	
	}

}
