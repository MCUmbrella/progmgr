package vip.floatationdevice.progmgr;

public class CommonUtil
{
    public static boolean hasNull(Object... objects)
    {
        for(Object o : objects)
            if(o == null)
                return true;
        return false;
    }

    public static boolean isBlank(Object o)
    {
        return o != null && o.toString().isBlank();
    }
}
