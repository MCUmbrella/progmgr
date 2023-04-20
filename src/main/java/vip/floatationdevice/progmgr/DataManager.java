package vip.floatationdevice.progmgr;

import lombok.extern.slf4j.Slf4j;

import static vip.floatationdevice.progmgr.Main.getMapper;

/**
 * Manages saved program data.
 */
@Slf4j
public class DataManager
{
    private static boolean ready = false;

    /**
     * Initializes the data manager.
     * Call this function first before doing any other things.
     * @throws IllegalStateException when the data manager is already initialized.
     * @throws RuntimeException when the data manager is unable to save the default last ID.
     */
    public static synchronized void initialize()
    {
        log.info("DataManager is initializing");
        if(ready) throw new IllegalStateException("DataManager already initialized");
        try
        {
            log.info("Checking database");
            int count = getMapper().getDataCount();
            log.info("" + count + " program data found");
            log.info("Database OK");
        }
        catch(Exception e)
        {
            log.error("DATABASE CHECK UNSUCCESSFUL: " + e);
            e.printStackTrace();
            Main.shutdown(-1);
        }
        log.info("DataManager is ready");
        ready = true;
    }

    public static boolean isReady(){return ready;}

    public static void checkReady(){if(!ready) throw new IllegalStateException("DataManager is not initialized");}
}
