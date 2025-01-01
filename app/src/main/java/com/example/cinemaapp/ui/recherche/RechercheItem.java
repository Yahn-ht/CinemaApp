package com.example.cinemaapp.ui.recherche;

public class RechercheItem {
    private final int imageResId;;
    private final String title;
    private final String description;

    public RechercheItem( int imageResId,String title,String description){
        this.imageResId=imageResId;
        this.title=title;
        this.description=description;
    }

    public int getImageResId() {
        return imageResId;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
}

