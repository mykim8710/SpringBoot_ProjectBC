package io.mykim.bc.springBoot.config.auth;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import io.mykim.bc.springBoot.domain.user.Role;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정 활성화

public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final CustomOAuth2UserService customOAuth2UserService;
	
	// OAuth 설정 메서드
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.headers().frameOptions().disable() // h2-console 사용을 위해 disable
				
				.and()
					.authorizeRequests()		// URL별 권한관리를 설정하는 옵션의 시작점, .authorizeRequests()가 선언되어야 .antMatchers()를 사용
					.antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()	// .antMatchers() : 권한관리 대상을 지정
					.antMatchers("/api/v1/**").hasRole(Role.USER.name())	// USER권한을 가진 사람만 가능
					.anyRequest().authenticated()	// .anyRequest() : 설정된 값 이외의 나머지 URL, .authenticated() : 나머지 URL들은 모두 인증된 사용자(로그인 유저)들에게 만 허용
				
				.and()
					.logout() // 로그아웃 설정
						.logoutSuccessUrl("/") // 로그아웃 성공시 "/" 주소로 이동
				
				.and()
					.oauth2Login() // OAuth2 로그인 기능에 대한 여러설정의 진입점
						.userInfoEndpoint()	// OAuth2 로그인 성공이후 사용자 정보를 가져올때의 설정 담당
							.userService(customOAuth2UserService); // 소셜로그인 성공 후 진행할 UserService 인터페이스의 구현체 등록, 소셜서비스들에서 사용자정보를 가져온 상태에서 추가로 진행하고자하는 기능을 명시할 수 있음
							
	}
}
