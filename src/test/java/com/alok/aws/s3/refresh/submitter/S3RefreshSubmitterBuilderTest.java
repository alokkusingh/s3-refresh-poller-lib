package com.alok.aws.s3.refresh.submitter;

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

import static org.junit.jupiter.api.Assertions.*;

class S3RefreshSubmitterBuilderTest {
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
    void setBucketName() {
        S3RefreshSubmitterBuilder s3RefreshSubmitterBuilder = S3RefreshSubmitter.builder();
        s3RefreshSubmitterBuilder = s3RefreshSubmitterBuilder.setBucketName("abc-bucket");

        assertNotNull(s3RefreshSubmitterBuilder);
    }

    @Test
    void setS3Client() {
        S3RefreshSubmitterBuilder s3RefreshSubmitterBuilder = S3RefreshSubmitter.builder();
        s3RefreshSubmitterBuilder = s3RefreshSubmitterBuilder.setS3Client(mockedS3Client);

        assertNotNull(s3RefreshSubmitterBuilder);
    }

    @Test
    void build() {
        assertThrows(AssertionError.class, () -> S3RefreshSubmitter.builder().build());
        assertThrows(AssertionError.class, () -> S3RefreshSubmitter.builder()
                .setS3Client(mockedS3Client)
                .build());
        assertThrows(AssertionError.class, () -> S3RefreshSubmitter.builder()
                .setBucketName("abc-bucket")
                .build());
        S3RefreshSubmitter s3RefreshSubmitter = S3RefreshSubmitter.builder()
                .setBucketName("abc-bucket")
                .setS3Client(mockedS3Client)
                .build();

        assertNotNull(s3RefreshSubmitter);
        
    }
}