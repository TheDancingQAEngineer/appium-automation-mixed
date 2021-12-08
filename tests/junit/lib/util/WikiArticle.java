package lib.util;

public class WikiArticle {

    private String title, description;

    public WikiArticle(String title, String description)
    {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }
}
