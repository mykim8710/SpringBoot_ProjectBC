package io.mykim.bc.springBoot.config.auth;

import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import io.mykim.bc.springBoot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
	private final HttpSession httpSession;
	
	//supportsParameter() : 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
		boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
						
		return isLoginUserAnnotation && isUserClass;
	}
	
	//resolveArgument() 파라미터에 전달하는 객체 생성, 세션에서 객체를 가져옴
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		return httpSession.getAttribute("user");
	}
}
