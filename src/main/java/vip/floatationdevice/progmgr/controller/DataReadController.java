package vip.floatationdevice.progmgr.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.floatationdevice.progmgr.data.CommonMappedResult;
import vip.floatationdevice.progmgr.service.DataReadService;

@SuppressWarnings("unused")
@RestController
public class DataReadController
{
    @Resource
    DataReadService service;

    @GetMapping("/api/programCount")
    public CommonMappedResult actionGetDataCount()
    {
        return service.getDataCount();
    }

    @GetMapping("/api/program")
    public CommonMappedResult actionGetPagedData(
            HttpServletRequest req, HttpServletResponse resp,
            @RequestParam(name = "pageNum") Integer pageNum
    ) throws Exception
    {
        return service.getPagedData(req, resp, pageNum);
    }

    @GetMapping("/api/program/{id}")
    public CommonMappedResult actionGetData(
            HttpServletRequest req, HttpServletResponse resp,
            @PathVariable("id") Integer id
    ) throws Exception
    {
        return service.getData(req, resp, id);
    }

    @GetMapping("/api/search")
    public CommonMappedResult actionFindData(
            HttpServletRequest req, HttpServletResponse resp,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "typeName", required = false) String typeName,
            @RequestParam(name = "actorCount", required = false) Integer actorCount
    ) throws Exception
    {
        return service.findData(req, resp, name, typeName, actorCount);
    }
}
