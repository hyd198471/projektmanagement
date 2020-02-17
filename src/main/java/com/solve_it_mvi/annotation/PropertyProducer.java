package com.solve_it_mvi.annotation;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyProducer {
    private Properties properties;

    @Property
    @Produces
    public String produceString(final InjectionPoint ip) {
        return this.properties.getProperty(getKey(ip));
    }

    @Property
    @Produces
    public int produceInt(final InjectionPoint ip) {
        return Integer.parseInt(this.properties.getProperty(getKey(ip)));
    }

    @Property
    @Produces
    public Long produceLong(final InjectionPoint ip) {
        return Long.parseLong(this.properties.getProperty(getKey(ip)));
    }

    @Property
    @Produces
    public boolean produceBoolean(final InjectionPoint ip) {
        return Boolean.parseBoolean(this.properties.getProperty(getKey(ip)));
    }

    private String getKey(InjectionPoint ip) {
        boolean keyExisted = ip.getAnnotated().isAnnotationPresent(Property.class) &&
                !ip.getAnnotated().getAnnotation(Property.class).value().isEmpty();
        return keyExisted
                ? ip.getAnnotated().getAnnotation(Property.class).value()
                : ip.getMember().getName();
    }

    @PostConstruct
    public void init() {
        this.properties = new Properties();
        InputStream inputStream = PropertyProducer.class.getResourceAsStream("/META-INF/application.properties");
        if (inputStream == null) {
            throw new RuntimeException("No properties!!!");
        }
        try {
            this.properties.load(inputStream);
        } catch (final IOException e) {
            throw new RuntimeException("Configuration could not be loaded!");
        }
    }

}
