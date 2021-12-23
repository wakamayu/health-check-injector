package com.wakamayu.jucu.health.check.injector.interfaces;

import com.wakamayu.jucu.health.check.injector.model.ResponseHealth;

/**
 *
 * @author carlos
 * @param <T>
 */
public interface FactoryHealthCheck {

    public ResponseHealth ready();

    public ResponseHealth readyLiveness();

    public ResponseHealth readyReadiness();

}
