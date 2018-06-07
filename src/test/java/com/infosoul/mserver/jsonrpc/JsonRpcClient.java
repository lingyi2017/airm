/**
 * 
 */
package com.infosoul.mserver.jsonrpc;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

/**
 * <p> Title: JsonRpc Client例子</p>
 * 
 * <p> Description: </p>
 * 
 * <p> Copyright: Copyright (c) 2014 by ACTEC </p>
 * 
 * <p> Company: Free-Lancer </p>
 * 
 * @author: free lance
 * @version: 1.0
 * @date: 2014年6月12日 下午4:31:07
 * 
 */
public class JsonRpcClient {

    private static Logger logger = LoggerFactory.getLogger(JsonRpcClient.class);

    /**
     * @param args
     * @throws Throwable
     */
    public static void main(String[] args) throws Throwable {
        try {
            JsonRpcHttpClient client = new JsonRpcHttpClient(new URL("http://192.168.0.183:8080/index.json"));
            Map<String, String> params = new HashMap<String, String>();
            params.put("auth", "-");
            params.put("ip", "192.168.0.1");
            params.put("tag", "1");
            Object properties = client.invoke("getChannelsInfoFromRCU", params, Object.class);
            System.out.println(properties);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

    }

}
