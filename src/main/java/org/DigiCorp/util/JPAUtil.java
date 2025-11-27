package org.DigiCorp.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class JPAUtil {

    private static EntityManagerFactory emf;

    private JPAUtil(){}

    // Lazy initialisation of factory when needed
    private static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            Map<String, String> persistenceMap = new HashMap<>();
            persistenceMap.put("jakarta.persistence.jdbc.url",
                    "jdbc:mariadb://localhost:3306/employees");

            emf = Persistence.createEntityManagerFactory("EmployeeService", persistenceMap);
        }
        return emf;
    }

    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

}
