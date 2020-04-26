package io.github.rafal.laskowski.aws.config;

import java.lang.reflect.InvocationTargetException;

public class ConfigurationLoader {
    private static final String PROPERTY_NAME = "aws.client.configuration";

    public static Object loadConfiguration() {
        String configurationClass = System.getProperty(PROPERTY_NAME);

        if (configurationClass != null) {
            try {
                Class<?> clazz = Class.forName(configurationClass);
                return clazz.getConstructor().newInstance();

            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException(e);
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            if (AWSClientType.get().equals(AWSClientType.LOCALSTACK)) {
                return new DefaultLocalstackConfiguration();
            }
        }

        throw new IllegalArgumentException(String.format("You need to provide a class which implements [%s] or [%s] interface and set property [%s] with the class to tell AWSClient how to load the configuration", LocalstackConfiguration.class.getSimpleName(), AWSConfiguration.class.getSimpleName(), PROPERTY_NAME));
    }
}
