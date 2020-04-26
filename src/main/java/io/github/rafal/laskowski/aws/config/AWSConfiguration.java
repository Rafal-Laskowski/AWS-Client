package io.github.rafal.laskowski.aws.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Regions;

public interface AWSConfiguration {
    Regions getRegion();
    AWSCredentials getCredentials();
}
