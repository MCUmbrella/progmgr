package vip.floatationdevice.progmgr.controller;

import cn.hutool.json.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.floatationdevice.progmgr.DataManager;
import vip.floatationdevice.progmgr.data.CommonMappedResult;
import vip.floatationdevice.progmgr.data.ProgramData;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SuppressWarnings("unused")
@RestController
public class DataWriteController
{
    @PostMapping(value = "/api/program", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CommonMappedResult actionInsertData(@RequestBody @Validated ProgramData data, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        DataManager.insertData(ProgramData.fromJson(new JSONObject(data)));
        response.setStatus(201);
        return new CommonMappedResult(0, "OK");
    }

    @DeleteMapping(value = "/api/program/{id}", produces = APPLICATION_JSON_VALUE)
    public CommonMappedResult actionDeleteData(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable("id") Integer id
    ) throws Exception
    {
        if(DataManager.hasData(id))
        {
            DataManager.deleteData(id);
            return new CommonMappedResult(0, "OK");
        }
        else response.sendError(404, "Program with ID " + id + " not exists");
        return null;
    }

    @PutMapping(value = "/api/program/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CommonMappedResult actionUpdateData(
            HttpServletRequest request, HttpServletResponse response,
            @RequestBody @Validated ProgramData data,
            @PathVariable("id") Integer id
    ) throws Exception
    {
        if(DataManager.hasData(id))
        {
            data.setId(id);
            DataManager.updateData(data);
            return new CommonMappedResult(0, "OK");
        }
        else response.sendError(404, "Program with ID " + data.getId() + " not exists");
        return null;
    }
}
