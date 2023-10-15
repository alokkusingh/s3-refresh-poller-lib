package com.alok.aws.s3.refresh.submitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class S3RefreshSubmitter {

    private final Logger log = LoggerFactory.getLogger(S3RefreshSubmitter.class);

    private S3Client s3Client;
    private String bucketName;
    private String appARefreshTrackerObjectKey;
    private String appBRefreshTrackerObjectKey;

    private static S3RefreshSubmitter instance;

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

    public void submitRefreshServiceA() {
       submitRefreshServiceA(getEpochUtc());
    }

    public void submitRefreshServiceB() {
        submitRefreshServiceB(getEpochUtc());
    }

    public void submitRefreshServiceAll() {
        long epochTime = getEpochUtc();
        submitRefreshServiceA(epochTime);
        submitRefreshServiceB(epochTime);
    }

    private long getEpochUtc() {
        return Instant.now().getEpochSecond();
    }
    public void submitRefreshServiceA(long epochTime) {
        System.out.println("submitRefreshServiceA::");
        log.info("submitRefreshServiceA::{}::{}::{}", bucketName, appARefreshTrackerObjectKey, epochTime);

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(appARefreshTrackerObjectKey)
                        .build(),
                RequestBody.fromBytes(Long.toString(epochTime).getBytes(StandardCharsets.UTF_8))
        );
    }

    public void submitRefreshServiceB(long epochTime) {
        log.info("submitRefreshServiceB::{}::{}::{}", bucketName, appBRefreshTrackerObjectKey, epochTime);

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(appBRefreshTrackerObjectKey)
                        .build(),
                RequestBody.fromBytes(Long.toString(epochTime).getBytes(StandardCharsets.UTF_8))
        );
    }
}
