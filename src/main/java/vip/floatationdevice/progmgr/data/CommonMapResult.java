package vip.floatationdevice.progmgr.data;

/**
 * The result returned to the HTTP client. Will be converted to JSON by Spring automatically.<br>
 * Expected format:
 * <pre>
 * {
 *   "code": {code}
 *   "message": {message}
 *   "data": {data}
 * }
 * </pre>
 */
public class CommonMapResult
{
    public final String code;
    public final String message;
    public final Object data;

    /**
     * Generate a common result.
     * @param code The status code of the result.
     * @param message The status message of the result.
     * @param data The data associated to the result.
     */
    public CommonMapResult(int code, String message, Object data)
    {
        this.code = String.valueOf(code);
        this.message = message;
        this.data = data;
    }
}
