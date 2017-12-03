package in.ac.kuexam.kudatesheets;

/**
 * Created by dipanker on 24/07/17.
 */

public class data {
    public data(String url, String name){
        this.setName(name);
        this.setUrl(url);
    }
    private String url;
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
