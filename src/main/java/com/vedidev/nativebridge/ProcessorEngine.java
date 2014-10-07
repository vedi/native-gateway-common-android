package com.vedidev.nativebridge;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vedi
 *         date 05/10/14
 */
@SuppressWarnings("UnusedDeclaration")
public class ProcessorEngine {
    private static ProcessorEngine INSTANCE;

    public static interface CallHandler {
        void handle(JSONObject params, JSONObject retParams) throws Exception;
    }

    private Map<String, CallHandler> callHandlers = new HashMap<String, CallHandler>();

    public static ProcessorEngine getInstance() {
        if (INSTANCE == null) {
            synchronized (ProcessorEngine.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ProcessorEngine();
                }
            }
        }
        return INSTANCE;
    }

    public ProcessorEngine() {
        this.registerProcessor("BunchManager", "registerBunch", new ProcessorEngine.CallHandler() {
            @Override
            public void handle(JSONObject params, JSONObject retParams) throws Exception {
                String bunchClassName = params.getString("bunch");
                @SuppressWarnings("unchecked")
                Class<Bunch> bunchClass = (Class<Bunch>) Class.forName("com.vedidev.nativebridge." +  bunchClassName);
                BunchManager.getInstance().registerBunch(bunchClass.newInstance());
            }
        });
    }

    public void registerProcessor(String bunch, String key, CallHandler callHandler) {
        callHandlers.put(buildDictKey(bunch, key), callHandler);
    }

    public JSONObject proceed(JSONObject params) {
        JSONObject retParameters = new JSONObject();
        try {
            String bunch = params.getString("bunch");
            String methodName = params.getString("method");

            String dictKey = this.buildDictKey(bunch, methodName);

            CallHandler callHandler = callHandlers.get(dictKey);
            if (callHandler != null) {
                try {
                    callHandler.handle(params.getJSONObject("params"), retParameters);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            } else {
                throw new UnsupportedOperationException(methodName);
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }

        return retParameters;
    }

    private String buildDictKey(String bunch, String methodName) {
        return bunch + "." + methodName;
    }
}
