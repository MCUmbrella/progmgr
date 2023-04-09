package vip.floatationdevice.progmgr.controller;

import cn.hutool.json.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.floatationdevice.progmgr.DataManager;
import vip.floatationdevice.progmgr.data.CommonMapResult;
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
    public CommonMapResult actionInsertProgram(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Object type = requestBody.get("type"), name = requestBody.get("name"), point = requestBody.get("point"), actors = requestBody.get("actors");
        if(!hasNull(type, name, point, actors))
        {
            if(type instanceof String &&
                    name instanceof String &&
                    point instanceof String &&
                    actors instanceof String
            )
            {
                if(!isBlank(name))
                {
                    DataManager.insertData(ProgramData.fromJson(new JSONObject(requestBody)));
                    return new CommonMapResult(0, "OK", null);
                }
                else response.sendError(400, "Name is empty");
            }
            else response.sendError(400, "Invalid parameter type");
        }
        else response.sendError(400, "Missing parameter");
        return null;
    }

    @PostMapping(value = "/delete/program", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CommonMapResult actionDeleteProgram(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Object id = requestBody.get("id");
        if(id != null)
        {
            if(id instanceof Integer)
            {
                if(DataManager.hasData((int) id))
                {
                    DataManager.deleteData((int) id);
                    return new CommonMapResult(0, "OK", null);
                }
                else response.sendError(404, "Program with ID " + id + " not exists");
            }
            else response.sendError(400, "Invalid parameter type");
        }
        else response.sendError(400, "Missing parameter");
        return null;
    }

    @PostMapping(value = "/update/program", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CommonMapResult actionUpdateProgram(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Object id = requestBody.get("id"), type = requestBody.get("type"), name = requestBody.get("name"), point = requestBody.get("point"), actors = requestBody.get("actors");
        if(!hasNull(id, type, name, point, actors))
        {
            if(id instanceof Integer &&
                    type instanceof String &&
                    name instanceof String &&
                    point instanceof String &&
                    actors instanceof String
            )
            {
                if(!isBlank(name))
                {
                    if(DataManager.hasData((int) id))
                    {
                        DataManager.updateData(ProgramData.fromJson(new JSONObject(requestBody)));
                        return new CommonMapResult(0, "OK", null);
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
