package io.mykim.bc.springBoot.config.auth;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import io.mykim.bc.springBoot.config.auth.dto.OAuthAttributes;
import io.mykim.bc.springBoot.config.auth.dto.SessionUser;
import io.mykim.bc.springBoot.domain.user.User;
import io.mykim.bc.springBoot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service

public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	private final UserRepository userRepository;
	private final HttpSession httpSession;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2UserService delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);
		

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		// 현재 로그인을 진행 중인 서비스를 구분하는 코드 : 로그인을 네이버인지, 구글인지 , 카카오인지 구분하는 코드
		
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		// OAuth2 로그인 진행 시 키가 되는 필드(like pk)
		// 구글은 기본적으로 코드(sub)를 지원, 네이버, 카카오는 미지원 : 구글, 네이버 로그인을 동시 지원할때 사용
		
		OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
		// OAuthAttributes : OAuth2UserService를 통해 가져온 OAuth2User의 Attribute(속성, 정보)를 담을 클래스
		
		User user = saveOrUpdate(attributes);
		
		httpSession.setAttribute("user", new SessionUser(user)); // SessionUser 클래스 : 세션에 사용자정보를 저장하기위한 Dto 클래스, 로그인 시
				
		return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey());
	}
	
	private User saveOrUpdate(OAuthAttributes attributes) {
		User user = userRepository.findByEmail(attributes.getEmail())
																		.map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
																		.orElse(attributes.toEntity());
		
		return userRepository.save(user);
	}
}
