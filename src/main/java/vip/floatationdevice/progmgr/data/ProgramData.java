package vip.floatationdevice.progmgr.data;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * Represents a show (or "program". whatever)
 */
@Data
public class ProgramData implements Serializable
{
    public static final long serialVersionUID = 0L;
    private Integer id = Integer.MIN_VALUE, actorCount = Integer.MIN_VALUE;
    private String name, typeName, view, actorList;

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
        String[] actors = j.getStr("actorList").split("[\\uFF0C,]");
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
        p.setTypeName(j.getStr("typeName").trim());
        p.setName(j.getStr("name").trim());
        p.setView(j.getStr("view").trim());
        p.setActorList(actorsList.toString());
        p.setActorCount(actorCount);
        return p;
    }

    public static ProgramData fromString(String jsonStr)
    {
        return fromJson(new JSONObject(jsonStr));
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
