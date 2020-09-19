// OAuthAttributes : OAuth2UserService를 통해 가져온 OAuth2User의 Attribute(속성, 정보)를 담을 클래스
// 스프링 시큐리티 OAuth 인증을 위한 속성 객체 

package io.mykim.bc.springBoot.config.auth.dto;

import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.collections4.MapUtils;

import io.mykim.bc.springBoot.domain.user.Role;
import io.mykim.bc.springBoot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
    
	private final static Logger LOG = Logger.getGlobal();
	
	private Map<String, Object> attributes;
	private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
    
    /**
     * 구글, 카카오, 네이버 등에 따른 속성을 만들어줌
     * @param registrationId 소셜 타입, 즉 네이버, 카카오, 구글
     * @param userNameAttributeName Principal.getName 하게 되면 나오는 로그인한 유저의 이름으로 등록할 필드명
     * @param attributes 각 플랫폼에서 반환받은 유저 정보
     * @return 인증 객체
     */
   
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
    	
    	LOG.info("요청 :: "+registrationId);
    	LOG.info("유저이름 :: "+userNameAttributeName);
    	LOG.info("속성 :: "+attributes);
    	
    	if("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        } else if("kakao".equals(registrationId)) {
        	return ofKakao("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name(MapUtils.getString(attributes, "name"))
                .email(MapUtils.getString(attributes, "email"))
                .picture(MapUtils.getString(attributes, "picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
    	Map<String, Object> response = (Map<String, Object>) attributes.get("response");
    	     
        return OAuthAttributes.builder()
        		.name(MapUtils.getString(response, "name"))
                .email(MapUtils.getString(response, "email"))
                .picture(MapUtils.getString(response, "profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    
    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
    	Map<String,Object> response = (Map<String, Object>)attributes.get("kakao_account");
    	Map<String, Object> profile = (Map<String, Object>) response.get("profile");
    	
    	System.out.println("response : " +response);
    	System.out.println("profile : " +profile);
    	
    	return OAuthAttributes.builder()
			    	.name(MapUtils.getString(profile, "nickname"))
			    	.email(MapUtils.getString(response, "email"))
			    	.picture(MapUtils.getString(profile, "profile_image_url"))
			    	.attributes(attributes)
			    	.nameAttributeKey(userNameAttributeName)
			    	.build();
	}

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
