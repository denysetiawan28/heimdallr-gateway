package com.heimdallr.gateway.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.hash.Hashing;
//import com.heimdallr.dto.IvSecretDTO;
import com.heimdallr.gateway.properties.SecurityProperties;
//import com.heimdallr.security.AES;


public class CustomRemoteTokenService implements ResourceServerTokenServices {

    private RestOperations restTemplate;

    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
    
//    @Autowired
//    private AES aes;
    
    //private final SecurityProperties prop;
    //private final String KEY;
    
    @Autowired
    public CustomRemoteTokenService(/*SecurityProperties prop*/) {
    	//this.prop = prop;
    	
    	SimpleClientHttpRequestFactory client = new SimpleClientHttpRequestFactory();
		client.setConnectTimeout(10_000);
		client.setReadTimeout(10_000);
    	
        this.restTemplate = new RestTemplate(client);
        
        ((RestTemplate) restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            // Ignore 400
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400) {
                    super.handleError(response);
                }
            }
        });
    }
	
	@Override
	public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
		//HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		
        HttpHeaders headers = new HttpHeaders();
        //String address = this.prop.getServerCheckTokenUri();
        String address = "";
        Map<String, Object> map = executePost(address+"?token=" + accessToken, headers);
        if (map == null || map.isEmpty() || map.get("error") != null) {
            throw new InvalidTokenException("Token not allowed !");
        }
        return tokenConverter.extractAuthentication(map);
	}

	@Override
	public OAuth2AccessToken readAccessToken(String accessToken) {
		// TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not supported: read access token");
	}
	
	@SuppressWarnings("static-access")
	private Map<String, Object> executePost(String path, HttpHeaders headers) {
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        	String now = sdf.format(new Date());
        	System.out.println("path : "+path);
            if (headers.getContentType() == null) {
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                
                //IvSecretDTO apiSecretIV = new IvSecretDTO(this.prop.getClientSecret()+now, this.prop.getClientId()+now);
                
//                String beApiKey = new String(new StringBuilder()
//												.append(this.prop.getClientId()).append("|")
//												.append(this.prop.getClientSecret()).append("|")
//												.append(now));
//                
//                String signature = Hashing.hmacSha512((prop.getClientSecret()+now).getBytes())
//						    					.newHasher()
//						    					.putString(beApiKey, StandardCharsets.UTF_8)
//						    					.hash()
//						    					.toString();
//	                
                //String encryptClient = aes.encrypt(this.prop.getClientId(), this.prop.getServerName() + now, "");
                //String encryptApiKey = aes.encrypt(beApiKey, apiSecretIV.getSecret(), apiSecretIV.getIV());
//                headers.add("x-client", encryptClient);
//                headers.add("x-api-key", encryptApiKey);
//                headers.add("x-signature", signature);
            }
            
            //@SuppressWarnings("rawtypes")
            //Map map = restTemplate.exchange(path, HttpMethod.POST, new HttpEntity<MultiValueMap<String, String>>(null, headers), Map.class).getBody();
            
            //@SuppressWarnings("unchecked")
            //Map<String, Object> result = map;
            Map<String, Object> result = new HashMap<>();
            result.put("msg", "success");
            
            //System.out.println("map : "+map);
            return result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

}
