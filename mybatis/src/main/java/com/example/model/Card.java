package com.example.model;

import java.io.Serializable;

public class Card implements Serializable {

    private int id;
    private String cardNo;

    public Card() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNo='" + cardNo + '\'' +
                '}';
    }

}
