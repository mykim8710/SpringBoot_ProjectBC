package io.mykim.bc.springBoot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.mykim.bc.springBoot.service.posts.PostsService;
import io.mykim.bc.springBoot.web.dto.PostsResponseDto;
import io.mykim.bc.springBoot.web.dto.PostsSaveRequestDto;
import io.mykim.bc.springBoot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
	private final PostsService postsService;
	
	// insert
	@PostMapping("/api/v1/posts")
	public Long save(@RequestBody PostsSaveRequestDto postsSaveRequestDto) {
		return postsService.save(postsSaveRequestDto);
	}
	
	// update
	@PutMapping("/api/v1/posts/{id}")
	public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto updateRequestDto) {
		return postsService.update(id, updateRequestDto);
	}
	
	// select
	@GetMapping("/api/v1/posts/{id}")
	public PostsResponseDto findById(@PathVariable Long id) {
		return postsService.findById(id);
	}
}
