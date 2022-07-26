package com.example.cakezip.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class WebSecurityConfig() : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity){
        http.csrf().disable()
        http.authorizeRequests()




                // anyone
            .antMatchers("/users/**","/search").permitAll()
                // customer
            .antMatchers("/map","/users/customer/**","/reviews/**","/likedshop/**","/users/cart/**","/like/**").hasRole("CUSTOMER")

                // seller
            .antMatchers("/sellers/**","/seller/**","/image/**").hasRole("SELLER")


                // authenticated
            .antMatchers("/users/edit","/users/logout").authenticated()

                // anyone
            .antMatchers("/home","/").permitAll()
//
//
            .antMatchers("/css/**","/js/**","/images/**","/webfonts/**").access("permitAll")

            .antMatchers("/users/passowrd").permitAll()
            .anyRequest().authenticated()


            .and()
            .formLogin()
            .loginPage("/users/login")
            .defaultSuccessUrl("/home",true)
            .usernameParameter("userEmail")
            .passwordParameter("password")
            .loginProcessingUrl("/users/login")
            .and()
            .logout()
            .logoutUrl("/users/logout")
            .logoutSuccessUrl("/home")


    }

    @Autowired
    private lateinit var userDetailsServiceImpl: UserDetailsServiceImpl

    @Bean
    fun encoder() = BCryptPasswordEncoder()

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(userDetailsServiceImpl)
            .passwordEncoder(encoder())
    }

}