package vip.floatationdevice.progmgr.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.floatationdevice.progmgr.data.CommonMappedResult;
import vip.floatationdevice.progmgr.data.ProgramData;
import vip.floatationdevice.progmgr.service.DataWriteService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SuppressWarnings("unused")
@RestController
public class DataWriteController
{
    @Resource
    DataWriteService service;

    @PostMapping(value = "/api/program", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CommonMappedResult actionInsertData(
            HttpServletRequest req, HttpServletResponse resp,
            @RequestBody @Validated ProgramData data
    ) throws Exception
    {
        return service.insertData(req, resp, data);
    }

    @DeleteMapping(value = "/api/program/{id}", produces = APPLICATION_JSON_VALUE)
    public CommonMappedResult actionDeleteData(
            HttpServletRequest req, HttpServletResponse resp,
            @PathVariable("id") Integer id
    ) throws Exception
    {
        return service.deleteData(req, resp, id);
    }

    @PutMapping(value = "/api/program/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CommonMappedResult actionUpdateData(
            HttpServletRequest req, HttpServletResponse resp,
            @RequestBody @Validated ProgramData data,
            @PathVariable("id") Integer id
    ) throws Exception
    {
        return service.updateData(req, resp, data, id);
    }
}
