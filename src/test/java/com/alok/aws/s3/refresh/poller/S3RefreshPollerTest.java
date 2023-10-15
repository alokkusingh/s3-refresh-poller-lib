package com.alok.aws.s3.refresh.poller;

import com.alok.aws.s3.refresh.submitter.S3RefreshSubmitter;
import com.alok.aws.s3.refresh.submitter.S3RefreshSubmitterBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import software.amazon.awssdk.services.s3.S3Client;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class S3RefreshPollerTest {

    @Mock
    private S3Client s3Client;

    @BeforeAll
    static void setup() {
    }
    @Test
    void getInstance() {
        S3RefreshPoller instance = S3RefreshPoller.getInstance(
                s3Client,
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