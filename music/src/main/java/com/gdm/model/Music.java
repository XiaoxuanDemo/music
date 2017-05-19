package com.gdm.model;

public class Music {
    private Integer musicid;

    private String name;

    private String path;

    private String author;

    private String album;

    private String size;

    private String imgpath;

    private String mvpath;

    private String lrcfile;

    public Integer getMusicid() {
        return musicid;
    }

    public void setMusicid(Integer musicid) {
        this.musicid = musicid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album == null ? null : album.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath == null ? null : imgpath.trim();
    }

    public String getMvpath() {
        return mvpath;
    }

    public void setMvpath(String mvpath) {
        this.mvpath = mvpath == null ? null : mvpath.trim();
    }

    public String getLrcfile() {
        return lrcfile;
    }

    public void setLrcfile(String lrcfile) {
        this.lrcfile = lrcfile == null ? null : lrcfile.trim();
    }
}