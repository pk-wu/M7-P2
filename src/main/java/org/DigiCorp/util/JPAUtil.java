package org.DigiCorp.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class JPAUtil {
    private final EntityManagerFactory emf;
    private final String dbName = "employees";
    public JPAUtil() {
        Map<String, String> persistenceMap = new HashMap<>();
        persistenceMap.put("jakarta.persistence.jdbc.url",
                "jdbc:mariadb://localhost:3306/" + dbName);

        this.emf = Persistence.createEntityManagerFactory("EmployeeService", persistenceMap);
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
