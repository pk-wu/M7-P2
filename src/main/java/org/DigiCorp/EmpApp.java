package org.DigiCorp;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.DigiCorp.service.EmployeeService;
import org.DigiCorp.util.ObjectMapperContextResolver;

import java.util.HashSet;
import java.util.Set;

/**
 * entry point into the application defining base URI path.
 * registers resource and provider classes.
 */
@ApplicationPath("/api")
public class EmpApp extends Application {
    /**
     * overrides the getClasses() method to register
     * our resource and provider classes
     *
     * @return Set of all class objects
     */
    @Override
    public Set<Class<?>> getClasses() {
        // hashset to hold application components
        Set<Class<?>> s = new HashSet<Class<?>>();
        // adding Resource class
        s.add(EmployeeService.class);
        // adding customized context resolver for JSON serializing
        s.add(ObjectMapperContextResolver.class);
        // return the set of added classes
        return s;
    }
}