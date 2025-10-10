package com.jaya.utils.api;

import com.jaya.utils.config.YamlConfig;
import io.restassured.response.*;
import com.jaya.utils.*;
import io.restassured.path.json.exception.JsonPathException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/** Simple HTTP client for auth endpoints used in tests. */
public class AuthApiClient {

    private String base() {
        try {
            String b = YamlConfig.getBaseUrl();
            if (b != null && !b.isBlank()) return b.endsWith("/") ? b.substring(0, b.length()-1) : b;
        } catch (Exception ignored) {}
        return System.getProperty("test.baseUrl", "http://localhost:8080");
    }

    public Map<String, Object> signup(String first, String last, String email, String password, String gender) {
        String template = JsonTemplates.load("api/auth/signup.json");
        String body = JsonTemplates.apply(template, new HashMap<String,String>() {{
            put("firstName", first);
            put("lastName", last);
            put("email", email);
            put("password", password);
            put("gender", gender);
        }});
    String[] endpoints = {"/auth/signup", "/api/auth/signup"};
    Response resp = null;
    String used = null;
    for (String ep : endpoints) {
        resp = given()
            .baseUri(base())
            .contentType("application/json")
            .body(body)
            .post(ep)
            .thenReturn();
        used = ep;
        if (resp.statusCode() != 404) break; // choose first non-404
    }
    Map<String,Object> map = new HashMap<>();
    map.put("status", resp.statusCode());
    map.put("body", resp.getBody().asString());
    map.put("token", safeJson(resp, "jwt"));
    map.put("endpoint", used);
        return map;
    }

    public String signin(String email, String password) {
        Response resp = given()
                .baseUri(base())
                .contentType("application/json")
                .body(new HashMap<String, Object>() {{
                    put("email", email);
                    put("password", password);
                }})
                .post("/auth/signin")
                .thenReturn();
        if (resp.statusCode() >= 400) return null;
        return resp.jsonPath().getString("jwt");
    }

    public Map<String,Object> signinDetails(String email, String password) {
        String template = JsonTemplates.load("api/auth/signin.json");
        String body = JsonTemplates.apply(template, new HashMap<String,String>() {{
            put("email", email);
            put("password", password);
        }});
        System.out.println("body"+body);
        String endpoint = "/auth/signin";
        Response resp = null;
        String used = null;
            resp = given().baseUri("http://localhost:8080")
                    .contentType("application/json")
                    .body(body)
                    .post(endpoint);

        Map<String,Object> map = new HashMap<>();
        map.put("httpStatus", resp.statusCode());
        map.put("jwt", safeJson(resp, "jwt"));
        map.put("message", safeJson(resp, "message"));
        map.put("status", safeJson(resp, "status"));
        map.put("rawBody", resp.getBody().asString());
        map.put("endpoint", used);
        if (resp.getBody().asString().startsWith("<!DOCTYPE html")) {
            map.put("htmlError", true);
        }
        System.out.println("response"+map);
        return map;
    }

    public Map<String,String> getProfile(String jwt) {
        Response resp = given()
                .baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + jwt)
                .get("/api/user/profile")
                .thenReturn();
        Map<String,String> profile = new HashMap<>();
        if (resp.statusCode() >= 400) return profile;
        profile.put("firstName", safeJson(resp, "firstName"));
        profile.put("lastName", safeJson(resp, "lastName"));
        profile.put("email", safeJson(resp, "email"));
        return profile;
    }

    private String safeJson(Response resp, String path) {
        try {
            return resp.jsonPath().getString(path);
        } catch (JsonPathException e) {
            return null;
        }
    }
}
