/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service_monitor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Badrika
 */
public class SERVICE_MONITOR extends Thread {

    public static final int THREAD_INTERVAL = 1000;
    protected boolean isThreadInRunning = true;
    protected ServiceConfigInit configInit = ServiceConfigInit.getInstance();
    protected ExecutorService threadPool = Executors.newCachedThreadPool();

    public SERVICE_MONITOR() {
        LogFileCreator.writeToInfoLog("Service Monitor started at " + String.valueOf(System.currentTimeMillis()));
    }

    protected boolean isTimeToRun(ServiceConfigBean configuration) {
        long timestamp = System.currentTimeMillis();
        return configuration.isUpAndRunning()
                && !configuration.isInServiceOutage(timestamp)
                && (timestamp >= configuration.getLastTimestamp() + configuration.getPollingFrequency() * 1000);
    }

    public void run() {
        try {
            while (isThreadInRunning) {
                try {
                    for (String serviceName : configInit.getServices()) {
                        ServiceConfigBean configBean = configInit.getConfiguration(serviceName);
                        if (!isTimeToRun(configBean)) {
                            continue;
                        }

                        threadPool.submit(new Monitor(serviceName));
                    }

                    Thread.sleep(THREAD_INTERVAL);
                } catch (Exception e) {
                    LogFileCreator.writeToErrorLog(e.toString());
                }
            }
        } catch (Exception e) {
            LogFileCreator.writeToErrorLog(e.toString());
        }
        LogFileCreator.writeToInfoLog("Service monitor stopped at " + String.valueOf(System.currentTimeMillis()));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
                
        final String SERVICE_ONE = "SERVICE_ONE";
        final String SERVICE_TWO = "SERVICE_TWO";
        final String SERVICE_THREE = "SERVICE_THREE";
        final String LOCAL_HOST = "127.0.0.1";

        SERVICE_MONITOR server = new SERVICE_MONITOR();
        server.configInit.addConfiguration(new ServiceConfigBean(SERVICE_ONE, LOCAL_HOST, 9991, true, 1, 2));
        server.configInit.addConfiguration(new ServiceConfigBean(SERVICE_TWO, LOCAL_HOST, 9992, true, 1, 3));
        server.configInit.addConfiguration(new ServiceConfigBean(SERVICE_THREE, LOCAL_HOST, 9993, true, 1, 4));

        server.start();

    }

}
