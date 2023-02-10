package com.example.nanlinkdemo.bean;

public class Menu {

    public static final int TYPE_LOGO = 0;
    public static final int TYPE_EMPTY = 1;
    public static final int TYPE_ITEM_gray_bg = 2;
    public static final int TYPE_ITEM_nav_bg = 3;



    private int iconResId = 0;
    private int type = TYPE_EMPTY;
    private String text;
    private int stateResId = 0;

    private int endStateResId = 0;


    public Menu(){

    }

    public Menu(int iconResId, String text, int stateResId, int type){
        setIconResId(iconResId);
        setText(text);
        setType(type);
        setStateResId(stateResId);
    }

    public Menu(int iconResId, String text, int stateResId, int endStateResId, int type){
        setIconResId(iconResId);
        setText(text);
        setType(type);
        setStateResId(stateResId);
        setEndStateResId(endStateResId);
    }

    public Menu(String text, int stateResId){
        setText(text);
        setStateResId(stateResId);
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getStateResId() {
        return stateResId;
    }

    public void setStateResId(int stateResId) {
        this.stateResId = stateResId;
    }

    public int getEndStateResId() {
        return endStateResId;
    }

    public void setEndStateResId(int endStateResId) {
        this.endStateResId = endStateResId;
    }
}
