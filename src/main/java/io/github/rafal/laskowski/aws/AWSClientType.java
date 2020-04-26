package io.github.rafal.laskowski.aws;

public enum AWSClientType {
    AWS,
    LOCALSTACK;

    private static final String AWS_STRING = AWS.name().toUpperCase();
    private static final String LOCALSTACK_STRING = LOCALSTACK.name().toUpperCase();
    private static final String AWS_CLIENT_PROPERTY_NAME = "aws.client.type";
    private static final IllegalArgumentException EXCEPTION = new IllegalArgumentException(String.format("Illegal Client Type. Use [%s] or [%s] within property [%s]", AWS_STRING, LOCALSTACK_STRING, AWS_CLIENT_PROPERTY_NAME));

    public static AWSClientType get() {
        String property = System.getProperty(AWS_CLIENT_PROPERTY_NAME);
        return parse(property);
    }

    public static AWSClientType parse(String input) {
        if (input != null) {
            if (input.equals(AWS_STRING)) {
                return AWS;
            } else if (input.equals(LOCALSTACK_STRING)) {
                return LOCALSTACK;
            }
        }

        throw EXCEPTION;
    }
}
