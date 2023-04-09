package vip.floatationdevice.progmgr;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.floatationdevice.progmgr.data.ProgramData;
import vip.floatationdevice.progmgr.sqlmapper.ProgramDataMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Manages saved program data.
 */
@SuppressWarnings("unused")
public class DataManager
{
    private static final Logger l = LoggerFactory.getLogger(DataManager.class);
    private static final SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Main.class.getResourceAsStream("/mybatis.xml"));
    private static boolean ready = false;
    static SqlSession session = null;

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
            l.info("Checking database");
            factory.getConfiguration().addMapper(ProgramDataMapper.class);
            if(!new File("progmgr.db").exists()) createDatabase();
            session = factory.openSession();
            int count = session.selectOne("vip.floatationdevice.progmgr.sqlmapper.ProgramDataMapper.getDataCount");
            l.info("" + count + " program data found");
            l.info("Database OK");
        }
        catch(Exception e)
        {
            l.error("DATABASE CHECK UNSUCCESSFUL: " + e);
            l.error("Reset the database by running the program with '--fixdb' argument");
            e.printStackTrace();
            Main.shutdown(-1);
        }
        l.info("DataManager is ready");
        ready = true;
    }

    public static boolean isReady(){return ready;}

    public static void checkReady(){if(!ready) throw new IllegalStateException("DataManager is not initialized");}

    public static void resetDatabase() throws Exception
    {
        l.warn("PERFORMING DATABASE RESET");
        File db = new File("progmgr.db");
        if(ready)
        {
            ready = false;
            session.close();
            db.renameTo(new File("progmgr.db.OLD"));
            l.info("Existing database renamed to 'progmgr.db.OLD'");
            createDatabase();
            session = factory.openSession();
            ready = true;
        }
        else
        {
            if(db.exists() || !db.isFile())
            {
                db.renameTo(new File("progmgr.db.OLD"));
                l.info("Existing database renamed to 'progmgr.db.OLD'");
            }
            createDatabase();
        }
        l.warn("DATABASE RESET SUCCESSFUL");
    }

    private static void createDatabase() throws Exception
    {
        l.info("Creating default database");
        File db = new File("progmgr.db");
        FileOutputStream fos = new FileOutputStream(db);
        fos.write(Main.class.getResourceAsStream("/progmgr.db").readAllBytes());
        fos.flush();
        fos.close();
        l.info("Database created");
    }

// ==================== READ RELATED FUNCTIONS ====================

    public static int getDataCount()
    {
        checkReady();
        return session.selectOne("vip.floatationdevice.progmgr.sqlmapper.ProgramDataMapper.getDataCount");
    }

    public static List<ProgramData> getAllData()
    {
        checkReady();
        return session.selectList("vip.floatationdevice.progmgr.sqlmapper.ProgramDataMapper.getAllData");
    }

    /**
     * Get a list containing max of 20 program data.
     */
    public static List<ProgramData> getPagedData(int page)
    {
        checkReady();
        return session.selectList("vip.floatationdevice.progmgr.sqlmapper.ProgramDataMapper.getPagedData", page * 20);
    }

    /**
     * Check if a program data with specified ID is in the database.
     */
    public static boolean hasData(int id)
    {
        checkReady();
        int status = session.selectOne("vip.floatationdevice.progmgr.sqlmapper.ProgramDataMapper.hasData", id);
        return status == 1;
    }

    /**
     * Get the information of a program with specified ID and return them as ProgramData.
     * @param id The ID of the program.
     * @return The ProgramData object of the program with specified ID. If there is no such program data with this ID, return null.
     */
    public static ProgramData getData(int id)
    {
        checkReady();
        return session.selectOne("vip.floatationdevice.progmgr.sqlmapper.ProgramDataMapper.getData", id);
    }

    /**
     * Search the database for the program data matching the specified properties.
     * Any of the parameters can be null, indicating it will not be checked in the search - but not all of them.
     * @return A list containing all the ProgramData object that matches the need.
     * @throws IllegalArgumentException if you pass 3 nulls.
     */
    public static List<ProgramData> findData(String typeName, Integer actorCount, String name)
    {
        if(typeName == null && actorCount == null && name == null)
            throw new IllegalArgumentException("so what the actual fuck do you want to search for? goddamn motherfucking asshole");
        checkReady();
        HashMap<String, Object> params = new HashMap<>();
        params.put("typeName", typeName);
        params.put("actorCount", actorCount);
        params.put("name", name);
        return session.selectList("vip.floatationdevice.progmgr.sqlmapper.ProgramDataMapper.findData", params);
    }

// ==================== WRITE RELATED FUNCTIONS ====================

    /**
     * Append a program data to the database using the information of the given ProgramData object.
     * @param p The information of the program.
     * @return true if successful, false otherwise.
     */
    public static boolean insertData(ProgramData p)
    {
        if(p == null) throw new IllegalArgumentException("ProgramData is null");
        checkReady();
        l.info("Adding program: " + p.getName());
        int result = session.insert("vip.floatationdevice.progmgr.sqlmapper.ProgramDataMapper.insertData", p);
        session.commit();
        if(result == 1)
        {
            l.info("Program added: " + p.getName());
            return true;
        }
        else l.error("Failed to add program: " + p.getName());
        return false;
    }

    /**
     * Update the program data in the database use the corresponding ProgramData object.
     * @param p The ProgramData object containing all the information of the updated program data, with its ID.
     * @return true if successful, false otherwise.
     */
    public static boolean updateData(ProgramData p)
    {
        if(p == null) throw new IllegalArgumentException("ProgramData is null");
        checkReady();
        l.info("Updating program #" + p.getId());
        if(hasData(p.getId()))
        {
            int result = session.update("vip.floatationdevice.progmgr.sqlmapper.ProgramDataMapper.updateData", p);
            session.commit();
            if(result == 1)
            {
                l.info("Update successful: program #" + p.getId() + " - " + p.getName());
                return true;
            }
            else l.error("Failed to update program #" + p.getId());
        }
        else l.error("Update failed: program #" + p.getId() + " not exists");
        return false;
    }

    /**
     * Delete a program data from the database.
     * @param id The ID of the program data to delete.
     * @return true if successful, false otherwise.
     */
    public static boolean deleteData(int id)
    {
        checkReady();
        l.info("Deleting program #" + id);
        if(hasData(id))
        {
            int result = session.update("vip.floatationdevice.progmgr.sqlmapper.ProgramDataMapper.deleteData", id);
            session.commit();
            if(result == 1)
            {
                l.info("Deleted program #" + id);
                return true;
            }
            else l.error("Failed to delete program #" + id);
        }
        else l.error("Deletion failed: program #" + id + " not exists");
        return false;
    }
}
