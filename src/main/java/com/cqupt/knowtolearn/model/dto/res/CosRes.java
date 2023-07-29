package com.cqupt.knowtolearn.model.dto.res;

import java.net.URL;

/**
 * @author Ray
 * @date 2023/7/29 20:11
 * @description
 */
public class CosRes {

    private URL requestURL;

    private String resourceURL;

    public URL getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(URL requestURL) {
        this.requestURL = requestURL;
    }

    public String getResourceURL() {
        return resourceURL;
    }

    public void setResourceURL(String resourceURL) {
        this.resourceURL = resourceURL;
    }
}
