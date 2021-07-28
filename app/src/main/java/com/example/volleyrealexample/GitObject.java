package com.example.volleyrealexample;

import java.util.Comparator;

public class GitObject {
    public GitObject(String id, String full_name, String avatar_url, String size, String license) {
        this.id = id;
        this.full_name = full_name;
        this.avatar_url = avatar_url;
        this.size = size;
        this.license = license;
    }

    String id;
    String full_name;
    String avatar_url;

    public GitObject(){

    }
    public static Comparator<GitObject> comparatorAz= new Comparator<GitObject>() {
        @Override
        public int compare(GitObject o1, GitObject o2) {
            return o1.getFull_name().compareTo(o2.getFull_name());
        }
    };
    public static Comparator<GitObject> comparatorZa= new Comparator<GitObject>() {
        @Override
        public int compare(GitObject o1, GitObject o2) {
            return o2.getFull_name().compareTo(o1.getFull_name());
        }
    };
    public static Comparator<GitObject> comparatorSize= new Comparator<GitObject>() {
        @Override
        public int compare(GitObject o1, GitObject o2) {
            return Integer.parseInt(o2.getSize()) - Integer.parseInt(o1.getSize());
        }
    };


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    String size;
    String license;
}
