package vip.floatationdevice.progmgr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController
{
    @GetMapping("/test")
    @ResponseBody
    public Map<String, Object> testAction()
    {
        HashMap<String, Object> m = new HashMap<String, Object>();
        m.put("code", "0");
        m.put("message", "Hello world!");
        return m;
    }

    @GetMapping("/echo")
    @ResponseBody
    public Map<String, Object> echoAction(@RequestParam(value = "message", required = true) String message)
    {
        HashMap<String, Object> m = new HashMap<String, Object>();
        m.put("code", String.valueOf(message.length()));
        m.put("message", message);
        return m;
    }
}
