package io.github.rafal.laskowski.aws.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;

public class DefaultLocalstackConfiguration implements LocalstackConfiguration {

    @Override
    public int getDynamoDBport() {
        return 4569;
    }

    @Override
    public int getSQSport() {
        return 4576;
    }

    @Override
    public int getLambdaPort() {
        return 4574;
    }

    @Override
    public int getS3port() {
        return 4572;
    }

    @Override
    public Regions getRegion() {
        return Regions.US_EAST_1;
    }

    @Override
    public AWSCredentials getCredentials() {
        return new BasicAWSCredentials("local", "stack");
    }

    @Override
    public String getLocalstackUrl() {
        return "http://localhost";
    }
}
