package ru.bugprod.webtable.client;

import com.nimbusds.jose.shaded.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class DataHubClient {

    private final String loginUrl = "http://datahub.yc.pbd.ai:9002/logIn";
    private final String graphqlUrl = "http://datahub.yc.pbd.ai:9002/api/graphiql";

    public Map<String, String> auth(String username, String password) throws IOException {
        JSONObject authData = new JSONObject();
        authData.appendField("username", username);
        authData.appendField("password", password);
        Connection.Response response = Jsoup.connect(loginUrl)
                .method(Connection.Method.POST)
                .header("Content-Type", "application/json;charset=UTF-8")
                .ignoreContentType(true)
                .requestBody(authData.toString())
                .execute();
        return response.cookies();
    }

    public String graphRequest(String graphql, Map<String, String> cookies) throws IOException {
        return Jsoup.connect(graphqlUrl)
                .method(Connection.Method.POST)
                .header("Content-Type", "application/json;charset=UTF-8")
                .cookies(cookies)
                .requestBody(graphql)
                .execute()
                .body();
    }
}
