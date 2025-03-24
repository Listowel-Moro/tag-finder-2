package com.listo.tag_finder.service;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import org.springframework.stereotype.Service;

@Service
public class ParameterStoreService {
    private final SsmClient ssmClient;

    public ParameterStoreService() {
        this.ssmClient = SsmClient.builder()
                .region(Region.US_WEST_2)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    public String getParameter(String name) {
        GetParameterRequest request = GetParameterRequest.builder()
                .name(name)
                .withDecryption(true)
                .build();

        return ssmClient.getParameter(request).parameter().value();
    }
}
