package vip.floatationdevice.progmgr.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.floatationdevice.progmgr.data.CommonMapResult;

@RestController
public class DataWriteController
{
    @RequestMapping("/add/program")
    public CommonMapResult actionInsertProgram() //TODO
    {
        return null;
    }

    @RequestMapping("/delete/program")
    public CommonMapResult actionDeleteProgram() //TODO
    {
        return null;
    }

    @RequestMapping("/update/program")
    public CommonMapResult actionUpdateProgram() //TODO
    {
        return null;
    }
}
