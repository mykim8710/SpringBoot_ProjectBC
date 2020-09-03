// SessionUser : 세션에 사용자정보를 저장하기위한 Dto 클래스
// 인증된(로그인 한) 사용자의 정보만 필요

package io.mykim.bc.springBoot.config.auth.dto;

import java.io.Serializable;

import io.mykim.bc.springBoot.domain.user.User;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
