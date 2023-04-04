package vip.floatationdevice.progmgr;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main
{
    public static void main(String[] args)
    {
        //SpringApplication.run(Main.class, args);
        DataManager.initialize();
        ProgramData p = ProgramData.fromString("{\n" +
                "    \"type\": \"戏曲\"," +
                "    \"name\": \"0顾茅庐\"," +
                "    \"point\": \"主打就是一个颠覆三观！\"," +
                "    \"actors\": \"张飞，周瑜，诸葛亮\"" +
                "}");
        System.out.println(DataManager.createData(p));
        for(int i = 1; i != 100001; i++)
        {
            p.setName(i + "顾茅庐");
            DataManager.createData(p);
        }
    }
}
