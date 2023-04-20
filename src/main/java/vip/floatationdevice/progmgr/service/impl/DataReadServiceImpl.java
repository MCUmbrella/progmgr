package vip.floatationdevice.progmgr.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import vip.floatationdevice.progmgr.data.CommonMappedResult;
import vip.floatationdevice.progmgr.service.DataReadService;

import static vip.floatationdevice.progmgr.Main.getMapper;

@Service
public class DataReadServiceImpl implements DataReadService
{
    @Override
    public CommonMappedResult getDataCount()
    {
        return new CommonMappedResult(0, "OK", getMapper().getDataCount());
    }

    @Override
    public CommonMappedResult getPagedData(HttpServletRequest req, HttpServletResponse resp, Integer pageNum) throws Exception
    {
        // do a little conversion. the page number that front-end pass start from 1 and in intervals of 5
        // e.g. pageNum: 1 -> program[0, 4], pageNum: 2 -> program[5, 9]
        return new CommonMappedResult(0, "OK", getMapper().getPagedData((pageNum - 1) * 5));
    }

    @Override
    public CommonMappedResult getData(HttpServletRequest req, HttpServletResponse resp, Integer id) throws Exception
    {
        if(getMapper().hasData(id) == 1) // has one and only one data with the specified ID?
            return new CommonMappedResult(0, "OK", getMapper().getData(id));
        resp.sendError(404, "Program with ID " + id + " not exists");
        return null;
    }

    @Override
    public CommonMappedResult findData(HttpServletRequest req, HttpServletResponse resp, String name, String typeName, Integer actorCount) throws Exception
    {
        // the client must specify at least 1 search criteria
        if(name == null && typeName == null && actorCount == null)
        {
            resp.sendError(400, "Missing parameter");
            return null;
        }
        // name must contain something other than blank, if specified
        if(name != null && name.isBlank())
        {
            resp.sendError(400, "Name is empty");
            return null;
        }
        // no problems found, go on
        return new CommonMappedResult(0, "OK", getMapper().findData(typeName, actorCount, name));
    }
}
