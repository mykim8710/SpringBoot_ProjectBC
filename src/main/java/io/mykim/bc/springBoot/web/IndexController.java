package io.mykim.bc.springBoot.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.mykim.bc.springBoot.config.auth.LoginUser;
import io.mykim.bc.springBoot.config.auth.dto.SessionUser;
import io.mykim.bc.springBoot.service.posts.PostsService;
import io.mykim.bc.springBoot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class IndexController {
	private final PostsService postsService;
	
	// main : board list
	@GetMapping("/")
	public String index(Model model, @LoginUser SessionUser user) {
		model.addAttribute("posts", postsService.findAllDesc());
		
		if(user != null) {
			model.addAttribute("userName", user.getName());
			//model.addAttribute("picture", user.getPicture());
		}
		
		return "index";
	}

	// 게시글 입력 페이지로 이동
	@GetMapping("/posts/save")
	public String postsSave() {
		return "postsSave";
	}

	// 해당 게시글 조회 페이지
	@GetMapping("/posts/select/{id}")
	public String postsSelect(@PathVariable Long id, Model model) {
		PostsResponseDto responseDto = postsService.findById(id);
		model.addAttribute("posts", responseDto);
		return "postsView";
	}

	// 해당 게시글 수정 페이지
	@GetMapping("/posts/update/{id}")
	public String postsUpdate(@PathVariable Long id, Model model) {
		PostsResponseDto responseDto = postsService.findById(id);
		model.addAttribute("posts", responseDto);
		return "postsUpdate";
	}

}
