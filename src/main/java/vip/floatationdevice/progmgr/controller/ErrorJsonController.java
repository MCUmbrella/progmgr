package vip.floatationdevice.progmgr.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ErrorJsonController implements ErrorController
{
    @RequestMapping("/error")
    @ResponseBody
    public Map<String, Object> action(HttpServletRequest request)
    {
        HashMap<String, Object> m = new HashMap<String, Object>();
        m.put("code", String.valueOf(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)));
        m.put("message", request.getAttribute(RequestDispatcher.ERROR_MESSAGE));
        return m;
    }
}
