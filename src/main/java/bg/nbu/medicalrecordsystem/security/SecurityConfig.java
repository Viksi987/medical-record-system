package bg.nbu.medicalrecordsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/login",
                                "/register",
                                "/save-user",
                                "/error"
                        ).permitAll()

                        .requestMatchers("/")
                        .hasAnyRole("ADMIN", "DOCTOR", "PATIENT")

                        .requestMatchers(
                                "/patients-page",
                                "/doctors-page",
                                "/examinations-page",
                                "/sick-leaves-page"
                        )
                        .hasAnyRole("ADMIN", "DOCTOR")

                        .requestMatchers("/patient-history/**")
                        .hasAnyRole("ADMIN", "DOCTOR", "PATIENT")

                        .requestMatchers(
                                "/add-patient",
                                "/save-patient",
                                "/delete-patient/**",
                                "/edit-patient/**",
                                "/update-patient"
                        )
                        .hasRole("ADMIN")

                        .requestMatchers(
                                "/add-doctor",
                                "/save-doctor",
                                "/delete-doctor/**",
                                "/edit-doctor/**",
                                "/update-doctor"
                        )
                        .hasRole("ADMIN")

                        .requestMatchers(
                                "/add-examination",
                                "/save-examination",
                                "/delete-examination/**",
                                "/edit-examination/**",
                                "/update-examination"
                        )
                        .hasAnyRole("ADMIN", "DOCTOR")

                        .requestMatchers(
                                "/add-sick-leave",
                                "/save-sick-leave",
                                "/delete-sick-leave/**",
                                "/edit-sick-leave/**",
                                "/update-sick-leave"
                        )
                        .hasAnyRole("ADMIN", "DOCTOR")

                        .anyRequest()
                        .authenticated()
                )

                .formLogin(form -> form

                        .loginPage("/login")

                        .defaultSuccessUrl("/", true)

                        .permitAll()
                )

                .logout(logout -> logout

                        .logoutSuccessUrl("/login?logout")

                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}