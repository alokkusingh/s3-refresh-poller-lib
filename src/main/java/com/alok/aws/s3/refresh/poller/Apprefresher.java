package com.alok.aws.s3.refresh.poller;

import org.slf4j.Logger;
import software.amazon.awssdk.services.s3.S3Client;


@FunctionalInterface
public interface Apprefresher {

     void refresh();
     default void checkForRefresh(Logger logger, S3Client s3Client, String bucketName, String objectKey) {
         logger.info("Checking refresh needed for {}::{}::{}", bucketName, objectKey, S3RefreshPoller.getInstance().getLastRefreshTime());
         // TODO: check refresh condition
          refresh();

         //TODO: set the timestamp from S3
         // in case not able to read then???
         S3RefreshPoller.getInstance().setLastRefreshTime(1697289565);
     }
}
