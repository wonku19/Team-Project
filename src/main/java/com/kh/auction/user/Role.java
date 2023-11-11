package com.kh.auction.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public enum Role {
    USER(Set.of(
            Permission.USER_READ
    )),
    ADMIN(
            Collections.emptySet()
    )

    ;

    @Getter
    private final Set<Permission> permissions;

//    public List<SimpleGrantedAuthority> getAuthorities()
//    {
//        var authorities = getPermissions()
//                .stream()
//                .map(permission -> new SimpleGrantedAuthority(permission.name()))
//                .toList();
//        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
//        log.info(""+authorities);
//        return authorities;
//    }
}
