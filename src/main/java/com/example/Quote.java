package com.example;

/**
 * Created by markheckler on 6/16/16.
 */
class Quote {
    private Long id;
    private String text;
    private String source;

    public Quote() {
    }

    public Quote(Long id, String text, String source) {
        this.id = id;
        this.text = text;
        this.source = source;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
