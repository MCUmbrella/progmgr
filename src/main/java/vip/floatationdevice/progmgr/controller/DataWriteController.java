package vip.floatationdevice.progmgr.controller;

import cn.hutool.json.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.floatationdevice.progmgr.DataManager;
import vip.floatationdevice.progmgr.data.CommonMappedResult;
import vip.floatationdevice.progmgr.data.ProgramData;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static vip.floatationdevice.progmgr.CommonUtil.hasNull;
import static vip.floatationdevice.progmgr.CommonUtil.isBlank;

@SuppressWarnings("unused")
@RestController
public class DataWriteController
{
    @PostMapping(value = "/add/program", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CommonMappedResult actionInsertProgram(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Object name = requestBody.get("name"), typeName = requestBody.get("typeName"), actorList = requestBody.get("actorList"), view = requestBody.get("view");
        if(!hasNull(name, typeName, actorList, view))
        {
            if(name instanceof String &&
                    typeName instanceof String &&
                    actorList instanceof String &&
                    view instanceof String
            )
            {
                if(!isBlank(name))
                {
                    DataManager.insertData(ProgramData.fromJson(new JSONObject(requestBody)));
                    return new CommonMappedResult(0, "OK");
                }
                else response.sendError(400, "Name is empty");
            }
            else response.sendError(400, "Invalid parameter type");
        }
        else response.sendError(400, "Missing parameter");
        return null;
    }

    @PostMapping(value = "/delete/program", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CommonMappedResult actionDeleteProgram(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Object id = requestBody.get("id");
        if(id != null)
        {
            if(id instanceof Integer)
            {
                if(DataManager.hasData((int) id))
                {
                    DataManager.deleteData((int) id);
                    return new CommonMappedResult(0, "OK");
                }
                else response.sendError(404, "Program with ID " + id + " not exists");
            }
            else response.sendError(400, "Invalid parameter type");
        }
        else response.sendError(400, "Missing parameter");
        return null;
    }

    @PostMapping(value = "/update/program", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CommonMappedResult actionUpdateProgram(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Object id = requestBody.get("id"), name = requestBody.get("name"), typeName = requestBody.get("typeName"), actorList = requestBody.get("actorList"), view = requestBody.get("view");
        if(!hasNull(id, name, typeName, actorList, view))
        {
            if(id instanceof Integer &&
                    name instanceof String &&
                    typeName instanceof String &&
                    actorList instanceof String &&
                    view instanceof String
            )
            {
                if(!isBlank(name))
                {
                    if(DataManager.hasData((int) id))
                    {
                        DataManager.updateData(ProgramData.fromJson(new JSONObject(requestBody)));
                        return new CommonMappedResult(0, "OK");
                    }
                    else response.sendError(404, "Program with ID " + id + " not exists");
                }
                else response.sendError(400, "Name is empty");
            }
            else response.sendError(400, "Invalid parameter type");
        }
        else response.sendError(400, "Missing parameter");
        return null;
    }
}
