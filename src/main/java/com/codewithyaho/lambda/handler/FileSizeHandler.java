package com.codewithyaho.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.codewithyaho.lambda.model.RequestInput;
import com.codewithyaho.lambda.service.FileSizeFormatService;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class FileSizeHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
        final Gson gson = new Gson();
        LambdaLogger logger = context.getLogger();
        logger.log("FileSizeHandler:: start lambda");
        String request = gson.toJson(apiGatewayProxyRequestEvent);
        logger.log("FileSizeHandler:: received request : " + request);

        String httpMethod = apiGatewayProxyRequestEvent.getHttpMethod();
        String requestEventBody = apiGatewayProxyRequestEvent.getBody();
        return switch (httpMethod) {
            case "POST" -> {
                RequestInput requestInput = gson.fromJson(requestEventBody, RequestInput.class);
                yield createAPIResponse(FileSizeFormatService.convertFileSizeToReadableFormat(requestInput), 200);
            }
            default -> throw new Error("FileSizeHandler:: Unsupported Methods:::" + httpMethod);
        };
    }

    private static APIGatewayProxyResponseEvent createAPIResponse(String body, int statusCode) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setBody(body);
        responseEvent.setHeaders(createHeaders());
        responseEvent.setStatusCode(statusCode);
        return responseEvent;
    }

    private static Map<String, String> createHeaders() {
        Map<String, String> headers = new HashMap();
        headers.put("Content-Type", "application/json");
        headers.put("Access-Control-Allow-Headers", "*");
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        return headers;
    }
}
