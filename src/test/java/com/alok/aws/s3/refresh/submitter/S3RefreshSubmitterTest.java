package com.alok.aws.s3.refresh.submitter;

import com.alok.aws.s3.refresh.AppType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class S3RefreshSubmitterTest {

    @Mock
    private S3Client s3Client;

    @BeforeAll
    static void setup() {
    }
    @Test
    void getInstance() {
        S3RefreshSubmitter instance = S3RefreshSubmitter.getInstance(
                s3Client,
                "abc-bucket",
                "key-1",
                "key-2"
        );

        assertNotNull(instance);
    }

    @Test
    void builder() {
        S3RefreshSubmitterBuilder builder = S3RefreshSubmitter.builder();
        assertNotNull(builder);
    }

    @Test
    void submitRefresh() {

//        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class))).thenReturn(PutObjectResponse.builder().build());
//        S3RefreshSubmitter s3RefreshSubmitter = S3RefreshSubmitter.getInstance(
//                s3Client,
//                "abc-bucket",
//                "key-1",
//                "key-2"
//        );
//
//        assertDoesNotThrow(() -> s3RefreshSubmitter.submitRefresh(AppType.APPB));
    }

    @Test
    void testSubmitRefresh() {
    }
}