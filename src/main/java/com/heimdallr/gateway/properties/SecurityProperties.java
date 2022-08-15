package com.heimdallr.gateway.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties("security.oauth2.server")
@EnableConfigurationProperties
public class SecurityProperties {
	private String clientId;
	private String clientSecret;
	private String serverName;
	private String serverCheckTokenUri;
}
