package vip.floatationdevice.progmgr.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.floatationdevice.progmgr.DataManager;
import vip.floatationdevice.progmgr.data.CommonMappedResult;

@SuppressWarnings("unused")
@RestController
public class DataReadController
{
    @GetMapping("/api/programCount")
    public CommonMappedResult actionGetDataCount()
    {
        return new CommonMappedResult(0, "OK", DataManager.getDataCount());
    }

    @GetMapping("/api/program")
    public CommonMappedResult actionGetPagedData(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(name = "pageNum") Integer pageNum
    ) throws Exception
    {
        return new CommonMappedResult(0, "OK", DataManager.getPagedData(pageNum - 1));
    }

    @GetMapping("/api/program/{id}")
    public CommonMappedResult actionGetData(
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable("id") Integer id
    ) throws Exception
    {
        if(DataManager.hasData(id))
            return new CommonMappedResult(0, "OK", DataManager.getData(id));
        response.sendError(404, "Program with ID " + id + " not exists");
        return null;
    }

    @GetMapping("/api/search")
    public CommonMappedResult actionFindData(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "typeName", required = false) String typeName,
            @RequestParam(name = "actorCount", required = false) Integer actorCount
    ) throws Exception
    {
        if(name == null && typeName == null && actorCount == null)
        {
            response.sendError(400, "Missing parameter");
            return null;
        }
        if(name != null && name.isBlank())
        {
            response.sendError(400, "Name is empty");
            return null;
        }
        return new CommonMappedResult(0, "OK", DataManager.findData(name, typeName, actorCount));
    }
}
