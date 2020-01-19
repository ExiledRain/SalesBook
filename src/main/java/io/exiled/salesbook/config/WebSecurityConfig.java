//package io.exiled.salesbook.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
////    @Autowired
////    private UserService userService;
////
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http
////                .authorizeRequests()
////                    .antMatchers("/**","/registration","/img/**","/styles/**").permitAll()
////                    .anyRequest().authenticated()
////                .and()
////                    .formLogin()
////                    .loginPage("/login")
////                    .permitAll()
////                .and()
////                    .logout()
////                    .permitAll();
////    }
//
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userService)
////                .passwordEncoder(NoOpPasswordEncoder.getInstance());
////    }
////
////    @Bean
////    public AlpService alpService(){
////        return new AlpService();
////    }
//
//    // In memory authorization
////    @Bean
////    @Override
////    public UserDetailsService userDetailsService() {
////        UserDetails user =
////                User.withDefaultPasswordEncoder()
////                        .username("user")
////                        .password("user")
////                        .roles("USER")
////                        .build();
////
////        return new InMemoryUserDetailsManager(user);
////    }
//}