package vip.floatationdevice.progmgr.data;

import cn.hutool.json.JSONObject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
@Data
public class ProgramData implements Serializable
{
    public static final long serialVersionUID = 0L;
    private Integer id = Integer.MIN_VALUE, actorCount = Integer.MIN_VALUE;
    @NotNull(message = "'name' is missing")
    @NotBlank(message = "'name' is blank")
    private String name;
    @NotNull(message = "'typeName' is missing")
    @NotBlank(message = "'typeName' is blank")
    private String typeName;
    @NotNull(message = "'view' is missing")
    @NotBlank(message = "'view' is blank")
    private String  view;
    @NotNull(message = "'actorList' is missing")
    @NotBlank(message = "'actorList' is blank")
    private String actorList;

    public void setActorList(String actorList)
    {
        this.actorList = actorList;
        this.actorCount = actorList.split("[\\uFF0C,]").length;
    }

    public void setActorCount(Integer actorCount){}

    /**
     * Use: converting data from the front-end.
     */
    public static ProgramData fromJson(JSONObject j)
    {
        ProgramData p = new ProgramData();
        String[] actors = j.getStr("actors").split("[\\uFF0C,]");
        StringBuilder actorsList = new StringBuilder();
        int actorCount = 0;
        for(String s : actors)
            if(!s.isBlank())
            {
                actorsList.append(s.trim()).append(',');
                actorCount++;
            }
        if(actorsList.length() != 0) actorsList.deleteCharAt(actorsList.length() - 1);
        p.setId(j.getInt("id"));
        p.setTypeName(j.getStr("type").trim());
        p.setName(j.getStr("name").trim());
        p.setView(j.getStr("point").trim());
        p.setActorList(actorsList.toString());
        p.setActorCount(actorCount);
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
