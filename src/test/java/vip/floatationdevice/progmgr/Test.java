package vip.floatationdevice.progmgr;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import vip.floatationdevice.progmgr.data.ProgramData;

import java.util.Random;

@SpringBootTest
@Slf4j
class Test
{
    private static final boolean GEN_SAMPLE = false;

    @org.junit.jupiter.api.Test
    void contextLoads()
    {
        log.info("Test.contextLoads() BEGIN");
        // generate some sample program data
        if(GEN_SAMPLE)
        {
            log.info("Generating sample program data");
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
            log.info("Sample program data generated");
        }
        log.info("Test.contextLoads() END");
    }
}
