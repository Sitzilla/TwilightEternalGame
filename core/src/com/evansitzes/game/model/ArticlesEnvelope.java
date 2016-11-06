package com.evansitzes.game.model;

import com.evansitzes.game.exceptions.ItemDoesntExistException;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by evan on 11/3/16.
 */
public class ArticlesEnvelope {
    @JsonProperty
    private ArrayList<Article> articles;

    public ArticlesEnvelope() {
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(final ArrayList<Article> articles) {
        this.articles = articles;
    }

    public Article getArticle(final String name) {

        for (final Article article : articles) {
            if (article.getName().equals(name)) {
                return article;
            }
        }

        throw new ItemDoesntExistException("Item " + name + " does not exist in resources file.");
    }

    @Override
    public String toString() {
        return "ArticlesEnvelope{" +
                "articles=" + articles +
                '}';
    }
}
