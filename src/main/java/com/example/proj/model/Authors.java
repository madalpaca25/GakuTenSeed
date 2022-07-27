package com.example.proj.model;

public class Authors {

    private Author author;
    private Type2 type;

    public Author getAuthor() {
        return author;
    }
    public void setAuthor(Author author) {
        this.author = author;
    }

    public Type2 getType() {
        return type;
    }
    public void setType(Type2 type) {
        this.type = type;
    }
    
}

class Author {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}

class Type2 {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
