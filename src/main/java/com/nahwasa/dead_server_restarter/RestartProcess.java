package com.nahwasa.dead_server_restarter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RestartProcess {
    private int processNum = -1;

    private RestartProcess() {}
    public RestartProcess(int processNum) {
        if (processNum<0 || processNum>=Config.FILE_FOR_EACH_RESTART_PROCESS_NUM.length) {
            throw new RuntimeException("INVALID 'processNum'.");
        }
        this.processNum = processNum;
    }

    public boolean start() {
        try {
            Common.print("----- RESTART PROCESS START -----");
            Process process = Runtime.getRuntime().exec(Config.FILE_FOR_EACH_RESTART_PROCESS_NUM[processNum]);

            StringBuilder execResult = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String input = "";
            while ( (input=br.readLine()) != null ) {
                execResult.append(input);
            }
            System.out.println(execResult);
            Common.print("----- RESTART PROCESS END -----");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
