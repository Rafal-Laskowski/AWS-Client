package io.github.rafal.laskowski.aws;

import com.amazonaws.client.builder.AwsSyncClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.sqs.AmazonSQS;
import io.github.rafal.laskowski.aws.config.AWSConfiguration;
import io.github.rafal.laskowski.aws.config.ConfigurationLoader;
import io.github.rafal.laskowski.aws.config.LocalstackConfiguration;

import java.util.Objects;

public class AWSClient {
    private static final Object configuration = ConfigurationLoader.loadConfiguration();

    public static AmazonSQS sqs() {
        return configure(AWSClientBuilder.sqs()).build();
    }

    public static AmazonDynamoDB dynamoDB() {
        return configure(AWSClientBuilder.dynamodb()).build();
    }

    public static AWSLambda lambda() {
        return configure(AWSClientBuilder.lambda()).build();
    }

    public static AmazonS3 s3() {
        return configure(AWSClientBuilder.s3()).build();
    }

    private static <T extends AwsSyncClientBuilder<T, V>, V>
    AWSClientBuilder<T, V> configure(AWSClientBuilder<T, V> builder) {

        if (AWSClient.configuration instanceof LocalstackConfiguration) {
            LocalstackConfiguration localstackConfiguration = (LocalstackConfiguration) AWSClient.configuration;

            return builder.withLocalEndpointConfiguration(localstackConfiguration.getLocalstackUrl(), getServicePort(localstackConfiguration, builder.getService()), localstackConfiguration.getRegion()).
                    withCredentials(localstackConfiguration.getCredentials());
        } else {
            AWSConfiguration awsConfiguration = (AWSConfiguration) AWSClient.configuration;

            return builder.withRemoteEndpointConfiguration(Objects.requireNonNull(awsConfiguration).getRegion()).
                    withCredentials(awsConfiguration.getCredentials());
        }
    }

    private static int getServicePort(LocalstackConfiguration configuration, Service service) {
        int servicePort = -1;

        switch(service) {
            case SQS: servicePort = configuration.getSQSport();
                break;
            case DYNAMODB: servicePort = configuration.getDynamoDBport();
                break;
            case S3: servicePort = configuration.getS3port();
                break;
            case LAMBDA: servicePort = configuration.getLambdaPort();
                break;
        }

        return servicePort;
    }
}
