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
public interface ServiceListner {
    void serviceUp(String name, long timestamp);
    void serviceDown(String name, long timestamp);
    
}
