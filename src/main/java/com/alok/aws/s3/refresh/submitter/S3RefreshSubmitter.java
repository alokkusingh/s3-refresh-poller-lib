package com.alok.aws.s3.refresh.submitter;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;

import java.time.Instant;

@Slf4j
public class S3RefreshSubmitter {

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

        //TODO: implement S3 update

    }

    public void submitRefreshServiceB(long epochTime) {
        log.info("submitRefreshServiceB::{}::{}::{}", bucketName, appBRefreshTrackerObjectKey, epochTime);

        //TODO: implement S3 update
    }
}
