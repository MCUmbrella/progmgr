package vip.floatationdevice.progmgr.controller;

import org.springframework.web.bind.annotation.*;
import vip.floatationdevice.progmgr.data.CommonMapResult;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class TestController
{
    @GetMapping("/test")
    @ResponseBody
    public CommonMapResult testAction()
    {
        return new CommonMapResult(0, "Hello world!", null);
    }

    @GetMapping("/echo")
    @ResponseBody
    public CommonMapResult echoAction(@RequestParam(value = "message", required = true) String message)
    {
        return new CommonMapResult(message.length(), message, null);
    }

    @RequestMapping(value = "/greet", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CommonMapResult greetAction(@RequestBody Map<String, Object> requestBody)
    {
        String name = (String) requestBody.get("name");
        return new CommonMapResult(0, "OK", "Hello " + name);
    }
}
