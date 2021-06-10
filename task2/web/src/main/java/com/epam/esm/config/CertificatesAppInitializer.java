package com.epam.esm.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.ResourceBundle;

/**
 * Web application initialization class.
 *
 * @author Shuleiko Yulia
 */
public class CertificatesAppInitializer implements WebApplicationInitializer {

    private static final String PROFILE_ACTIVE = "spring.profiles.active";
    private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
    private static final String APPLICATION_BUNDLE_NAME = "app";
    private static final String MAPPING = "/";

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext applicationContext
                = new AnnotationConfigWebApplicationContext();
        applicationContext.scan("com.epam.esm");
        ServletRegistration.Dynamic registration = servletContext.addServlet(DISPATCHER_SERVLET_NAME,
                new DispatcherServlet(applicationContext));
        registration.addMapping(MAPPING);
        setActiveProfile(servletContext);
    }

    /**
     * Sets current active profile.
     *
     * @param servletContext the {@code ServletContext} object
     */
    private void setActiveProfile(ServletContext servletContext){
        ResourceBundle resource = ResourceBundle.getBundle(APPLICATION_BUNDLE_NAME);
        servletContext.setInitParameter(PROFILE_ACTIVE, resource.getString(PROFILE_ACTIVE));
    }
}
