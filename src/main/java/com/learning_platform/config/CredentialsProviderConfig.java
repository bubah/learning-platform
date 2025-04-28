package com.learning_platform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

@Configuration
public class CredentialsProviderConfig {

    @Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-key}")
    private String secretKey;

    @Value("${learning.platform.environment}")
    private String environment;

    @Bean
    public AwsCredentialsProvider credentialsProvider() {
        // if spring profile is "local", use StaticCredentialsProvider
        // otherwise use DefaultCredentialsProvider

        if (environment.equals("local")) {
            return StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKey, secretKey)
            );
        } else {
            return DefaultCredentialsProvider.create();
        }
    }
}
