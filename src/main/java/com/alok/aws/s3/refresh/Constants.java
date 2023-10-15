package com.alok.aws.s3.refresh;

public class Constants {

    private Constants() {}

    public static final String AWS_S3_REFRESH_TRACKER_OBJECT_KEY_APPA = "refresh/appA.json";
    public static final String AWS_S3_REFRESH_TRACKER_OBJECT_KEY_APPB = "refresh/appB.json";
    public static final Long AWS_S3_REFRESH_POLLER_INTERVAL_DEFAULT = 300L;
    public static final Long AWS_S3_REFRESH_POLLER_INITIAL_DELAY_DEFAULT = 300L;
}
