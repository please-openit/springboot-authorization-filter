package it.pleaseopen.demo;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class PleaseOpenItFilter implements Filter {

    @Value("${please-open-it.controllerId}")
    String controllerId;

    @Value("${please-open-it.provider}")
    String provider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        HttpServletRequest req = (HttpServletRequest) request;
        String sessionId = exchangeToken(provider, ((HttpServletRequest) request).getHeader("Authorization").split(" ")[1] );
        try {

            HttpResponse<String> pleaseOpenItResponse = Unirest.get("https://access.please-open.it/access/")
                    .header("Accept", "*/*")
                    .header("Cache-Control", "no-cache")
                    .header("Authorization", sessionId)
                    .header("controllerId", controllerId)
                    .asString();
            if(pleaseOpenItResponse.getStatus() == 200){
                chain.doFilter(request, response);
            }else{
                ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
                ((HttpServletResponse) response).setStatus(401);
            }
        }catch (Exception ex){
            ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
            ((HttpServletResponse) response).setStatus(500);
        }
    }

    public String exchangeToken(String provider, String token){
        JSONObject tokenExchangeOperation = new JSONObject();
        tokenExchangeOperation.put("provider", provider);
        tokenExchangeOperation.put("token", token);
        try {
            HttpResponse<String> pleaseOpenItResponse = Unirest.post("https://access.please-open.it/login/fromToken")
                    .header("Content-Type", "application/json;charset=utf-8")
                    .header("Accept", "*/*")
                    .header("Cache-Control", "no-cache")
                    .body(tokenExchangeOperation)
                    .asString();
            return pleaseOpenItResponse.getBody();
        } catch (UnirestException e) {
            return null;
        }
    }

    @Override
    public void init(FilterConfig filterConfig){  }

    @Override
    public void destroy(){  }
}
