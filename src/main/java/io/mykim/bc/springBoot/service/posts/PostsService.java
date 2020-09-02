package io.mykim.bc.springBoot.service.posts;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.mykim.bc.springBoot.domain.posts.Posts;
import io.mykim.bc.springBoot.domain.posts.PostsRepository;
import io.mykim.bc.springBoot.web.dto.PostsListResponseDto;
import io.mykim.bc.springBoot.web.dto.PostsResponseDto;
import io.mykim.bc.springBoot.web.dto.PostsSaveRequestDto;
import io.mykim.bc.springBoot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostsService {
	private final PostsRepository postsRepository;
	
	// insert
	@Transactional
	public Long save(PostsSaveRequestDto postsSaveRequestDto) {
		return postsRepository.save(postsSaveRequestDto.toEntity()).getId();
	}
	
	// update
	@Transactional
	public Long update(Long id, PostsUpdateRequestDto updateRequestDto) {
		Posts posts = postsRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. 게시글 id : " + id));

		posts.update(updateRequestDto.getTitle(), updateRequestDto.getContent());

		return id;
	}
	
	// select by id
	@Transactional
	public PostsResponseDto findById(Long id) {
		Posts entity = postsRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. 게시글 id : " + id));

		return new PostsResponseDto(entity);
	}
	
	//select all
	@Transactional(readOnly = true) // (readOnly = true) : 트랜젝션 범위는 유지하되 조회기능만 남겨두어 조회속도가 개선 / 등록, 수정, 삭제 기능이 없는
									// 서비스의 메서드에 사용!!
	public List<PostsListResponseDto> findAllDesc() {
		return postsRepository.findAllDesc().stream().map(posts -> new PostsListResponseDto(posts))
				.collect(Collectors.toList());
	}
	
	// delete
	@Transactional
	public void delete(Long id) {
		Posts entity = postsRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. 게시글 id : " + id));
		postsRepository.deleteById(id);
	}

}
