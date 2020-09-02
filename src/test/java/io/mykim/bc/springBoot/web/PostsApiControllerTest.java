package io.mykim.bc.springBoot.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import io.mykim.bc.springBoot.domain.posts.Posts;
import io.mykim.bc.springBoot.domain.posts.PostsRepository;
import io.mykim.bc.springBoot.web.dto.PostsSaveRequestDto;
import io.mykim.bc.springBoot.web.dto.PostsUpdateRequestDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PostsRepository postsRepository;

	@After
	public void clearAll() throws Exception {
		postsRepository.deleteAll();
	}

	@Test
	public void Posts_등록된다() throws Exception {
		// given
		String title = "title";
		String content = "content";
		PostsSaveRequestDto saveRequestDto = PostsSaveRequestDto.builder()
																									.title(title)
																									.content(content)
																									.author("author")
																									.build();
		String url = "http://localhost:" + port + "/api/v1/posts";

		// when
		ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, saveRequestDto, Long.class);

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);

		List<Posts> postsList = postsRepository.findAll();
		assertThat(postsList.get(0).getTitle()).isEqualTo(title);
		assertThat(postsList.get(0).getContent()).isEqualTo(content);

	}
	
	@Test
	public void Posts가_수정된다() throws Exception {
		// given
		Posts savePosts = postsRepository.save(Posts.builder()
																			.title("title")
																			.content("content")
																			.author("author")
																			.build());
		Long updatedId = savePosts.getId();
		String updatedTitle = "updatedTitle";
		String updatedContent = "updatedContent";
		
		PostsUpdateRequestDto updateRequestDto = PostsUpdateRequestDto.builder()
																											.title(updatedTitle)
																											.content(updatedContent)
																											.build();
		
		String url = "http://localhost:" + port + "/api/v1/posts/" +updatedId;
		
		HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(updateRequestDto);
		
		// when
		ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
		
		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);

		List<Posts> postsList = postsRepository.findAll();
		assertThat(postsList.get(0).getTitle()).isEqualTo(updatedTitle);
		assertThat(postsList.get(0).getContent()).isEqualTo(updatedContent);		
	}
	
	@Test
	public void Posts가_삭제된다() throws Exception {
		// given
		Posts savePosts = postsRepository.save(Posts.builder()
																			.title("title")
																			.content("content")
																			.author("author")
																			.build());
		Long id = savePosts.getId();
		
		// when
		postsRepository.deleteById(id);
		
		// then
		List<Posts> postsList = postsRepository.findAll();
		assertThat(postsList.size()).isEqualTo(0);
	}
	
}
