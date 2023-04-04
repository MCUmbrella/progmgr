package vip.floatationdevice.progmgr;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@SpringBootApplication
public class Main
{
    public static void main(String[] args)
    {
        //SpringApplication.run(Main.class, args);
        ProgramData p = ProgramData.fromString("""
                {
                    "type": "戏曲",
                    "name": "三顾茅庐",
                    "point": "主打就是一个颠覆三观！",
                    "actors": "张飞，周瑜，诸葛亮"
                }""");
        for(String s : p.toMap().keySet())
            System.out.println(s + ": " + p.toMap().get(s));
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("program"));
            oos.writeObject(p);
            oos.flush();
            oos.close();
            System.out.println("saved");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            ProgramData p2 = (ProgramData) new ObjectInputStream(new FileInputStream("program")).readObject();
            System.out.println("loaded");
            for(String s : p.toMap().keySet())
                System.out.println(s + ": " + p.toMap().get(s));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
