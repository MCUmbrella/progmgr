package vip.floatationdevice.progmgr.data;

import cn.hutool.json.JSONObject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * Represents a show (or "program". whatever)
 */
@Data
public class ProgramData implements Serializable
{
    public static final long serialVersionUID = 0L;
    private Integer id = null, actorCount = Integer.MIN_VALUE;

    @NotNull(message = "'name' is missing")
    @NotBlank(message = "'name' is blank")
    private String name;
    @NotNull(message = "'typeName' is missing")
    @NotBlank(message = "'typeName' is blank")
    private String typeName;
    @NotNull(message = "'view' is missing")
    @NotBlank(message = "'view' is blank")
    private String view;
    @NotNull(message = "'actorList' is missing")
    @NotBlank(message = "'actorList' is blank")
    private String actorList;

    public void setActorList(String actorList)
    {
        // separate by: 、 ， ,
        String[] actors = actorList.split("[\\u3001\\uFF0C,]");
        // standardize input & detect actor counts
        StringBuilder actorsListFormatted = new StringBuilder();
        int actorCount = 0;
        for(String s : actors)
            if(!s.isBlank())
            {
                actorsListFormatted.append(s.trim()).append(',');
                actorCount++;
            }
        if(actorsListFormatted.length() != 0) actorsListFormatted.deleteCharAt(actorsListFormatted.length() - 1);

        this.actorList = actorsListFormatted.toString();
        this.actorCount = actorCount;
    }

    // ignore direct call to setting actor counts, to avoid actorCount and actorList conflicts
    // setting actor counts is managed by setActorList()
    public void setActorCount(int actorCount){}

    /**
     * Use: converting data from the front-end.
     */
    public static ProgramData fromJson(JSONObject j)
    {
        ProgramData p = new ProgramData();
        p.setId(j.getInt("id"));
        p.setTypeName(j.getStr("typeName").trim());
        p.setName(j.getStr("name").trim());
        p.setView(j.getStr("view").trim());
        p.setActorList(j.getStr("actorList"));
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
