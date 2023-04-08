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

@SuppressWarnings("unused")
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
            HashMap<String, Object> programResults = new HashMap<>();
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
        HashMap<String, Object> program = new HashMap<>();
        program.put("program", DataManager.getData((int) requestBody.get("id")));
        return new CommonMapResult(0, "OK", program);
    }

    @RequestMapping(value = "/get/search", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public CommonMapResult actionFindData(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Object type = requestBody.get("type"), num = requestBody.get("num"), name = requestBody.get("name");
        if(type == null && num == null && name == null)
        {
            response.sendError(400, "Missing parameter");
            return null;
        }
        if((type != null && !(type instanceof String)) ||
                (num != null && !(num instanceof Integer)) ||
                (name != null && !(name instanceof String))
        )
        {
            response.sendError(400, "Invalid parameter type");
            return null;
        }
        if(name != null && ((String) name).isBlank())
        {
            response.sendError(400, "Name is empty");
            return null;
        }
        HashMap<String, Object> programSearchResults = new HashMap<>();
        programSearchResults.put("programSearchResults", DataManager.findData((String) type, (Integer) num, (String) name));
        return new CommonMapResult(0, "OK", programSearchResults);
    }
}
