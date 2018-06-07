package com.infosoul.mserver.log.user;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.infosoul.mserver.common.web.Servlets;
import com.infosoul.mserver.dto.user.UserDTO;


public class LoginClient {
	
   public static void main(String[] args) {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://localhost:8080/mserver/rs/app/user").path("login");
            
            String authentication = Servlets.encodeHttpBasic("superadmin", "admin");
            
            // post方式登录（传UserDTO对象）
            UserDTO userDTO = new UserDTO();
            userDTO.setLoginName("superadmin");
            userDTO.setPassword("admin");
            login(target, userDTO, authentication);
            
            // post方式登录(传字符串)
            //loginByPostStr(target, "longxy");
            
            // get方式登录
            //loginByGet(target, "longxy", "admin");
            
            // post方式通过Id查找用户（用于测试DTO） 
            //findUserById(target, "admin");
        }
        catch (Exception e) {
            throw new WebApplicationException();
        }
    }
   
   // post方式登录（传UserDTO对象）
   private static void login(WebTarget target, Object obj, String authentication){
	   Response response = target.request(MediaType.APPLICATION_JSON).header(Servlets.AUTHENTICATION_HEADER, authentication).
			   post(Entity.entity(obj, MediaType.APPLICATION_JSON));
	   System.out.println("-----------response响应信息-------------" + response);
	   System.out.println("-----------客户端返回信息----------------" + response.readEntity(String.class));
	   response.close();
   }
   
   // post方式登录（传UserDTO对象）
   /*private static void login(WebTarget target, Object obj){
	   Response response = target.request(MediaType.APPLICATION_JSON).
			   post(Entity.entity(obj, MediaType.APPLICATION_JSON));
	   System.out.println("-----------response响应信息-------------" + response);
	   System.out.println("-----------客户端返回信息----------------" + response.readEntity(String.class));
	   response.close();
   }*/
   
   // post方式登录（传字符串）
   private static void loginByPostStr(WebTarget target, String username){
	   Response response = target.request(MediaType.APPLICATION_JSON).
			   post(Entity.entity(username, MediaType.APPLICATION_JSON));
	   System.out.println("-----------POST方式登录-------------" + response);
	   System.out.println("-----------POST方式登录-------------" + response.readEntity(String.class));
	   response.close();
   }
   
   // get方式登录
   private static void loginByGet(WebTarget target, String username, String password){
	   WebTarget loginQueryParam = target.queryParam("username", username).queryParam("password", password);
	   Invocation.Builder invocationBuilder = loginQueryParam.request(MediaType.APPLICATION_JSON);
	   invocationBuilder.header("some-header", "true");
	   Response response = invocationBuilder.get();
	   System.out.println("-----------GET方式登录-------------" + response);
	   System.out.println("-----------GET方式登录-------------" + response.readEntity(String.class));
	   response.close();
   }
   
   // post方式通过Id查找用户（用于测试DTO）
   private static void findUserById(WebTarget target, String id){
	   Response response = target.request(MediaType.APPLICATION_JSON).
			   post(Entity.entity(id, MediaType.APPLICATION_JSON));
	   System.out.println("-----------POST方式登录-------------" + response);
	   System.out.println("-----------POST方式登录-------------" + response.readEntity(String.class));
	   response.close();
   }
	
}
