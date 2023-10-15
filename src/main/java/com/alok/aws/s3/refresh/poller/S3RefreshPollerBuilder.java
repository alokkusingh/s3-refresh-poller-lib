package com.alok.aws.s3.refresh.poller;

import com.alok.aws.s3.refresh.AppType;
import com.alok.aws.s3.refresh.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.Objects;

public class S3RefreshPollerBuilder {
    private final Logger log = LoggerFactory.getLogger(S3RefreshPollerBuilder.class);

    private S3Client s3Client;
    private String bucketName;
    private AppType appType;
    private Long pollingInterval;
    private Long pollingInitialDelay;

    public S3RefreshPollerBuilder setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public S3RefreshPollerBuilder setS3Client(S3Client s3Client) {
        this.s3Client = s3Client;
        return this;
    }

    public S3RefreshPollerBuilder setAppType(AppType appType) {
        this.appType = appType;
        return this;
    }

    public S3RefreshPollerBuilder setPollingInterval(Long pollingInterval) {
        this.pollingInterval = pollingInterval;
        return this;
    }

    public S3RefreshPollerBuilder setPollingInitialDelay(Long pollingInitialDelay) {
        this.pollingInitialDelay = pollingInitialDelay;
        return this;
    }

    public S3RefreshPoller build() {
        if (Objects.isNull(s3Client)) {
            throw new AssertionError("S3Client object must be provided");
        }
        if (Objects.isNull(bucketName)) {
            throw new AssertionError("bucket name must be provided");
        }
        if (Objects.isNull(appType)) {
            throw new AssertionError("appType can't be null");
        }

        if (pollingInterval == null) {
            pollingInterval = Constants.AWS_S3_REFRESH_POLLER_INTERVAL_DEFAULT;
        }
        if (pollingInitialDelay == null) {
            pollingInitialDelay = Constants.AWS_S3_REFRESH_POLLER_INITIAL_DELAY_DEFAULT;
        }

        String s3ObjectKeyName = null;
        if (appType == AppType.APPA) {
            s3ObjectKeyName = Constants.AWS_S3_REFRESH_TRACKER_OBJECT_KEY_APPA;
        }
        if (appType == AppType.APPB) {
            s3ObjectKeyName = Constants.AWS_S3_REFRESH_TRACKER_OBJECT_KEY_APPB;
        }
        return S3RefreshPoller.getInstance(s3Client, bucketName, s3ObjectKeyName, pollingInterval, pollingInitialDelay);
    }
}
