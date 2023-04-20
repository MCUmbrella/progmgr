package vip.floatationdevice.progmgr.service.impl;

import cn.hutool.json.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.floatationdevice.progmgr.data.CommonMappedResult;
import vip.floatationdevice.progmgr.data.ProgramData;
import vip.floatationdevice.progmgr.service.DataWriteService;

import static vip.floatationdevice.progmgr.Main.getMapper;

@Service
@Slf4j
public class DataWriteServiceImpl implements DataWriteService
{
    @Override
    public CommonMappedResult insertData(HttpServletRequest req, HttpServletResponse resp, ProgramData data) throws Exception
    {
        log.info("Creating program: " + data.getName());
        getMapper().insertData(ProgramData.fromJson(new JSONObject(data)));
        resp.setStatus(201); // HTTP status code 201: content created
        return new CommonMappedResult(0, "Program created");
    }

    @Override
    public CommonMappedResult deleteData(HttpServletRequest req, HttpServletResponse resp, Integer id) throws Exception
    {
        if(getMapper().hasData(id) == 1)
        {
            log.info("Deleting program #" + id);
            getMapper().deleteData(id);
            return new CommonMappedResult(0, "Program deleted");
        }
        else resp.sendError(404, "Program with ID " + id + " not exists");
        return null;
    }

    @Override
    public CommonMappedResult updateData(HttpServletRequest req, HttpServletResponse resp, ProgramData data, Integer id) throws Exception
    {
        if(getMapper().hasData(id) == 1)
        {
            log.info("Updating program #" + id + " -> " + data.getName());
            data.setId(id);
            getMapper().updateData(data);
            return new CommonMappedResult(0, "Program updated");
        }
        else resp.sendError(404, "Program with ID " + data.getId() + " not exists");
        return null;
    }
}
