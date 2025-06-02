package com.example.myapplication;

public class CardModel {
    private int resId;
    private boolean isMatched;
    private boolean isOpen;

    public CardModel(int resId, boolean isMatched, boolean isOpen) {
        this.resId = resId;
        this.isMatched = isMatched;
        this.isOpen = isOpen;
    }

    public int getResId() {
        return resId;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
