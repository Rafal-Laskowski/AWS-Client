package io.github.rafal.laskowski.aws.config;

public interface LocalstackConfiguration extends AWSConfiguration {
    int getDynamoDBport();
    int getSQSport();
    int getLambdaPort();
    int getS3port();
    String getLocalstackUrl();
}
