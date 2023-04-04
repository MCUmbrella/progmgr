package vip.floatationdevice.progmgr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Scanner;

/**
 * Manages saved program data.
 */
public class DataManager //TODO: index by per 10000 files & add HashMap based cache system
{
    private static final Logger l = LoggerFactory.getLogger(DataManager.class);
    private static boolean ready = false;
    private static long lastId = -1L;

    /**
     * Initializes the data manager.
     * Call this function first before doing any other things.
     * @throws IllegalStateException when the data manager is already initialized.
     * @throws RuntimeException when the data manager is unable to save the default last ID.
     */
    public static synchronized void initialize()
    {
        l.info("DataManager is initializing");
        if(ready) throw new IllegalStateException("DataManager already initialized");
        try
        {
            lastId = new Scanner(new FileInputStream(new File(".progmgr", "LAST_ID"))).nextLong();
            l.info("Last ID: " + lastId);
        }
        catch(Exception e)
        {
            l.warn("Last ID not saved or unreadable. Defaulting to -1: " + e);
            saveLastId(-1);
        }
        l.info("DataManager is ready");
        ready = true;
    }

    public static boolean isReady()
    {
        return ready;
    }

    private static synchronized void saveLastId(long id)
    {
        try
        {
            File f = new File(".progmgr", "LAST_ID");
            if(!f.exists())
                f.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(Long.toString(id).getBytes());
            fos.flush();
            fos.close();
            l.info("Last ID saved: " + id);
        }
        catch(Exception e)
        {
            l.error("Failed to save last ID: " + e);
            throw new RuntimeException("Failed to save last ID", e);
        }
    }

    /**
     * Check if the data file of a specified program exists.
     * NOTE: This function doesn't check the permission and whether it is a folder.
     * @param id The ID of the program data.
     */
    public static synchronized boolean hasData(long id)
    {
        return new File(".progmgr", String.valueOf(id)).exists();
    }

    /**
     * Get the information of a program with specified ID and return them as ProgramData.
     * @param id The ID of the program.
     * @return The ProgramData object of the program with specified ID.
     * @throws RuntimeException when the file with the name as ID is not found in "./.progmgr", or is unreadable or is a folder.
     */
    public static synchronized ProgramData getData(long id)
    {
        if(!ready) throw new IllegalStateException("DataManager is not initialized");
        if(!hasData(id)) throw new RuntimeException("File not found: .progmgr/" + id);
        try
        {
            return (ProgramData) new ObjectInputStream(new FileInputStream(new File(".progmgr", String.valueOf(id)))).readObject();
        }
        catch(Exception e)
        {
            l.error("Unable to read data file of ID " + id + ": " + e);
            throw new RuntimeException("Unable to read data file of ID " + id, e);
        }
    }

    /**
     * Create a program data and save it to local.
     * @param p The information of the program.
     * @return The ID of the newly created program.
     * @throws RuntimeException when unable to save the file.
     */
    public static synchronized long createData(ProgramData p)
    {
        if(!ready) throw new IllegalStateException("DataManager is not initialized");
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(".progmgr", String.valueOf(lastId + 1L))));
            oos.writeObject(p);
            oos.flush();
            oos.close();
            saveLastId(lastId + 1);
            l.info("Saved program info of " + p.getName() + ", ID=" + (lastId + 1L));
        }
        catch(Exception e)
        {
            l.error("Failed to save the data of program with ID " + (lastId + 1L) + ": " + e);
            throw new RuntimeException("Failed to save program data" + e);
        }
        return ++lastId;
    }
}
