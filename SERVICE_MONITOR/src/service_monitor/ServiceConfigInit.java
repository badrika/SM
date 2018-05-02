/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service_monitor;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Badrika
 */
public class ServiceConfigInit {
    
    private Map<String, ServiceConfigBean> configuration = new HashMap<String, ServiceConfigBean>();

    private static final ServiceConfigInit instance = new ServiceConfigInit();

    private ServiceConfigInit() {}

    public static ServiceConfigInit getInstance() {
        return instance;
    }

    public ServiceConfigBean getConfiguration(String name) {
        return configuration.get(name);
    }

    public void addConfiguration(ServiceConfigBean serviceConfiguration) {
        configuration.put(serviceConfiguration.getName(), serviceConfiguration);
    }

    public Iterable<String> getServices() {
        return configuration.keySet();
    }
    
}
