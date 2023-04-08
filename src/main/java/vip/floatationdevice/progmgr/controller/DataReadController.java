package vip.floatationdevice.progmgr.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import vip.floatationdevice.progmgr.DataManager;
import vip.floatationdevice.progmgr.data.CommonMapResult;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class DataReadController
{
    @RequestMapping("/get/programCount")
    @ResponseBody
    public CommonMapResult actionGetDataCount()
    {
        return new CommonMapResult(0, "OK", DataManager.getDataCount());
    }

    @RequestMapping(value = "/get/programList", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public CommonMapResult actionGetPagedData(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        if(requestBody.get("pageNum") == null || !(requestBody.get("pageNum") instanceof Integer))
        {
            response.sendError(400, "Missing parameter");
            return null;
        }
        else
        {
            HashMap programResults = new HashMap();
            programResults.put("programResults", DataManager.getPagedData((int) requestBody.get("pageNum")));
            return new CommonMapResult(0, "OK", programResults);
        }
    }

    @RequestMapping(value = "/get/program", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public CommonMapResult actionGetData(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        if(requestBody.get("id") == null || !(requestBody.get("id") instanceof Integer))
        {
            response.sendError(400, "Missing parameter");
            return null;
        }
        if(!DataManager.hasData((int) requestBody.get("id")))
        {
            response.sendError(404, "No such program");
            return null;
        }
        HashMap program = new HashMap();
        program.put("program", DataManager.getData((int) requestBody.get("id")));
        return new CommonMapResult(0, "OK", program);
    }

    @RequestMapping(value = "/get/search", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public CommonMapResult actionFindData(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        if(requestBody.get("type") == null && requestBody.get("num") == null && requestBody.get("name") == null)
        {
            response.sendError(400, "Missing parameter");
            return null;
        }
        if((requestBody.get("type") != null && !(requestBody.get("type") instanceof String)) ||
                (requestBody.get("num") != null && !(requestBody.get("num") instanceof Integer)) ||
                (requestBody.get("name") != null && !(requestBody.get("name") instanceof String))
        )
        {
            response.sendError(400, "Invalid parameter type");
            return null;
        }
        HashMap programSearchResults = new HashMap();
        programSearchResults.put("programSearchResults", DataManager.findData((String) requestBody.get("type"), (Integer) requestBody.get("num"), (String) requestBody.get("name")));
        return new CommonMapResult(0, "OK", programSearchResults);
    }
}
