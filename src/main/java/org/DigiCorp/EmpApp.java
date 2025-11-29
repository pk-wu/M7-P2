package org.DigiCorp;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.DigiCorp.resource.EmployeeResource;
import org.DigiCorp.util.ObjectMapperContextResolver;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class EmpApp extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(EmployeeResource.class);
        s.add(ObjectMapperContextResolver.class);
        return s;
    }
}