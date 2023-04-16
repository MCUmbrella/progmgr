package vip.floatationdevice.progmgr;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class Main
{
    private static ConfigurableApplicationContext applicationContext;

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
        log.info("PROGMGR is starting up");
        applicationContext = SpringApplication.run(Main.class, args);
        log.info("PROGMGR startup completed");
        log.info("Web interface: http://127.0.0.1:8077/SP-IMS.html");
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
        log.info("PROGMGR is shutting down");
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
