package max.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final AccessDeniedHandler accessDeniedHandler;

	public WebSecurityConfig(AccessDeniedHandler accessDeniedHandler) {
		this.accessDeniedHandler = accessDeniedHandler;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic();
		http
			.authorizeRequests()
				.antMatchers("/", "/*", "/js/**", "/error**", "/css/**", "/img/**")
					.permitAll()
				.antMatchers("/administrative/" , "/administrative/**" , "/administrative/**/**" , "/api/**" , "/api/**/**")
					.hasRole("ADMIN")


				.antMatchers("/administrative/h2-console/**")
					.permitAll()


				.anyRequest()
					.authenticated()
				.and()
					.formLogin()
					.loginPage("/login")
					.defaultSuccessUrl("/app/")
					.failureUrl("/login?error=true")
					.permitAll()
				.and()
					.logout()
					.logoutSuccessUrl("/")
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.permitAll()
				.and()
					.exceptionHandling().accessDeniedHandler(accessDeniedHandler);

		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user =
				User.withDefaultPasswordEncoder()
						.username("admin")
						.password("admin")
						.roles("ADMIN")
						.build();

		return new InMemoryUserDetailsManager(user);
	}

}