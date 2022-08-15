package com.heimdallr.gateway.config;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//import com.heimdallr.properties.SecurityProperties;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
    private static final String PRIV_API_PATTERN = "/priv/**";
    private static final String PUBL_API_PATTERN = "/pub/**";
    private static final String PUBLIC_PATTERN = "/v1/pub/**";
    
    private CustomAccessTokenConverter customAccessTokenConverter;
    //private SecurityProperties securityProperties;
    
	public ResourceServerConfig(CustomAccessTokenConverter customAccessTokenConverter
								/*SecurityProperties securityProperties*/) {
		// TODO Auto-generated constructor stub
		this.customAccessTokenConverter = customAccessTokenConverter;
		//this.securityProperties = securityProperties;
	}
    
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.formLogin().disable();
		
		http.authorizeRequests()
			.antMatchers(PUBLIC_PATTERN).permitAll()
			.antMatchers(PUBL_API_PATTERN).permitAll()
			.antMatchers(HttpMethod.GET, PRIV_API_PATTERN).access("#oauth2.hasScope('read')")
			.antMatchers(HttpMethod.POST, PRIV_API_PATTERN).access("#oauth2.hasScope('write')")
			.antMatchers(HttpMethod.PATCH, PRIV_API_PATTERN).access("#oauth2.hasScope('write')")
			.antMatchers(HttpMethod.PUT, PRIV_API_PATTERN).access("#oauth2.hasScope('write')")
			.antMatchers(HttpMethod.DELETE, PRIV_API_PATTERN).access("#oauth2.hasScope('write')")
			.and()
			.authorizeRequests()
			.anyRequest().authenticated();
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		// TODO Auto-generated method stub
		super.configure(resources);
	}
	
	@Bean
	public ResourceServerTokenServices tokenService() {
		return new CustomRemoteTokenService(/*this.securityProperties*/);
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
		authenticationManager.setTokenServices(tokenService());
		
		return authenticationManager;
	}
	
	@Bean
	public TokenStore tokenStore() {
		
		return new JwtTokenStore(/*accessTokenConverter()*/ null);
	}
	
//	@Bean
//	public JwtAccessTokenConverter accessTokenConverter() {
//		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//		converter.setAccessTokenConverter(customAccessTokenConverter);
//		
//		final Resource resource = new ClassPathResource("certificate/pubkey.txt");
//		String publicKey = null;
//		
//		try {
//			publicKey = IOUtils.toString(resource.getInputStream(), Charset.defaultCharset());
//		} catch (final IOException e) {
//			// TODO: handle exception
//			throw new UncheckedIOException(e);
//		}
//		
//		converter.setVerifierKey(publicKey);
//		
//		return converter;
//	}
}
