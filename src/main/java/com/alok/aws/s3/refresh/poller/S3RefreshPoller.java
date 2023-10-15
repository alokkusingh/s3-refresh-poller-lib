package com.alok.aws.s3.refresh.poller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.s3.S3Client;
import java.util.concurrent.*;

public class S3RefreshPoller {
    private final Logger log = LoggerFactory.getLogger(S3RefreshPoller.class);

    private S3Client s3Client;
    private String bucketName;
    private String appRefreshTrackerObjectKey;
    private Long pollingInterval;
    private Long pollingInitialDelay;
    private Apprefresher apprefresher;
    private boolean listenerRegistered;
    private static S3RefreshPoller instance;
    private long lastRefreshTime;

    private S3RefreshPoller(
            S3Client s3Client,
            String bucketName,
            String appRefreshTrackerObjectKey,
            Long pollingInterval,
            Long pollingInitialDelay
            ) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.appRefreshTrackerObjectKey = appRefreshTrackerObjectKey;
        this.pollingInterval = pollingInterval;
        this.pollingInitialDelay = pollingInitialDelay;
    }

    protected static S3RefreshPoller getInstance(
            S3Client s3Client,
            String bucketName,
            String appRefreshTrackerObjectKey,
            Long pollingInterval,
            Long pollingInitialDelay
    ) {
        if (instance == null) {
            synchronized (S3RefreshPoller.class) {
                if (instance == null) {
                    instance = new S3RefreshPoller(s3Client, bucketName, appRefreshTrackerObjectKey, pollingInterval, pollingInitialDelay);
                }
            }
        }

        return instance;
    }

    protected static S3RefreshPoller getInstance() {
        return instance;
    }

    protected long getLastRefreshTime() {
        return lastRefreshTime;
    }

    protected long setLastRefreshTime(long lastRefreshTime) {
        log.info("setLastRefreshTime::{}", lastRefreshTime);
        return this.lastRefreshTime = lastRefreshTime;
    }

    public static S3RefreshPollerBuilder builder() {
        return new S3RefreshPollerBuilder();
    }

    public synchronized void registerListener(Apprefresher apprefresher) {

        if (listenerRegistered) {
            log.warn("Refresh listener already registered!");
            return;
        }
        this.apprefresher = apprefresher;
        this.listenerRegistered = true;

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        log.info("registerListener::scheduling::{}::{}::{}", bucketName, appRefreshTrackerObjectKey, pollingInterval);
        scheduledExecutorService.scheduleWithFixedDelay(
                () -> apprefresher.checkForRefresh(s3Client, bucketName, appRefreshTrackerObjectKey),
                pollingInterval,
                pollingInitialDelay,
                TimeUnit.SECONDS
        );
    }
}
