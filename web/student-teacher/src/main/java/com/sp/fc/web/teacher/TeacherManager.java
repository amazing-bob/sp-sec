package com.sp.fc.web.teacher;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

@Component
public class TeacherManager implements AuthenticationProvider, InitializingBean {

    private HashMap<String, Teacher> teacherDb = new HashMap<>();


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TeacherAuthenticationToken token = (TeacherAuthenticationToken) authentication;
        if (teacherDb.containsKey(token.getCredentials())) {
            Teacher authenticatedTeacher = teacherDb.get(token.getCredentials());
            return TeacherAuthenticationToken.builder()
                    .principal(authenticatedTeacher)
                    .details(authenticatedTeacher.getUsername())
                    .authenticated(true)
                    .build();
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == TeacherAuthenticationToken.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set.of(
                new Teacher("kim", "김상헌", Set.of(new SimpleGrantedAuthority("ROLE_TEACHER")), null),
                new Teacher("jung", "정진영", Set.of(new SimpleGrantedAuthority("ROLE_TEACHER")), null)
        ).forEach(t ->
                teacherDb.put(t.getId(), t)
        );
    }
}
