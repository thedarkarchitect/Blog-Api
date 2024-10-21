package com.example.blogApi.config;


import com.example.blogApi.service.user.jwt.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final UserServiceImpl userService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)//this line disables the crsf protection cause the application is stateless cause it is using JWT
                .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated()) //this allows access to all endpoints that start with /api/auth typically used for login and registation or password reset and requires authentication for all other requests
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //this configures how sessions are managed and typically RESTFUL services where each request should be stateless, often in conjuction with JWT
                .authenticationProvider(authenticationProvider()) //this sets the authentication provider to be used by the application
                .addFilterBefore(
                        jwtAuthenticationFilter, //collects the JWT token from the request and validates it and sets the user details in the security context
                        UsernamePasswordAuthenticationFilter.class // UsernamePasswordAuthenticationFilter is the default filter that processes authentication requests after the user submits their username and password
                )
                .cors(c -> c.configurationSource(corsConfig)); //this adds the jwtAuthenticationFilter before the UsernamePasswordAuthenticationFilter

        return httpSecurity.build(); //this builds the securityfilterchain object
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } //this method creates a new instance of BCryptPasswordEncoder which is used to encode and validate passwords

    @Bean //this annotation indicates that the method produces a spring Bean to be managed by the spring container
    public AuthenticationProvider authenticationProvider() {//spring Security uses this method to perform authentication, ensuring that user credentials are validated against the data stored in the database and that passwords are securely handled
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); //this retrieves data from the db
        provider.setUserDetailsService(userService);//using the userDetailsService service interacts with the db to retrieve user details and provider loads user-specific data
        provider.setPasswordEncoder(passwordEncoder());//the DaoAuthenticationProvider uses the passwordEncorder method encode and validate passwords by creating an instance of PasswordEncorder
        return provider;//this configures and returns a DaoAuthenticationProvider Bean
    }

    @Bean
    //AuthenticationManager is a crucial part of Spring Security, it responsible for processing authentication requests
    //By using this Bean to expose the Spring Context, making it available for DI in other parts of the application
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
