package com.sp.fc.web.config;

import com.sp.fc.web.student.StudentManager;
import com.sp.fc.web.teacher.TeacherManager;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;

@Order(1)
@Configuration/**/
//@EnableWebSecurity(debug = false)
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MobileSecurityConfig {

    CsrfFilter filter;
    private StudentManager studentManager;
    private TeacherManager teacherManager;

    public MobileSecurityConfig(StudentManager studentManager, TeacherManager teacherManager) {
        this.studentManager = studentManager;
        this.teacherManager = teacherManager;
    }

    //FIXME 필터에서 403을 떨군다 이유룰 못참음 고칠 필요가 있다.
    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .antMatcher("/api/**")
                .csrf().disable()
                .authorizeRequests(reqs -> reqs.anyRequest().permitAll() )
                .httpBasic()
                .and()
                .authenticationProvider(studentManager)
                .authenticationProvider(teacherManager)
                .build();
    }



}
