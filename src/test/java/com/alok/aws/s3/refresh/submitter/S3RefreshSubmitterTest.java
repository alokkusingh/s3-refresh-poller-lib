package com.alok.aws.s3.refresh.submitter;

import com.alok.aws.s3.refresh.AppType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class S3RefreshSubmitterTest {

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
        S3RefreshSubmitter instance = S3RefreshSubmitter.getInstance(
                mockedS3Client,
                "abc-bucket",
                "key-1",
                "key-2"
        );

        assertNotNull(instance);
    }

    @Test
    void builder() {
        S3RefreshSubmitterBuilder builder = S3RefreshSubmitter.builder();
        assertNotNull(builder);
    }

    @Test
    void submitRefresh() {

        S3RefreshSubmitter s3RefreshSubmitter = S3RefreshSubmitter.builder()
                .setBucketName("abc-bucket")
                .setS3Client(mockedS3Client)
                .build();

        assertDoesNotThrow(
                () -> s3RefreshSubmitter.submitRefresh(AppType.APPB)
        );

        assertDoesNotThrow(
                () -> s3RefreshSubmitter.submitRefresh()
        );
    }
}