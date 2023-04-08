package vip.floatationdevice.progmgr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import vip.floatationdevice.progmgr.data.ProgramData;

import java.util.Random;

@SpringBootTest
class Test
{
    private static final Logger l = LoggerFactory.getLogger(Test.class);
    private static final boolean GEN_SAMPLE = false;
    @org.junit.jupiter.api.Test
    void contextLoads()
    {
        l.info("Test.contextLoads() BEGIN");

        // generate some sample program data
        if(GEN_SAMPLE)
        {
            l.info("Generating sample program data");
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
            l.info("Sample program data generated");
        }

        l.info("Test.contextLoads() END");
    }
}
