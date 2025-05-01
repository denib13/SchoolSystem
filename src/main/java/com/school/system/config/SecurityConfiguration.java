package com.school.system.config;

import com.school.system.users.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public SecurityConfiguration(JwtFilter jwtFilter,
                                 AuthenticationProvider authenticationProvider) {
        this.jwtFilter = jwtFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //http.cors(AbstractHttpConfigurer::disable);
        http
                .cors(cors -> {})
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                                .requestMatchers("api/auth/**").permitAll()
                                // students permissions
                                .requestMatchers(HttpMethod.GET, "api/students/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_READ.getPermission(),
                                        Permission.STUDENT_READ.getPermission(),
                                        Permission.TEACHER_READ.getPermission(),
                                        Permission.PARENT_READ.getPermission(),
                                        Permission.HEADMASTER_READ.getPermission())
                                .requestMatchers(HttpMethod.POST, "api/students/**")
                                .hasAnyAuthority(
                                        Permission.HEADMASTER_CREATE.getPermission(),
                                        Permission.ADMIN_CREATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.PUT, "api/students/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_UPDATE.getPermission(),
                                        Permission.HEADMASTER_UPDATE.getPermission(),
                                        Permission.STUDENT_UPDATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.DELETE, "api/students/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_DELETE.getPermission(),
                                        Permission.HEADMASTER_DELETE.getPermission()
                                )
                                // absences permissions
                                .requestMatchers(HttpMethod.GET, "api/absences/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_READ.getPermission(),
                                        Permission.STUDENT_READ.getPermission(),
                                        Permission.TEACHER_READ.getPermission(),
                                        Permission.HEADMASTER_READ.getPermission(),
                                        Permission.PARENT_READ.getPermission()
                                )
                                .requestMatchers(HttpMethod.POST, "api/absences/**")
                                .hasAnyAuthority(
                                        Permission.TEACHER_CREATE.getPermission(),
                                        Permission.ADMIN_CREATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.PUT, "api/absences/**")
                                .hasAnyAuthority(
                                        Permission.TEACHER_UPDATE.getPermission(),
                                        Permission.ADMIN_UPDATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.DELETE, "api/absences/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_DELETE.getPermission(),
                                        Permission.TEACHER_DELETE.getPermission()
                                )
                                // school classes permissions
                                .requestMatchers(HttpMethod.GET, "api/grades/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_READ.getPermission(),
                                        Permission.STUDENT_READ.getPermission(),
                                        Permission.TEACHER_READ.getPermission(),
                                        Permission.PARENT_READ.getPermission(),
                                        Permission.HEADMASTER_READ.getPermission()
                                )
                                .requestMatchers(HttpMethod.POST, "api/grades/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_CREATE.getPermission(),
                                        Permission.HEADMASTER_CREATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.PUT, "api/grades/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_UPDATE.getPermission(),
                                        Permission.HEADMASTER_UPDATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.DELETE, "api/grades/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_DELETE.getPermission(),
                                        Permission.HEADMASTER_DELETE.getPermission()
                                )
                                // marks permissions
                                .requestMatchers(HttpMethod.GET, "api/marks/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_READ.getPermission(),
                                        Permission.STUDENT_READ.getPermission(),
                                        Permission.PARENT_READ.getPermission(),
                                        Permission.TEACHER_READ.getPermission(),
                                        Permission.HEADMASTER_READ.getPermission()
                                )
                                .requestMatchers(HttpMethod.POST, "api/marks/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_CREATE.getPermission(),
                                        Permission.TEACHER_CREATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.PUT, "api/marks/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_UPDATE.getPermission(),
                                        Permission.TEACHER_UPDATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.DELETE, "api/marks/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_DELETE.getPermission(),
                                        Permission.TEACHER_DELETE.getPermission()
                                )
                                // remarks permissions
                                .requestMatchers(HttpMethod.GET, "api/remarks/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_READ.getPermission(),
                                        Permission.STUDENT_READ.getPermission(),
                                        Permission.PARENT_READ.getPermission(),
                                        Permission.TEACHER_READ.getPermission(),
                                        Permission.HEADMASTER_READ.getPermission()
                                )
                                .requestMatchers(HttpMethod.POST, "api/remarks/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_CREATE.getPermission(),
                                        Permission.TEACHER_CREATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.PUT, "api/remarks/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_UPDATE.getPermission(),
                                        Permission.TEACHER_UPDATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.DELETE, "api/remarks/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_DELETE.getPermission(),
                                        Permission.TEACHER_DELETE.getPermission()
                                )
                                // schools permissions
                                .requestMatchers(HttpMethod.GET, "api/schools/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_READ.getPermission(),
                                        Permission.STUDENT_READ.getPermission(),
                                        Permission.TEACHER_READ.getPermission(),
                                        Permission.PARENT_READ.getPermission(),
                                        Permission.HEADMASTER_READ.getPermission()
                                )
                                .requestMatchers(HttpMethod.POST, "api/schools/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_CREATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.PUT, "api/schools/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_UPDATE.getPermission(),
                                        Permission.HEADMASTER_UPDATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.DELETE, "api/schools/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_DELETE.getPermission()
                                )
                                // subjects permissions
                                .requestMatchers(HttpMethod.GET, "api/subjects/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_READ.getPermission(),
                                        Permission.STUDENT_READ.getPermission(),
                                        Permission.TEACHER_READ.getPermission(),
                                        Permission.PARENT_READ.getPermission(),
                                        Permission.HEADMASTER_READ.getPermission()
                                )
                                .requestMatchers(HttpMethod.POST, "api/subjects/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_CREATE.getPermission(),
                                        Permission.HEADMASTER_CREATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.PUT, "api/subjects/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_UPDATE.getPermission(),
                                        Permission.HEADMASTER_UPDATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.DELETE, "api/subjects/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_DELETE.getPermission(),
                                        Permission.HEADMASTER_DELETE.getPermission()
                                )
                                // headmasters permissions
                                .requestMatchers(HttpMethod.GET, "api/headmasters/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_READ.getPermission(),
                                        Permission.STUDENT_READ.getPermission(),
                                        Permission.TEACHER_READ.getPermission(),
                                        Permission.PARENT_READ.getPermission(),
                                        Permission.HEADMASTER_READ.getPermission()
                                )
                                .requestMatchers(HttpMethod.POST, "api/headmasters/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_CREATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.PUT, "api/headmasters/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_UPDATE.getPermission(),
                                        Permission.HEADMASTER_UPDATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.DELETE, "api/headmasters/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_DELETE.getPermission()
                                )
                                // parents permissions
                                .requestMatchers(HttpMethod.GET, "api/parents/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_READ.getPermission(),
                                        Permission.STUDENT_READ.getPermission(),
                                        Permission.TEACHER_READ.getPermission(),
                                        Permission.PARENT_READ.getPermission(),
                                        Permission.HEADMASTER_READ.getPermission()
                                )
                                .requestMatchers(HttpMethod.POST, "api/parents/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_CREATE.getPermission(),
                                        Permission.HEADMASTER_CREATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.PUT, "api/parents/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_UPDATE.getPermission(),
                                        Permission.HEADMASTER_UPDATE.getPermission(),
                                        Permission.PARENT_UPDATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.DELETE, "api/parents/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_DELETE.getPermission()
                                )
                                // teachers permissions
                                .requestMatchers(HttpMethod.GET, "api/teachers/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_READ.getPermission(),
                                        Permission.STUDENT_READ.getPermission(),
                                        Permission.TEACHER_READ.getPermission(),
                                        Permission.PARENT_READ.getPermission(),
                                        Permission.HEADMASTER_READ.getPermission()
                                )
                                .requestMatchers(HttpMethod.POST, "api/teachers/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_CREATE.getPermission(),
                                        Permission.HEADMASTER_CREATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.PUT, "api/teachers/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_UPDATE.getPermission(),
                                        Permission.HEADMASTER_UPDATE.getPermission(),
                                        Permission.TEACHER_UPDATE.getPermission()
                                )
                                .requestMatchers(HttpMethod.DELETE, "api/teachers/**")
                                .hasAnyAuthority(
                                        Permission.ADMIN_DELETE.getPermission()
                                )
                                .anyRequest().authenticated()
                )
                .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
