package com.peoplestouch.layouts;

import android.support.annotation.RawRes;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by BhavinPadhiyar on 14/06/16.
 */
public class PHP_CRUD_API_Transform {

    private static Context rhino;
    private static Scriptable scope;
    private static Function jsFunction;

    public static void init(android.content.Context context)
    {
        if(jsFunction==null) {
            rhino = Context.enter();
            rhino.setOptimizationLevel(-1);
            scope = rhino.initStandardObjects();
            jsFunction = getFunction(context,R.raw.php_crud_api_transform, "php_crud_api_transform");
        }
    }
    public static Function getFunction(android.content.Context context,@RawRes int jsFile, String method)
    {
        try {
            InputStream is = context.getResources().openRawResource(jsFile);
            byte[]  buffer = new byte[is.available()];

            while (is.read(buffer) != -1);
            String javaScriptCode = new String(buffer);

            rhino.evaluateString(scope, javaScriptCode, "JavaScript", 1, null);
            Object obj = scope.get(method, scope);
            if (obj instanceof Function) {
                jsFunction = (Function) obj;
                return jsFunction;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String transform(String json)
    {
        if(json!=null) {
            Object[] params = new Object[]{json};
            Object jsResult = jsFunction.call(rhino, scope, scope, params);
            return (String) jsResult;
        }
        return null;
    }
}
