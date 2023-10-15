package com.alok.aws.s3.refresh.poller;

import com.alok.aws.s3.refresh.AppType;
import com.alok.aws.s3.refresh.submitter.S3RefreshSubmitter;
import com.alok.aws.s3.refresh.submitter.S3RefreshSubmitterBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.s3.S3Client;

import static org.junit.jupiter.api.Assertions.*;

class S3RefreshPollerBuilderTest {

    @BeforeAll
    static void setup() {
    }
    @Test
    void setBucketName() {
        S3RefreshPollerBuilder s3RefreshPollerBuilder = S3RefreshPoller.builder();
        s3RefreshPollerBuilder = s3RefreshPollerBuilder.setBucketName("abc-bucket");

        assertNotNull(s3RefreshPollerBuilder);
    }

    @Test
    void setS3Client() {
        S3RefreshPollerBuilder s3RefreshPollerBuilder = S3RefreshPoller.builder();
        s3RefreshPollerBuilder = s3RefreshPollerBuilder.setS3Client(S3Client.create());

        assertNotNull(s3RefreshPollerBuilder);
    }

    @Test
    void setAppType() {
        S3RefreshPollerBuilder s3RefreshPollerBuilder = S3RefreshPoller.builder();
        s3RefreshPollerBuilder = s3RefreshPollerBuilder.setAppType(AppType.APPA);

        assertNotNull(s3RefreshPollerBuilder);
    }

    @Test
    void setPollingInterval() {
        S3RefreshPollerBuilder s3RefreshPollerBuilder = S3RefreshPoller.builder();
        s3RefreshPollerBuilder = s3RefreshPollerBuilder.setPollingInterval(300L);

        assertNotNull(s3RefreshPollerBuilder);
    }

    @Test
    void setPollingInitialDelay() {
        S3RefreshPollerBuilder s3RefreshPollerBuilder = S3RefreshPoller.builder();
        s3RefreshPollerBuilder = s3RefreshPollerBuilder.setPollingInitialDelay(300L);

        assertNotNull(s3RefreshPollerBuilder);
    }

    @Test
    void build() {
        assertThrows(AssertionError.class, () -> S3RefreshPoller.builder().build());
        assertThrows(AssertionError.class, () -> S3RefreshPoller.builder()
                .setS3Client(S3Client.create())
                .build());
        assertThrows(AssertionError.class, () -> S3RefreshPoller.builder()
                .setBucketName("abc-bucket")
                .build());
        assertThrows(AssertionError.class, () -> S3RefreshPoller.builder()
                .setS3Client(S3Client.create())
                .setBucketName("abc-bucket")
                .build());

        S3RefreshPoller s3RefreshPoller = S3RefreshPoller.builder()
                .setBucketName("abc-bucket")
                .setS3Client(S3Client.create())
                .setAppType(AppType.APPA)
                .setPollingInitialDelay(300L)
                .setPollingInterval(300L)
                .build();
        assertNotNull(s3RefreshPoller);

        s3RefreshPoller = S3RefreshPoller.builder()
                .setBucketName("abc-bucket")
                .setS3Client(S3Client.create())
                .setAppType(AppType.APPB)
                .build();
        assertNotNull(s3RefreshPoller);
    }
}