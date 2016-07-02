package com.peoplestouch.layouts;

import android.support.annotation.RawRes;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by BhavinPadhiyar on 16/06/16.
 */
public class JSONTransform extends JavaScript{

    public JSONTransform(@RawRes int jsFile, android.content.Context context, String[] methods) {
        super(jsFile,context,methods);
    }
    public JSONTransform(String javaScriptCode, String[] methods) {
        super(javaScriptCode,methods);
    }
    public JSONTransform(@RawRes int jsFile, android.content.Context context, String method) {
        super(jsFile,context,method);
    }
    public JSONTransform(String javaScriptCode, String method) {
        super(javaScriptCode,method);
    }

    public String transform(String json)
    {
        return call("php_crud_api_transform",json).toString();
    };
}
