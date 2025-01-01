package com.example.cinemaapp.ui.snack;

public class SnackItem {
        private final int imageResId;
        private final String title;
        private final String price;
        private final String category;

        public SnackItem(int imageResId, String title, String price, String category  ) {
            this.imageResId = imageResId;
            this.title = title;
            this.price = price;
            this.category = category;
        }

        public int getImageResId() {
            return imageResId;
        }

        public String getTitle() {
            return title;
        }

        public String getPrice() {
            return price;
        }

        public String getCategory() {
            return category;
        }
    }
