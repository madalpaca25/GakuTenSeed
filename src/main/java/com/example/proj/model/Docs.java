package com.example.proj.model;

import org.apache.commons.lang3.StringUtils;

public class Docs {

    private String key;
    private String title;
    private String cover_i;
    private String[] author_name;

    public String[] getAuthor_name() {
        return author_name;
    }
    public void setAuthor_name(String[] author_name) {
        this.author_name = author_name;
    }

    public String getCover_i() {
        return cover_i;
    }
    public void setCover_i(String cover_i) {
        String str = "https://covers.openlibrary.org/b/id/"+cover_i+"-L.jpg";
        this.cover_i = str;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        String str = StringUtils.remove(key, "/works/");
        this.key = str;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    
}
