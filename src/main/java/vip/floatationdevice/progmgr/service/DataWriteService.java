package vip.floatationdevice.progmgr.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vip.floatationdevice.progmgr.data.CommonMappedResult;
import vip.floatationdevice.progmgr.data.ProgramData;

public interface DataWriteService
{
    CommonMappedResult insertData(HttpServletRequest req, HttpServletResponse resp, ProgramData data) throws Exception;

    CommonMappedResult deleteData(HttpServletRequest req, HttpServletResponse resp, Integer id) throws Exception;

    CommonMappedResult updateData(HttpServletRequest req, HttpServletResponse resp, ProgramData data, Integer id) throws Exception;
}
