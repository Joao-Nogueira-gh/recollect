package ua.tqs.ReCollect.utils;

import java.util.ArrayList;
import java.util.List;

public class PictureListDto {

    public PictureListDto() {
        this.images = new ArrayList<>();
    }

    private List<Image> images;

    public void addImage(Image image) {
        this.images.add(image);
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
