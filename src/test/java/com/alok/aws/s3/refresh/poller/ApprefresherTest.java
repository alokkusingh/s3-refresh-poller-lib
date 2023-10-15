package com.alok.aws.s3.refresh.poller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ApprefresherTest {

    static S3Client mockedS3Client = null;

    @BeforeAll
    static void setup() {
        mockedS3Client = new S3Client() {
            @Override
            public String serviceName() {
                return null;
            }

            @Override
            public void close() {

            }

            public PutObjectResponse putObject(PutObjectRequest putObjectRequest, RequestBody requestBody) {
                return null;
            }

            public ResponseInputStream<GetObjectResponse> getObject(GetObjectRequest getObjectRequest) throws NoSuchKeyException, InvalidObjectStateException, AwsServiceException, SdkClientException, S3Exception {
                return new ResponseInputStream<GetObjectResponse>(
                        GetObjectResponse.builder().build(),
                        new ByteArrayInputStream("12345678".getBytes()));
            }
        };
    }
    @Test
    void checkForRefresh() {
        S3RefreshPoller instance = S3RefreshPoller.getInstance(
                mockedS3Client,
                "abc-bucket",
                "key-1",
                300L,
                300L
        );
        Apprefresher apprefresher = new Apprefresher() {
            @Override
            public void refresh() {

            }
        };

        apprefresher.checkForRefresh(mockedS3Client, "abc-bucket", "key-1");
    }
}