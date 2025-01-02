package com.example.coffee.Models;

import java.util.List;

import kotlin.jvm.internal.SerializedIr;

public class GitHubUserSearchResponse {
    private List<User> items;
    public static class User {
        private String login;
        private String avatar_url;
        private String html_url;

        public String getLogin() {
            return login;
        }

        public String getAvatarUrl() {
            return avatar_url;
        }

        public String getHtmlUrl() {
            return html_url;
        }
    }
}
