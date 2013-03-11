package helper;

/*
 * Copyright (c) Yahoo! Inc. All rights reserved.
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

/**
 * Utility class for HTTP Operations
 * 
 */
public class HTTPUtils {
    public static final String JSON_CONTENT = "application/json";
    public static final String CCM_CONTENT  = "application/x-ccm+json";

    private static List<Integer> validStatusCodes = new ArrayList<Integer>();

    static {
        validStatusCodes.add(HttpStatus.SC_ACCEPTED);
        validStatusCodes.add(HttpStatus.SC_OK);
        validStatusCodes.add(HttpStatus.SC_CREATED);
    }
    /**
     * Method to do HTTP get on a given url.
     * 
     * @param url
     *            - URL to be retrieved.
     * 
     * @return - content of the url
     * 
     * @throws IOException
     */
    public static String get(String url) throws IOException {
        HttpClient client = new HttpClient();

        GetMethod getMethod = new GetMethod(url);

        client.executeMethod(getMethod);

        return getResponse(getMethod);
    }


    public static InputStream get(String url,Map<String,String> headers, String proxyHost, String proxyPort, String userAgent) throws IOException {
        HttpClient client = new HttpClient();

        GetMethod getMethod = new GetMethod(url);

        if(headers != null){
            Iterator<Map.Entry<String,String>> headersIterator = headers.entrySet().iterator();
            while(headersIterator.hasNext()) {
                Map.Entry<String, String> pairs = headersIterator.next();
                getMethod.addRequestHeader(pairs.getKey(), pairs.getValue());
            }
        }

        if ((proxyHost != null) && (proxyHost.trim().length() != 0) && (proxyPort != null)
                && (proxyPort.trim().length() != 0)) {
            Integer proxyPortInt = new Integer(proxyPort);
            client.getHostConfiguration().setProxy(proxyHost, proxyPortInt);
        }

        if ((userAgent != null) && (userAgent.trim().length() != 0)) {
            getMethod.addRequestHeader("User-Agent", userAgent);
        }


        client.executeMethod(getMethod);

        return getResponseAsStream(getMethod, false);

    }

    
    public static InputStream get(String url, String proxyHost, String proxyPort, String userAgent) throws IOException {
        return get(url, proxyHost, proxyPort, userAgent, false);
    }

    public static InputStream get(String url, String proxyHost, String proxyPort, String userAgent, boolean excep) throws IOException {
        GetMethod getMethod = new GetMethod(url);
        HttpClient client = new HttpClient();
        if ((proxyHost != null) && (proxyHost.trim().length() != 0) && (proxyPort != null)
                && (proxyPort.trim().length() != 0)) {
            Integer proxyPortInt = new Integer(proxyPort);
            client.getHostConfiguration().setProxy(proxyHost, proxyPortInt);
        }

        if ((userAgent != null) && (userAgent.trim().length() != 0)) {
            getMethod.addRequestHeader("User-Agent", userAgent);
        }

        client.executeMethod(getMethod);
        return getResponseAsStream(getMethod, excep);
    }

    /**
     * Method to do HTTP delete on a given url.
     * 
     * @param url
     *            - URL to be retrieved.
     * 
     * @return - success/failure
     * 
     * @throws IOException
     */
    public static boolean delete(String url) throws IOException {
        HttpClient client = new HttpClient();

        DeleteMethod deleteMethod = new DeleteMethod(url);

        client.executeMethod(deleteMethod);
        boolean response = true;
        if (deleteMethod.getStatusCode() != 200) {
            response = false;
        }
        LogUtils.info(HTTPUtils.class, "Delete response: " + deleteMethod.getStatusCode());
        return response;
    }

    /**
     * Method to do HTTP get on a given url.
     * 
     * @param url
     *            - URL to be retrieved.
     * @param excep
     *            - if true, throws Exception when Http Status Code is other
     *            than 200.
     * 
     * @return - content of the url
     * 
     * @throws IOException
     */
    public static String get(String url, boolean excep) throws IOException {
        HttpClient client = new HttpClient();

        GetMethod getMethod = new GetMethod(url);

        client.executeMethod(getMethod);

        return getResponse(getMethod, excep);
    }

    /**
     * Method to HTTP post on a given url.
     * 
     * @param url
     *            - URL to which data is posted.
     * @param data
     *            - Data to be posted.
     * 
     * @return - response for the post.
     * 
     * @throws IOException
     */
    public static String post(String url, String data) throws IOException {
        return post(url, data, JSON_CONTENT, false);
    }

    /**
     * Method to HTTP post on a given url.
     * 
     * @param url
     *            - URL to which data is posted.
     * @param data
     *            - Data to be posted.
     * @param type
     *            - Content type
     * @param excep
     *            - if true, throws Exception when Http Status Code is other
     *            than 200.
     * 
     * @return - response for the post.
     * 
     * @throws IOException
     */
    public static String post(String url, String data, String type, boolean excep) throws IOException {
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(url);

        StringRequestEntity entity = new StringRequestEntity(data, type, "UTF-8");

        postMethod.setRequestEntity(entity);

        client.executeMethod(postMethod);

        return getResponse(postMethod, excep);
    }


    /**
     * Method to post with custom headers.
     * 
     * @param url URL to which data is posted.
     * @param body Data to be posted.
     * @param type Content type.
     * @param headers Map of custom headers.
     * @param proxyHost Proxy host.
     * @param proxyPort Proxy port.
     * @return response for the post.
     * @throws IOException
     */
    public static String post(String url, String body, String type, Map<String, String> headers, String proxyHost, String proxyPort) throws IOException {
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(url);

        if (headers != null) {
            Iterator<Map.Entry<String,String>> headersIterator = headers.entrySet().iterator();
            while(headersIterator.hasNext()) {
                Map.Entry<String, String> pairs = headersIterator.next();
                postMethod.addRequestHeader(pairs.getKey(), pairs.getValue());
            }
        }

        if ((proxyHost != null) && (proxyHost.trim().length() != 0) && (proxyPort != null)
                && (proxyPort.trim().length() != 0)) {
            Integer proxyPortInt = new Integer(proxyPort);
            client.getHostConfiguration().setProxy(proxyHost, proxyPortInt);
        }

        StringRequestEntity entity = new StringRequestEntity(body, type, "UTF-8");
        postMethod.setRequestEntity(entity);
        client.executeMethod(postMethod);
        return getResponse(postMethod, true);
    }

    /**
     * Method to HTTP put on a given url.
     * 
     * @param url
     *            - URL to which data is put.
     * @param data
     *            - Data to be put.
     * 
     * @return - response for the put.
     * 
     * @throws IOException
     */
    public static String put(String url, String data) throws IOException {
        return put(url, data, JSON_CONTENT, false);
    }

    /**
     * Method to HTTP put on a given url.
     * 
     * @param url
     *            - URL to which data is put.
     * @param data
     *            - Data to be put.
     * @param type
     *            - Content Type
     * @param excep
     *            - if true, throws Exception when Http Status Code is other
     *            than 200.
     * 
     * @return - response for the put.
     * 
     * @throws IOException
     */
    public static String put(String url, String data, String type, boolean excep) throws IOException {
        HttpClient client = new HttpClient();

        PutMethod putMethod = new PutMethod(url);

        StringRequestEntity entity = new StringRequestEntity(data, type, "UTF-8");

        putMethod.setRequestEntity(entity);

        client.executeMethod(putMethod);

        return getResponse(putMethod, excep);
    }

    public static String put(String url, InputStream data, String type, Map<String, String> headers, boolean exception) throws IOException {
        HttpClient client = new HttpClient();
        PutMethod putMethod = new PutMethod(url);

        Iterator<String> headerKeys = headers.keySet().iterator();

        while (headerKeys.hasNext()) {
            String header = headerKeys.next();
            String value  = headers.get(header);
            putMethod.setRequestHeader(header, value);
        }

        putMethod.setRequestEntity(new InputStreamRequestEntity(data));
        client.executeMethod(putMethod);

        return getResponse(putMethod, exception);
    }

    /**
     * Checks for Http Status and returns the reponse if Http Status is OK
     * (200).
     * 
     * If there is Http Error, response will be null and the Status Code is
     * logged.
     * 
     * @param method
     *            - Http Method
     * @return String - response for the Http Call
     * @throws IOException
     */
    private static String getResponse(HttpMethod method) throws IOException {
        return getResponse(method, false);
    }

    /**
     * Checks for Http Status and returns the reponse if Http Status is OK
     * (200).
     * 
     * 
     * @param method
     *            - Http Method
     * @param excep
     *            - if true, throws Exception when Http Status Code is other
     *            than 200.
     * 
     * @return String - response for the Http Call
     * @throws IOException
     */
    private static String getResponse(HttpMethod method, boolean excep) throws IOException {
        String response = null;

        if (validStatusCodes.contains(method.getStatusCode())) {
        	byte[] responseBytes = method.getResponseBody();
            response = new String(responseBytes, "UTF-8");
        } else {
            StringBuilder msgBuilder = new StringBuilder("Http Error occurred with the url : ").append(method.getURI())
                    .append("; Status code: ").append(method.getStatusCode()).append("; Message: ")
                    .append(method.getResponseBodyAsString());

            if (excep) {
                throw new IOException(msgBuilder.toString());
            } else {
                // Change this to info if too much of logs are printed
                LogUtils.error(HTTPUtils.class, msgBuilder.toString());
            }
        }

        return response;
    }

    private static InputStream getResponseAsStream(HttpMethod method, boolean excep) throws IOException {
        InputStream response = null;

        if (method.getStatusCode() == HttpStatus.SC_OK) {
            response = method.getResponseBodyAsStream();
        } else {
            StringBuilder msgBuilder = new StringBuilder("Http Error occurred with the url : ").append(method.getURI())
                    .append("; Status code: ").append(method.getStatusCode()).append("; Message: ")
                    .append(method.getResponseBodyAsString());
            if (excep) {
                throw new IOException(msgBuilder.toString());
            } else {
                LogUtils.warn(HTTPUtils.class, msgBuilder.toString());
            }
        }
        return response;
    }

}

