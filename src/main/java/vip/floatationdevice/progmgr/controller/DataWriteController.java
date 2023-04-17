package vip.floatationdevice.progmgr.controller;

import cn.hutool.json.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.floatationdevice.progmgr.DataManager;
import vip.floatationdevice.progmgr.data.CommonMappedResult;
import vip.floatationdevice.progmgr.data.ProgramData;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SuppressWarnings("unused")
@RestController
public class DataWriteController
{
    @PostMapping(value = "/add/program", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CommonMappedResult actionInsertData(@RequestBody @Validated ProgramData data, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        DataManager.insertData(ProgramData.fromJson(new JSONObject(data)));
        response.setStatus(201);
        return new CommonMappedResult(0, "OK");
    }

    @PostMapping(value = "/delete/program", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CommonMappedResult actionDeleteData(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception
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
    public CommonMappedResult actionUpdateData(@RequestBody @Validated ProgramData data, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        if(data.getId() != null)
        {
            if(DataManager.hasData(data.getId()))
            {
                DataManager.updateData(data);
                return new CommonMappedResult(0, "OK");
            }
            else response.sendError(404, "Program with ID " + data.getId() + " not exists");
        }
        else response.sendError(400, "Missing parameter");
        return null;
    }
}
