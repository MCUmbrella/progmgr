package vip.floatationdevice.progmgr.data;

import cn.hutool.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/*
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * !!                                                                         !!
 * !!     WARNING! WARNING!!! MOTHERFUCKING BIG FAT WARNING!!!!!!!1!!!!1!!    !!
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
@SuppressWarnings("unused")
public class ProgramData implements Serializable
{
    public static final long serialVersionUID = 0L;
    private Integer id = Integer.MIN_VALUE, actorCount = Integer.MIN_VALUE;
    private String name, typeName, view, actorList;

    public Integer getId(){return id;}

    public Integer getActorCount(){return actorCount;}

    public String getName(){return name;}

    public String getTypeName(){return typeName;}

    public String getView(){return view;}

    public String getActorList(){return actorList;}

    public void setId(Integer id){this.id = id;}

    public void setActorCount(Integer actorCount){this.actorCount = actorCount;}

    public void setName(String name){this.name = name;}

    public void setTypeName(String typeName){this.typeName = typeName;}

    public void setView(String view){this.view = view;}

    public void setActorList(String actorList)
    {
        this.actorList = actorList;
        this.actorCount = actorList.split("[\\uFF0C,]").length;
    }

    /**
     * Use: converting data from the front-end.
     */
    public static ProgramData fromJson(JSONObject j)
    {
        ProgramData p = new ProgramData();
        p.setId(j.getInt("id"));
        p.setTypeName(j.getStr("type"));
        p.setName(j.getStr("name"));
        p.setView(j.getStr("point"));
        p.setActorList(j.getStr("actors"));
        p.setActorCount(j.getStr("actors").split("[\\uFF0C,]").length);
        return p;
    }

    public static ProgramData fromString(String jsonStr)
    {
        return fromJson(new JSONObject(jsonStr));
    }

    /**
     * Use: returning the data to the front-end.
     * @return Map, but should be converted to JSON by Spring automatically.
     */
    public Map<String, Object> toMap()
    {
        HashMap<String, Object> m = new HashMap<>();
        m.put("id", id);
        m.put("typeName", typeName);
        m.put("name", name);
        m.put("view", view);
        m.put("actorList", actorList);
        return m;
    }

    @Override
    public String toString()
    {
        return new JSONObject()
                .set("id", id)
                .set("name", name)
                .set("typeName", typeName)
                .set("actorList", actorList)
                .set("view", view)
                .toString();
    }
}
