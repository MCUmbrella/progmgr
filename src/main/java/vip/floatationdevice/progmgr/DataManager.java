package vip.floatationdevice.progmgr;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

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
            if(!new File("progmgr.db").exists()) createDatabase();
            int count = getMapper().getDataCount();
            log.info("" + count + " program data found");
            log.info("Database OK");
        }
        catch(Exception e)
        {
            log.error("DATABASE CHECK UNSUCCESSFUL: " + e);
            log.error("Reset the database by running the program with '--fixdb' argument");
            e.printStackTrace();
            Main.shutdown(-1);
        }
        log.info("DataManager is ready");
        ready = true;
    }

    public static boolean isReady(){return ready;}

    public static void checkReady(){if(!ready) throw new IllegalStateException("DataManager is not initialized");}

    public static void resetDatabase() throws Exception
    {
        log.warn("PERFORMING DATABASE RESET");
        File db = new File("progmgr.db");
        if(ready)
        {
            ready = false;
            db.renameTo(new File("progmgr.db.OLD"));
            log.info("Existing database renamed to 'progmgr.db.OLD'");
            createDatabase();
            ready = true;
        }
        else
        {
            if(db.exists() || !db.isFile())
            {
                db.renameTo(new File("progmgr.db.OLD"));
                log.info("Existing database renamed to 'progmgr.db.OLD'");
            }
            createDatabase();
        }
        log.warn("DATABASE RESET SUCCESSFUL");
    }

    private static void createDatabase() throws Exception
    {
        log.info("Creating default database");
        File db = new File("progmgr.db");
        InputStream is = Main.class.getResourceAsStream("/progmgr.db");
        assert is != null;
        FileOutputStream fos = new FileOutputStream(db);
        fos.write(is.readAllBytes());
        fos.flush();
        fos.close();
        log.info("Database created");
    }
}
