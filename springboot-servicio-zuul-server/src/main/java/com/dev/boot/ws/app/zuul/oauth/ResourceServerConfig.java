package com.dev.boot.ws.app.zuul.oauth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@RefreshScope
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Value("${config.security.oauth.jwt.key}")
	private String jwtKey;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/api/security/oauth/**")
			.permitAll()
			.antMatchers(HttpMethod.GET, "/api/productos/api/productos/listar", "/api/v1/item/api/v1/item/listar", "/api/usuarios/usuarios").permitAll()
			.antMatchers(HttpMethod.GET, "/api/productos/api/productos/getOne/{id}", 
					"/api/v1/item/api/v1/item/getOne/{id}/cantidad/{cantidad}",
					"/api/usuarios/usuarios/{id}").hasAnyRole("ADMIN", "USER")
			.antMatchers(HttpMethod.POST, "/api/productos/api/productos/saveProducto",
					"/api/v1/item/api/v1/item/crear",
					"/api/usuarios/usuarios").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, "/api/productos/api/productos/editar/{id}",
					"/api/v1/item/api/v1/item/editar/{id}",
					"/api/usuarios/usuarios/{id}").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/api/productos/api/productos/eliminar/{id}",
					"/api/v1/item/api/v1/item/eliminar/{id}",
					"/api/usuarios/usuarios/{id}").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and()
			.cors().configurationSource(corsConfigurationSource());
			
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(Arrays.asList("*"));
		corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(
				new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
		
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(jwtKey);
		return tokenConverter;
	}
}

