package com.alok.aws.s3.refresh.poller;

import com.alok.aws.s3.refresh.submitter.S3RefreshSubmitter;
import com.alok.aws.s3.refresh.submitter.S3RefreshSubmitterBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.ByteArrayInputStream;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class S3RefreshPollerTest {

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
    void getInstance() {
        S3RefreshPoller instance = S3RefreshPoller.getInstance(
                mockedS3Client,
                "abc-bucket",
                "key-1",
                300L,
                300L
        );

        assertNotNull(instance);
        instance.setLastRefreshTime(Instant.EPOCH.getEpochSecond());
    }

    @Test
    void builder() {
        S3RefreshPollerBuilder builder = S3RefreshPoller.builder();
        assertNotNull(builder);
    }

    @Test
    void getLastRefreshTime() {
    }

    @Test
    void setLastRefreshTime() {
    }


    @Test
    void registerListener() {
    }
}