package io.mykim.bc.springBoot.service.posts;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.mykim.bc.springBoot.domain.posts.Posts;
import io.mykim.bc.springBoot.domain.posts.PostsRepository;
import io.mykim.bc.springBoot.web.dto.PostsResponseDto;
import io.mykim.bc.springBoot.web.dto.PostsSaveRequestDto;
import io.mykim.bc.springBoot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostsService {
	private final PostsRepository postsRepository;
	
	@Transactional
	public Long save(PostsSaveRequestDto postsSaveRequestDto) {
		return postsRepository.save(postsSaveRequestDto.toEntity()).getId();
	}
	
	@Transactional
	public Long update(Long id, PostsUpdateRequestDto updateRequestDto) {
		Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. 게시글 id : " +id));
		
		posts.update(updateRequestDto.getTitle(), updateRequestDto.getContent());
		
		return id;
	}
	
	@Transactional
	public PostsResponseDto findById(Long id) {
		Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. 게시글 id : " +id));
			
		return new PostsResponseDto(entity);
	}

}
