package com.example.proj.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;


import com.example.proj.model.BooksResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.ActionSupport;

public class Books extends ActionSupport {

    BooksResponse booksResponse = new BooksResponse();
    private String myQuery;
    private String currentToken;
    private String currentSession;
    
    
    public String execute() throws Exception{

        try {if (getMyQuery() != null){
            String myString = getMyQuery().replaceAll(" ", "+").toLowerCase();
            URL url = new URL ("http://openlibrary.org/search.json?title="+myString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode()); 
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
             
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                output =  br.lines().collect(Collectors.joining());
                booksResponse = mapper.readValue(output, BooksResponse.class);

            conn.disconnect();
        }

        } catch (Exception e) {
            e.printStackTrace();
        } return currentSession;
    }
    

    public BooksResponse getBooksResponse() {
        return booksResponse;
    }

    public void setBooksResponse(BooksResponse booksResponse) {
        this.booksResponse = booksResponse;
    }

    public String getMyQuery() {
        return myQuery;
    }

    public void setMyQuery(String myQuery) {
        this.myQuery = myQuery;
    }
    
    public String getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(String currentToken) {
        this.currentToken = currentToken;
    }

}

