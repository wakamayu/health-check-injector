/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wakamayu.jucu.health.check.injector.driver;

import com.wakamayu.jucu.health.check.injector.interfaces.Driver;
import com.wakamayu.jucu.health.check.injector.configure.TracerModel;
import com.wakamayu.jucu.health.check.injector.enums.TypeStatus;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author carlos
 */
@Named("SQL")
@Stateless
public class InjectorPersistenceUnit implements Driver {

    private static Map<String, String> queries = new HashMap<String, String>();

    static {
        queries.put("ORACLE", "SELECT 'Hello' from DUAL");
        queries.put("MYSQL", "SELECT 1");
        queries.put("POSTGREST", "SELECT 1");
        queries.put("MICROSOFT", "SELECT 1");
    }

    private EntityManager em;

    private EntityManager entityManager(String persistenceUnit) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        return emf.createEntityManager();

    }

    boolean check(String persistenceUnit, String typeDatasource) {
        try {

            return !entityManager(persistenceUnit).createNativeQuery(queries.get(typeDatasource)).getResultList().isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public TracerModel execute(TracerModel model) {
        if (model.getPersistenceUnit() != null && model.getDriver() != null) {
            boolean pipe = check(model.getPersistenceUnit(), model.getDriver());
            if (pipe) {
                model.setStatus(TypeStatus.AVAILABILITY);
            } else {
                model.setStatus(TypeStatus.UNAVAILABILITY);
            }
        } else {
            model.setStatus(TypeStatus.UNAVAILABILITY);
        }
        return model;
    }

}
