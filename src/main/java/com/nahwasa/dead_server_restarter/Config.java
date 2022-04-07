package com.nahwasa.dead_server_restarter;

public class Config {
    public static final String LIVE_CHECK_URL   = "http://localhost:8080/chk";
    public static final int RESTART_PROCESS_NUM = 0;
    public static final String[] FILE_FOR_EACH_RESTART_PROCESS_NUM = {
            "restart_batch.bat",
            "/test.sh"
    };
    public static final int DELAY   = 5000;
    public static final int PERIOD  = 60000;

}
