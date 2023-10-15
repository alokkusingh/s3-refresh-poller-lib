package com.alok.aws.s3.refresh.submitter;

import com.alok.aws.s3.refresh.AppType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Objects;

public class S3RefreshSubmitter {

    private final Logger log = LoggerFactory.getLogger(S3RefreshSubmitter.class);

    private static volatile S3RefreshSubmitter instance;
    private S3Client s3Client;
    private String bucketName;
    private String appARefreshTrackerObjectKey;
    private String appBRefreshTrackerObjectKey;


    private S3RefreshSubmitter(
            S3Client s3Client,
            String bucketName,
            String appARefreshTrackerObjectKey,
            String appBRefreshTrackerObjectKey
            ) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.appARefreshTrackerObjectKey = appARefreshTrackerObjectKey;
        this.appBRefreshTrackerObjectKey = appBRefreshTrackerObjectKey;
    }

    protected static S3RefreshSubmitter getInstance(
            S3Client s3Client,
            String bucketName,
            String appARefreshTrackerObjectKey,
            String appBRefreshTrackerObjectKey
    ) {
        if (instance == null) {
            synchronized (S3RefreshSubmitter.class) {
                if (instance == null) {
                    instance = new S3RefreshSubmitter(s3Client, bucketName, appARefreshTrackerObjectKey, appBRefreshTrackerObjectKey);
                }
            }
        }

        return instance;
    }

    public static S3RefreshSubmitterBuilder builder() {
        return new S3RefreshSubmitterBuilder();
    }

    public void submitRefresh(AppType appType) {
       submitRefresh(appType, getEpochUtc());
    }

    public void submitRefresh() {
        long epochTime = getEpochUtc();
        submitRefresh(AppType.APPA, epochTime);
        submitRefresh(AppType.APPB, epochTime);
    }

    private void submitRefresh(AppType appType, long epochTime) {
        log.info("submitRefreshService::{}::{}::{}", appType, bucketName, epochTime);
        if (Objects.isNull(appType)) {
            throw new AssertionError("appType can't be null");
        }

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(appType == AppType.APPA? appARefreshTrackerObjectKey : appBRefreshTrackerObjectKey)
                        .build(),
                RequestBody.fromBytes(Long.toString(epochTime).getBytes(StandardCharsets.UTF_8))
        );
    }

    private long getEpochUtc() {
        return Instant.now().getEpochSecond();
    }
}
