package com.mephalay.spider;

/**
 * Created by Mephalay on 2/9/2016.
 */
public class JsonResponse {
    private Boolean success;
    private JsonResult result;

    public JsonResponse() {

    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public JsonResult getResult() {
        return result;
    }

    public void setResult(JsonResult result) {
        this.result = result;
    }
}
