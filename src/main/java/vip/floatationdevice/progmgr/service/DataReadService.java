package vip.floatationdevice.progmgr.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vip.floatationdevice.progmgr.data.CommonMappedResult;

public interface DataReadService
{
    CommonMappedResult getDataCount();

    CommonMappedResult getPagedData(HttpServletRequest req, HttpServletResponse resp, Integer pageNum) throws Exception;

    CommonMappedResult getData(HttpServletRequest req, HttpServletResponse resp, Integer id) throws Exception;

    CommonMappedResult findData(HttpServletRequest req, HttpServletResponse resp, String name, String typeName, Integer actorCount) throws Exception;
}
