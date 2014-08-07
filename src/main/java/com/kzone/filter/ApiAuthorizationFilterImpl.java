package com.kzone.filter;

/**
 * Created by jeffy on 2014/8/4 0004.
 */
import com.wordnik.swagger.model.*;
import com.wordnik.swagger.core.filter.SwaggerSpecFilter;

import org.slf4j.*;

import java.util.Map;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
/**
 *
 * The rules are maintained in simple map with key as path and a boolean value
 * indicating given path is secure or not. For method level security the key is
 * combination of http method and path .
 *
 * If the resource or method is secure then it can only be viewed using a
 * secured api key
 *
 * Note: Objective of this class is not to provide fully functional
 * implementation of authorization filter. This is only a sample demonstration
 * how API authorization filter works.
 *
 */

public class ApiAuthorizationFilterImpl implements SwaggerSpecFilter {
    static Logger logger = LoggerFactory.getLogger(ApiAuthorizationFilterImpl.class);

    public boolean isOperationAllowed(Operation operation, ApiDescription api, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        boolean isAuthorized = checkKey(params, headers);
        if(isAuthorized) {
            return true;
        }
        else {
            if(!"GET".equals(operation.method()) || api.path().indexOf("/store") != -1) {
                return false;
            }
            else return true;
        }
    }

    public boolean isParamAllowed(Parameter parameter, Operation operation, ApiDescription api, Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        boolean isAuthorized = checkKey(params, headers);
        if((parameter.paramAccess().isDefined() && parameter.paramAccess().get().equals("internal")) && !isAuthorized)
            return false;
        else
            return true;
    }

    public boolean checkKey(Map<String, List<String>> params, Map<String, List<String>> headers) {
        String keyValue = null;
        if(params.containsKey("api_key"))
            keyValue = params.get("api_key").get(0);
        else {
            if(headers.containsKey("api_key"))
                keyValue = headers.get("api_key").get(0);
        }
        if("special-key".equals(keyValue))
            return true;
        else
            return false;
    }
}
