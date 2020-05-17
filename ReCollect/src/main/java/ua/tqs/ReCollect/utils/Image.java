package ua.tqs.ReCollect.utils;

public class Image {

    private String url;

    public Image(String url) {
        this.url = url;
    }

    public Image() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Image{" +
                "url='" + url + '\'' +
                '}';
    }
}
