package io.mykim.bc.springBoot.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	public void 메인페이지_로딩된다() {
		// when
		String body = this.restTemplate.getForObject("/", String.class);

		System.out.println(body);

		// then
		assertThat(body).contains("SpringBoot_JPA Board");

	}

	@Test
	@WithMockUser(roles = "USER")
	public void 게시글작성페이지_로딩된다() throws Exception {
		String str = "게시글 등록 페이지";

		mvc.perform(get("/posts/save"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(str)));	// 해당 url로 이동한 페이지의 본문중에 str이 포함된어 있는지 확인
	}

}
