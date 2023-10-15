package com.alok.aws.s3.refresh.submitter;

import com.alok.aws.s3.refresh.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import software.amazon.awssdk.services.s3.S3Client;

import java.time.Instant;

public class S3RefreshSubmitterBuilder {

    private final Logger log = LoggerFactory.getLogger(S3RefreshSubmitterBuilder.class);
    private S3Client s3Client;
    private String bucketName;

    public S3RefreshSubmitterBuilder setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public S3RefreshSubmitterBuilder setS3Client(S3Client s3Client) {
        this.s3Client = s3Client;
        return this;
    }

    public S3RefreshSubmitter build() {
        Assert.state(s3Client != null, "S3Client object must be provided");
        Assert.state(bucketName != null, "bucket name must be provided");
        return S3RefreshSubmitter.getInstance(s3Client, bucketName, Constants.AWS_S3_REFRESH_TRACKER_OBJECT_KEY_APPA, Constants.AWS_S3_REFRESH_TRACKER_OBJECT_KEY_APPB);
    }
}
