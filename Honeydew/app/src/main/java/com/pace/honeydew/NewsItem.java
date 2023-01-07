package com.pace.honeydew;

public class NewsItem {

    private String title, urlToImage,description;
    //getters and setter. Wrapper class
    public NewsItem(String title, String urlToImage, String description) {
        this.title = title;
        this.urlToImage = urlToImage;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getDescription() {
        return description;
    }
}
