/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service_monitor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Badrika
 */
public class LogFileCreator {

    public static void writeToInfoLog(String msg) {

        String line = "\n_______________________________________________________________________________________\n";
        String filename = "SERVICE_MONITOR_LOG";
        BufferedWriter bw = null;
        String path = "";

        path = "C:\\LOGS\\Info"; //sample file path is given for windows, can be differ from OSTYPE to OSTYPE

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        filename = path + "/" + filename + ".txt";

        msg = line + String.valueOf(System.currentTimeMillis()) + "\n" + msg;

        try {
            bw = new BufferedWriter(new FileWriter(filename, true));
            bw.write(msg);
            bw.newLine();
            bw.flush();
        } catch (Exception ioe) {
            System.out.println("Error: writing to log file");
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                }
            }
        }
    }
    
    public static void writeToErrorLog(String msg) {

        String line = "\n_______________________________________________________________________________________\n";
        String filename = "SERVICE_MONITOR_LOG";
        BufferedWriter bw = null;
        String path = "";

        path = "C:\\LOGS\\Error"; //sample file path is given for windows, can be differ from OSTYPE to OSTYPE

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        filename = path + "/" + filename + ".txt";

        msg = line + String.valueOf(System.currentTimeMillis()) + "\n" + msg;

        try {
            bw = new BufferedWriter(new FileWriter(filename, true));
            bw.write(msg);
            bw.newLine();
            bw.flush();
        } catch (Exception ioe) {
            System.out.println("Error: writing to log file");
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                }
            }
        }
    }

}
