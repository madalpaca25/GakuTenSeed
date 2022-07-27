package com.example.proj.model;

import java.util.ArrayList;

public class BooksResponse {

    private int numFound;
    private int start;
    private boolean numFoundExact;
    private ArrayList<Docs> docs;
    

    public int getNumFound() {
        return numFound;
    }
    public void setNumFound(int numFound) {
        this.numFound = numFound;
    }
    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public boolean isNumFoundExact() {
        return numFoundExact;
    }
    public void setNumFoundExact(boolean numFoundExact) {
        this.numFoundExact = numFoundExact;
    }

    public ArrayList<Docs> getDocs() {
        return docs;
    }
    public void setDocs(ArrayList<Docs> docs) {
        this.docs = docs;
    }

    


    // private ArrayList<Authors> authors;
    // public ArrayList<Authors> getAuthors() {
    //     return authors;
    // }
    // public void setAuthors(ArrayList<Authors> authors) {
    //     this.authors = authors;
    // }


    // private String title;
    // public String getTitle() {
    //     return title;
    // }
    // public void setTitle(String title) {
    //     this.title = title;
    // }
    
}
