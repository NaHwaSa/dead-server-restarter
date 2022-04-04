package com.nahwasa.dead_server_restarter;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
    private static void checkIt() throws Exception {
        HttpResponse<String> response = Unirest.get("http://logcalhost:8080/chk").asString();
        System.out.println(response.getStatus());
    }

    public static void main(String[] args) throws Exception {
        Unirest.config().verifySsl(false);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    checkIt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        new Timer().scheduleAtFixedRate(timerTask, 1000, 6000);
    }
}
