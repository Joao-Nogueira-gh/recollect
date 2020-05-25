package ua.tqs.ReCollect.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class Image {

    private URL url;

    public Image(String url) {
        if(url==null || url.trim().equals("")){
            this.url = null;
        }
        else{
            try{
                this.url = new URL(url);
            }
            catch (Exception e){
                this.url = null;
            }
        }
    }

    public Image() {
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Image{" +
                "url='" + url.toString() + '\'' +
                '}';
    }
}
