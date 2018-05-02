/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service_monitor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Badrika
 */
public class ServiceConfigBean {

    private String name;
    private String host;
    private int port;
    private boolean upAndRunning;
    private int pollingFrequency;
    private int graceTime;
    private long serviceOutageStartTime;
    private long serviceOutageEndTime;
    private long lastTimestamp;
    private ServiceStatus status = new ServiceStatus();
    private List<ServiceListner> listeners = new ArrayList<ServiceListner>();

    public ServiceConfigBean(String name, String host, int port, boolean upAndRunning, 
            int pollingFrequency, int graceTime) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.upAndRunning = upAndRunning;
        this.pollingFrequency = pollingFrequency;
        this.graceTime = graceTime;
    }

    public ServiceStatus getStatus() {
        return status;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isUpAndRunning() {
        return upAndRunning;
    }

    public void setUpAndRunning(boolean upAndRunning) {
        this.upAndRunning = upAndRunning;
    }

    public synchronized int getPollingFrequency() {
        if (pollingFrequency > graceTime) {
            return graceTime;
        } else {
            return pollingFrequency;
        }
    }

    public void setPollingFrequency(int pollingFrequency) {
        this.pollingFrequency = pollingFrequency;
    }

    public int getGraceTime() {
        return graceTime;
    }

    public void setGraceTime(int graceTime) {
        this.graceTime = graceTime;
    }

    public long getServiceOutageStartTime() {
        return serviceOutageStartTime;
    }

    public void setServiceOutageStartTime(long serviceOutageStartTime) {
        this.serviceOutageStartTime = serviceOutageStartTime;
    }

    public long getServiceOutageEndTime() {
        return serviceOutageEndTime;
    }

    public void setServiceOutageEndTime(long serviceOutageEndTime) {
        this.serviceOutageEndTime = serviceOutageEndTime;
    }

    public long getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(long lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    synchronized public boolean isInServiceOutage(long currentTimestamp) {
        if (serviceOutageStartTime > 0 && serviceOutageEndTime > 0) {
            return currentTimestamp >= serviceOutageStartTime && currentTimestamp <= serviceOutageEndTime;
        }
        return false;
    }
    
    synchronized public void serviceUpEvent(long timestamp) {
        for (ServiceListner listener : listeners) {
            listener.serviceUp(name, timestamp);
        }
    }

    synchronized public void serviceDownEvent(long timestamp) {
        for (ServiceListner listener : listeners) {
            listener.serviceDown(name, timestamp);
        }
    }
    
    synchronized public void addListener(ServiceListner listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    synchronized public void removeListener(ServiceListner listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

}
