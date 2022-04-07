package com.nahwasa.dead_server_restarter;

import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.Unirest;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
    private static boolean isItOk() throws Exception {
        HttpResponse response = null;
        try {
            response = Unirest.get(Config.LIVE_CHECK_URL).asEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (response == null || response.getStatus() != HttpStatus.OK)
            return false;
        return true;
    }

    public static void main(String[] args) throws Exception {
        Unirest.config().verifySsl(false);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Common.print(String.format("\n\n----- CHECKING SERVER : %s -----", Config.LIVE_CHECK_URL));
                try {
                    if (!isItOk()) {
                        Common.print(String.format("----- CHECKING FAIL : %s -----", Config.LIVE_CHECK_URL));
                        RestartProcess rp = new RestartProcess(Config.RESTART_PROCESS_NUM);
                        if (!rp.start()) {
                            Common.print("----- RESTART FAIL -----");
                        }
                    } else {
                        Common.print(String.format("----- CHECKING SUCCESS : %s -----", Config.LIVE_CHECK_URL));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        new Timer().scheduleAtFixedRate(timerTask, Config.DELAY, Config.PERIOD);
    }
}
