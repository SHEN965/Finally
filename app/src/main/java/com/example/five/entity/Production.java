package com.example.five.entity;

public class Production {

    private String proId;
    private String proName;
    private String proPrice;
    private String Img_url;
    private int star;

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

//    public Production(String proName,String proPrice,String Img_url){
//        this.proName = proName;
//        this.proPrice = proPrice;
//        this.Img_url = Img_url;
//    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProPrice() {
        return proPrice;
    }

    public void setProPrice(String proPrice) {
        this.proPrice = proPrice;
    }

    public String getImg_url() {
        return Img_url;
    }

    public void setImg_url(String img_url) {
        Img_url = img_url;
    }
    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

}
