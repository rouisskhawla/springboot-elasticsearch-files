package com.demo.api.elastic.files.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.ClientConfiguration.MaybeSecureClientConfigurationBuilder;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

/**
 * Configuration for authenticating with elasticsearch where xpack security is
 * enabled
 * 
 * @author Khawla Rouis
 * @version 1.0
 * @since Nov 01, 2025
 **/

@Configuration
class ElasitcSearchConfig extends ElasticsearchConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(ElasitcSearchConfig.class);

	@Value("${elasticsearch.host}")
	private String host;

	@Value("${elasticsearch.username}")
	private String username;

	@Value("${elasticsearch.password}")
	private String password;
	

	@Override
	public ClientConfiguration clientConfiguration() {
		try {
			return ((MaybeSecureClientConfigurationBuilder) ClientConfiguration.builder()
					.connectedTo(host)
					.withBasicAuth(username, password))
					.usingSsl()
					.build();
		 	} catch (Exception e) {
				logger.error("Connection with elasticsearch host failed", e);
	            return null;
	        }
	}

}
