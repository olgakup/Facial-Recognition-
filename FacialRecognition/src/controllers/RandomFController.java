package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RandomFController {

    Process p;

    protected RandomFController() {

    }

    //Methed is executting Python Script: rf.py (Random forest Implementation in Python):

    protected void startRF() {
        try {
            this.p = Runtime.getRuntime().exec("python PythonScript/rf.py");
            BufferedReader input = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader error = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // read the output from the command
            String s = null;
            System.out.println("Command output:\n");
            while ((s = input.readLine()) != null) {
                System.out.println(s);
            }

            // read any errors from the attempted command
            System.out.println("Error of the command (if any):\n");
            while ((s = error.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            System.out.println("Cant execute rf.py");
        }

    }


}
