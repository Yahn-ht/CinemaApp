package com.example.cinemaapp.ui.favorie;

public class FavorieItem {
        private final int imageResId;
        private final int imagefav;
        private final String title;
        private final String description;
        private final String cinemalocate;

       public FavorieItem( int imageResId,int imagefav,String title,String description,String cinemalocate){
           this.imageResId=imageResId;
           this.imagefav= imagefav;
           this.title=title;
           this.description=description;
           this.cinemalocate=cinemalocate;
       }

        public int getImageResId() {
            return imageResId;
        }
        public String getTitle() {
            return title;
        }

    public int getImagefav() {
        return imagefav;
    }

    public String getCinemalocate() {
        return cinemalocate;
    }

    public String getDescription() {
        return description;
    }
}

