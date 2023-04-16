package vip.floatationdevice.progmgr.controller;

import org.springframework.web.bind.annotation.*;
import vip.floatationdevice.progmgr.data.CommonMappedResult;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SuppressWarnings("unused")
@RestController
public class TestController
{
    @GetMapping("/test")
    public CommonMappedResult testAction()
    {
        return new CommonMappedResult(0, "Hello world!");
    }

    @GetMapping("/echo")
    public CommonMappedResult echoAction(@RequestParam(value = "message") String message)
    {
        return new CommonMappedResult(message.length(), message);
    }

    @PostMapping(value = "/greet", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CommonMappedResult greetAction(@RequestBody Map<String, Object> requestBody)
    {
        String name = (String) requestBody.get("name");
        return new CommonMappedResult(0, "OK", "Hello " + name);
    }
}
