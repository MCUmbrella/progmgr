package vip.floatationdevice.progmgr.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.floatationdevice.progmgr.DataManager;
import vip.floatationdevice.progmgr.data.CommonMappedResult;

import java.util.HashMap;

@SuppressWarnings("unused")
@RestController
public class DataReadController
{
    @GetMapping("/get/programCount")
    public CommonMappedResult actionGetDataCount()
    {
        return new CommonMappedResult(0, "OK", DataManager.getDataCount());
    }

    @GetMapping("/get/programList")
    public CommonMappedResult actionGetPagedData(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "pageNum") Integer pageNum) throws Exception
    {
        HashMap<String, Object> programResults = new HashMap<>();
        programResults.put("programResults", DataManager.getPagedData(pageNum - 1));
        return new CommonMappedResult(0, "OK", programResults);
    }

    @GetMapping("/get/program")
    public CommonMappedResult actionGetData(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "id") Integer id) throws Exception
    {
        if(DataManager.hasData(id))
        {
            HashMap<String, Object> program = new HashMap<>();
            program.put("program", DataManager.getData(id));
            return new CommonMappedResult(0, "OK", program);
        }
        response.sendError(404, "Program with ID " + id + " not exists");
        return null;
    }

    @GetMapping("/get/search")
    public CommonMappedResult actionFindData(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "num", required = false) Integer num,
            @RequestParam(name = "name", required = false) String name
    ) throws Exception
    {
        if(type == null && num == null && name == null)
        {
            response.sendError(400, "Missing parameter");
            return null;
        }
        if(name != null && name.isBlank())
        {
            response.sendError(400, "Name is empty");
            return null;
        }
        HashMap<String, Object> programSearchResults = new HashMap<>();
        programSearchResults.put("programSearchResults", DataManager.findData(type, num, name));
        return new CommonMappedResult(0, "OK", programSearchResults);
    }
}
