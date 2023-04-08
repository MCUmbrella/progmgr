package vip.floatationdevice.progmgr;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main
{
    private static ConfigurableApplicationContext applicationContext;
    private static final Logger l = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)
    {
        System.out.println("PROGMGR is starting up");
        applicationContext = SpringApplication.run(Main.class, args);
        l.info("PROGMGR startup completed");
/*
        // generate some sample program data
        Random r = new Random();
        char[] alp = "QWERTYUIOPASDFGHJKLZXCVBNM".toCharArray();
        ProgramData p = new ProgramData();
        for(int i = 1; i != 1001; i++)
        {
            p.setName("Program#" + i);
            p.setView("Description of program #" + i);
            p.setTypeName("" + alp[r.nextInt(alp.length)]);
            StringBuilder sb = new StringBuilder();
            for(int j = -1, k = r.nextInt(100); j != k; j++)
                sb.append(alp[r.nextInt(alp.length)]).append(',');
            sb.deleteCharAt(sb.length() - 1);
            p.setActorList(sb.toString());
            DataManager.insertData(p);
        }
        DataManager.session.commit();
*/
    }

    public static ConfigurableApplicationContext getContext(){return applicationContext;}

    @PostConstruct
    public void onStartup() // will be called automatically
    {
        DataManager.initialize();
    }

    @PreDestroy
    public void onShutdown() // will be called automatically. use Main.shutdown() instead
    {
        l.info("PROGMGR is shutting down");
    }

    /**
     * Call this to shutdown PROGMGR.
     * @param code The exit code returned to the operating system.
     */
    public static void shutdown(int code)
    {
        applicationContext.close();
        System.exit(code);
    }
}
