package com.nbw.lineplusmemoapp.list;

public class ImgListItem {

    private int id;
    private String img;
    private int order;

    public ImgListItem(int id, String img, int order) {
        this.id = id;
        this.img = img;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public String getImg() {
        return img;
    }

    public int getOrder() {
        return order;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
