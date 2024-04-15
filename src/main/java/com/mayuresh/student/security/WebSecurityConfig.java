package com.mayuresh.student.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig{

    /*(orignal code) Without_Token_Exception
    @Autowired
    private JwtAuthFilter authFilter;
    */


    @Autowired /*For_Token_Exception*/
    @Qualifier("handlerExceptionResolver") // To avoid the abiguity
    private HandlerExceptionResolver exceptionResolver;


    @Bean  /*For_Token_Exception*/
    public JwtAuthFilter jwtAuthFilter(){
        return new JwtAuthFilter(exceptionResolver);
    }

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService();
    }

    public static final String[] PUBLIC_URLS = { "/api/user/login/save", "/api/DynamicMenus/**",
            "/api/Fund/GetAboutSection/**", "/api/NFO/GetNFO", "/api/FMP/GetFMP", "/api/Fund/GetDocuments",
            "/api/NFO/GetAllNFO", "/api/FMP/GetAllFMP", "/api/Fund/GetFundManagers", "/api/Fund/**", "/api/test",
            "/api/Fund/GetAssetAllocation", "api/Fund/GetLumpsumInvestment**", "api/Fund/GetAllFundsNew**",
            "api/Fund/GetDropdownValues", "api/Fund/GetFundManagerDetails", "api/convert", "api/FAQ", "/api/cache/***","/api/cache/new/***","/api/testlocal",
            "/api/Fund/GetAllFundsNew", "/products/authenticate","/test/hello","/api/heyy","/api/user/**"}; //new line


    //"/api/Fund/GetAllFundsNew", "/products/authenticate","/test/hello","/api/heyy","/api/user/**"
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable().authorizeHttpRequests().requestMatchers("/user/**","/student/getAll","/student/sayhey")
//                .permitAll().anyRequest().authenticated()
//                .and()
//                .formLogin().and().httpBasic().and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
//        DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();
//
//        return defaultSecurityFilterChain;
//    }

    @Bean
    public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
        return http.cors(withDefaults())
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/user/**","/student/sayhey","/student/id/**").permitAll()
//                        .requestMatchers("/student/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/student/**").permitAll()
                        .requestMatchers("/emp/**").hasAnyAuthority("USER")
                        .anyRequest().authenticated())
                .exceptionHandling(exc -> exc.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                //.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class) /*For_Token_Exception*/
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
