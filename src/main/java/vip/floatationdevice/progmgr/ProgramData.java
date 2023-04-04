package vip.floatationdevice.progmgr;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/*
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * !!                                                                         !!
 * !!         WARNING!!! MOTHERFUCKING BIG FAT WARNING!!!!!!!1!!!!1!!         !!
 * !!      THIS DATA OBJECT HAS TWO FIELDS THAT HAVE NAMING CONFLICTS!!!      !!
 * !!                                                                         !!
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * !! The field used to store the "highlights" is named "view" when returned, !!
 * !! while the incoming data is named "point" when adding or modifying; The  !!
 * !! field used to store the "cast list" needs to be named "actorList" when  !!
 * !! returned, while the data passed in when added or modified is named      !!
 * !! "actors "when adding or modifying.                                      !!
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
/**
 * Represents a show (or "program". whatever)
 */
public class ProgramData implements Serializable
{
    public static final long serialVersionUID = 0L;
    private long id = Integer.MIN_VALUE;
    private String name, type, point;
    private String[] actors;

    public long getId(){return id;}

    public String getName(){return name;}

    public String getType(){return type;}

    public String getPoint(){return point;}

    public String[] getActors(){return actors;}

    public void setId(long id){this.id = id;}

    public void setName(String name){this.name = name;}

    public void setType(String type){this.type = type;}

    public void setPoint(String point){this.point = point;}

    public void setActors(String... actors){this.actors = actors;}

    public void setActors(String actors){this.actors = actors.split(",");}

    /**
     * Usa: converting data from the front-end.
     */
    public static ProgramData fromString(String json)
    {
        ProgramData p = new ProgramData();
        JSONObject j = new JSONObject(json);
        p.setType(j.getStr("type"));
        p.setName(j.getStr("name"));
        p.setPoint(j.getStr("point"));
        p.setActors(j.getStr("actors"));
        return p;
    }

    /**
     * Use: returning the data to the front-end.
     * @return Map, but should be converted to JSON by Spring automatically.
     */
    public Map<String, Object> toMap()
    {
        StringBuilder a = new StringBuilder();
        for(String s : this.actors)
            a.append(s).append(',');
        if(a.length() != 0) a.deleteCharAt(a.length() - 1);
        HashMap<String, Object> m = new HashMap<String, Object>();
        m.put("id", id);
        m.put("typeName", type);
        m.put("name", name);
        m.put("view", point);
        m.put("actorList", a.toString());
        return m;
    }

//    /**
//     * Use: save the data to local files.
//     * Should be called in the serialization automatically.
//     */
//    private void writeObject(ObjectOutputStream oos) throws IOException
//    {
//        JSONObject j = new JSONObject();
//        j.set("id", id)
//                .set("type", type)
//                .set("name", name)
//                .set("point", point)
//                .set("actors", actors);
//        oos.write(j.toString().getBytes(StandardCharsets.UTF_8));
//    }
//
//    /**
//     * Use: read the data from local files.
//     * Should be called in the serialization automatically.
//     */
//    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
//    {
//        JSONObject j = new JSONObject(new Scanner(ois).nextLine());
//        JSONArray actorsArray = j.getJSONArray("actors");
//        String[] actors = new String[actorsArray.size()];
//        if(actorsArray != null) for(int i = 0; i != actorsArray.size(); i++)
//            actors[i] = actorsArray.get(i).toString();
//        id = j.getInt("id");
//        type = j.getStr("type");
//        name = j.getStr("name");
//        point = j.getStr("point");
//        this.actors = actors;
//    }
}
