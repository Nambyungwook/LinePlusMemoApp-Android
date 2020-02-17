package com.nbw.lineplusmemoapp.memolist;

import java.util.ArrayList;
import java.util.List;

public class MemoListItem {
    private int id;
    private String title;
    private String content;
    private ArrayList<String> imgArray = new ArrayList<String>();

    public MemoListItem(int id, String title, String content, ArrayList<String> imgArray) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imgArray = imgArray;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<String> getImgArray() {
        return imgArray;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImgArray(ArrayList<String> imgArray) {
        this.imgArray = imgArray;
    }
}
