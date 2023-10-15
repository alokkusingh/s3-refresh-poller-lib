package com.alok.aws.s3.refresh.poller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@FunctionalInterface
public interface Apprefresher {
    final Logger log = LoggerFactory.getLogger(Apprefresher.class);

     void refresh();
     default void checkForRefresh(S3Client s3Client, String bucketName, String objectKey) {
         log.info("App refreshed at {}::{}::{}", bucketName, objectKey, S3RefreshPoller.getInstance().getLastRefreshTime());
         ResponseInputStream<GetObjectResponse> s3objectResponse = s3Client.getObject(GetObjectRequest.builder()
                         .bucket(bucketName)
                         .key(objectKey)
                         .build()
                 );

         BufferedReader reader = new BufferedReader(new InputStreamReader(s3objectResponse));

         Long latestRefreshTimestamp = null;
         try {
             latestRefreshTimestamp = Long.valueOf(reader.readLine());
         } catch (IOException e) {
             log.warn("Refresh tracker read failed, error: {}", e.getMessage());
             log.debug(e.getStackTrace().toString());
             return;
         } finally {
             try {
                 reader.close();
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
         }

         log.info("Data refreshed at {}::{}::{}", bucketName, objectKey, latestRefreshTimestamp);

         if ( S3RefreshPoller.getInstance().getLastRefreshTime() <  latestRefreshTimestamp) {
             refresh();
             S3RefreshPoller.getInstance().setLastRefreshTime(latestRefreshTimestamp);
         } else {
             log.info("App is up to date");
         }
     }
}
