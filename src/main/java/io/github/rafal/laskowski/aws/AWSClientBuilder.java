package io.github.rafal.laskowski.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.client.builder.AwsSyncClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

class AWSClientBuilder<T extends AwsSyncClientBuilder<T, V>, V> {
  private final T awsClient;
  private final Service service;

  private AWSClientBuilder(T awsClient, Service service) {
    this.awsClient = awsClient;
    this.service = service;
  }

  public AWSClientBuilder<T, V> withLocalEndpointConfiguration(String endpoint, int port, Regions region) {
    awsClient.withEndpointConfiguration(
        new AwsClientBuilder.EndpointConfiguration(
                endpoint.concat(":").concat(String.valueOf(port)), region.getName()));
    return this;
  }

  public AWSClientBuilder<T, V> withRemoteEndpointConfiguration(Regions region) {
    awsClient.withRegion(region);
    return this;
  }

  public AWSClientBuilder<T, V> withCredentials(AWSCredentials credentials) {
    awsClient.withCredentials(new AWSStaticCredentialsProvider(credentials));
    return this;
  }

  public Service getService() {
    return service;
  }

  public V build() {
    return awsClient.build();
  }

  public static AWSClientBuilder<AmazonDynamoDBClientBuilder, AmazonDynamoDB> dynamodb() {
    return new AWSClientBuilder<>(AmazonDynamoDBClientBuilder.standard(), Service.DYNAMODB);
  }

  public static AWSClientBuilder<AWSLambdaClientBuilder, AWSLambda> lambda() {
    return new AWSClientBuilder<>(AWSLambdaClientBuilder.standard(), Service.LAMBDA);
  }

  public static AWSClientBuilder<AmazonSQSClientBuilder, AmazonSQS> sqs() {
    return new AWSClientBuilder<>(AmazonSQSClientBuilder.standard(), Service.SQS);
  }

  public static AWSClientBuilder<AmazonS3ClientBuilder, AmazonS3> s3() {
    AmazonS3ClientBuilder clientBuilder = AmazonS3ClientBuilder.standard();
    clientBuilder.withPathStyleAccessEnabled(true);

    return new AWSClientBuilder<>(clientBuilder, Service.S3);
  }
}
