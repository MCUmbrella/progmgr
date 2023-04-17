package vip.floatationdevice.progmgr.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.floatationdevice.progmgr.data.CommonMappedResult;

@SuppressWarnings("unused")
@RestController
public class ErrorJsonController implements ErrorController
{
    @RequestMapping("/error")
    public CommonMappedResult action(HttpServletRequest request)
    {
        return new CommonMappedResult(
                (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE),
                (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE)
        );
    }
}
