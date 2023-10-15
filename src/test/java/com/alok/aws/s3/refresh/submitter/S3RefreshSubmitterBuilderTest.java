package com.alok.aws.s3.refresh.submitter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import software.amazon.awssdk.services.s3.S3Client;

import static org.junit.jupiter.api.Assertions.*;

class S3RefreshSubmitterBuilderTest {
    @BeforeAll
    static void setup() {
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
        s3RefreshSubmitterBuilder = s3RefreshSubmitterBuilder.setS3Client(S3Client.create());

        assertNotNull(s3RefreshSubmitterBuilder);
    }

    @Test
    void build() {
        assertThrows(AssertionError.class, () -> S3RefreshSubmitter.builder().build());
        assertThrows(AssertionError.class, () -> S3RefreshSubmitter.builder()
                .setS3Client(S3Client.create())
                .build());
        assertThrows(AssertionError.class, () -> S3RefreshSubmitter.builder()
                .setBucketName("abc-bucket")
                .build());
        S3RefreshSubmitter s3RefreshSubmitter = S3RefreshSubmitter.builder()
                .setBucketName("abc-bucket")
                .setS3Client(S3Client.create())
                .build();

        assertNotNull(s3RefreshSubmitter);
        
    }
}