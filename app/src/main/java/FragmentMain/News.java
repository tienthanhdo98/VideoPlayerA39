package FragmentMain;

public class News {
    String title,urlAvt,content;

    public News(String title, String urlAvt, String content) {
        this.title = title;
        this.urlAvt = urlAvt;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlAvt() {
        return urlAvt;
    }

    public void setUrlAvt(String urlAvt) {
        this.urlAvt = urlAvt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
