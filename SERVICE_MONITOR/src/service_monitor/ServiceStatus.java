/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service_monitor;

/**
 *
 * @author Badrika
 */
public class ServiceStatus {

    public static final String SERVICE_UP = "SERVICE_UP";
    public static final String SERVICE_DOWN = "SERVIE_DOWN";
    private long statusTimestamp;
    private String status;

    public boolean DownToUp(long timestamp, int graceTime) {

        switch (status) {
            case SERVICE_DOWN:
                if (statusTimestamp + graceTime * 1000 <= timestamp) {
                    status = SERVICE_UP;
                    statusTimestamp = timestamp;
                    return true;
                } else {
                    return false;
                }
            case SERVICE_UP:
                return true;
            default:
                return false;

        }

    }

    public boolean UpToDown(long timestamp, int graceTime) {
        
        switch (status) {
            case SERVICE_UP:
                if (statusTimestamp + graceTime * 1000 <= timestamp) {
                    status = SERVICE_DOWN;
                    statusTimestamp = timestamp;
                    return true;
                } else {
                    return false;
                }
            case SERVICE_DOWN:
                return true;
            default:
                return false;

        }

    }

}
