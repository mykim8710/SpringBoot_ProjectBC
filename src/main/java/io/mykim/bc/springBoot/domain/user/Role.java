// 각 사용자의 권한을 관리하는 Enum class

package io.mykim.bc.springBoot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	
	// 스프링 시큐리티에서는 권한코드에 항상 'ROLE_'이 앞에 있어야 함!!
	// 코드(key, title)
	GUEST("ROLE_GUEST", "손님"),
	USER("ROLE_USER", "일반사용자");
	
	private final String key;
	private final String title;
}
