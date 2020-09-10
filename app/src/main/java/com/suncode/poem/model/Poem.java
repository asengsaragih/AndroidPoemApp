package com.suncode.poem.model;

import java.io.Serializable;

public class Poem implements Serializable {

    private String title;
    private String content;
    private String url;
    private Poet poet;

    public Poem(String title, String content, String url, Poet poet) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.poet = poet;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public Poet getPoet() {
        return poet;
    }

    public static class Poet implements Serializable {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }
}
