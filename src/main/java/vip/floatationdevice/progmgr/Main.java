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
        for(String a : args)
            if("--fixdb".equals(a))
                try
                {
                    DataManager.resetDatabase();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    System.exit(0);
                }
        l.info("PROGMGR is starting up");
        applicationContext = SpringApplication.run(Main.class, args);
        l.info("PROGMGR startup completed");
        l.info("Web interface: http://127.0.0.1:8077/SP-IMS.html");
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
     * Call this to shut down PROGMGR.
     * @param code The exit code returned to the operating system.
     */
    public static void shutdown(int code)
    {
        applicationContext.close();
        System.exit(code);
    }
}
