package io.mykim.bc.springBoot.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void 메인페이지_로딩된다() {
		// when
		String body = this.restTemplate.getForObject("/", String.class);
		
		System.out.println(body);
		
		// then 
		assertThat(body).contains("SpringBoot_JPA Board");
		
	}
	
	@Test
	public void 게시글작성페이지_로딩된다() {
		// when
		String body = this.restTemplate.getForObject("/posts/save", String.class);
		
		System.out.println(body);
		
		// then 
		assertThat(body).contains("게시글 등록 페이지");
		
	}
}
