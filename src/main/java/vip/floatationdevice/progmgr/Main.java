package vip.floatationdevice.progmgr;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main
{
    public static void main(String[] args)
    {
        long a = System.currentTimeMillis();
        //SpringApplication.run(Main.class, args);
        DataManager.initialize();
        ProgramData p = ProgramData.fromString("{\n" +
                "    \"type\": \"戏曲\"," +
                "    \"name\": \"0顾茅庐\"," +
                "    \"point\": \"主打就是一个颠覆三观！\"," +
                "    \"actors\": \"张飞，周瑜，诸葛亮\"" +
                "}");
        for(int i = 0; i != 10000; i++)
        {
            p.setName(i + "顾茅庐");
            DataManager.createData(p);
        }
        System.out.println(System.currentTimeMillis() - a);
    }
}
