/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service_monitor;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Badrika
 */
public class Monitor extends Thread {

    protected ServiceConfigBean configBean;

    private void connected() {

        LogFileCreator.writeToInfoLog("Connected to " + configBean.getName());

        long timestamp = System.currentTimeMillis();
        configBean.setLastTimestamp(timestamp);
        boolean stateChanged = configBean.getStatus().DownToUp(timestamp, configBean.getGraceTime());
        if (stateChanged) {
            configBean.serviceUpEvent(timestamp);
        }
    }

    private void notConnected() {
        LogFileCreator.writeToInfoLog("Not connected to " + configBean.getName());

        long timestamp = System.currentTimeMillis();
        configBean.setLastTimestamp(timestamp);
        boolean stateChanged = configBean.getStatus().DownToUp(timestamp, configBean.getGraceTime());
        if (stateChanged) {
            configBean.serviceDownEvent(timestamp);
        }
    }

    public void run() {
        Socket socket = null;
        try {
            socket = new Socket(configBean.getHost(), configBean.getPort());
            if (socket.isConnected()) {
                connected();
            } else {
                notConnected();
            }
        } catch (Exception e) {
            notConnected();
            LogFileCreator.writeToErrorLog(e.toString());
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                LogFileCreator.writeToErrorLog(e.toString());
            }
        }
    }

    public Monitor(String name) throws IOException {
        configBean = ServiceConfigInit.getInstance().getConfiguration(name);
    }

}
