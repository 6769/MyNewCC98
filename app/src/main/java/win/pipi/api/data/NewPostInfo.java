package win.pipi.api.data;

public class NewPostInfo {

    public NewPostInfo(String title, String content, PostContentType contentType) {
        this.title = title;
        this.content = content;
        this.contentType = contentType;
    }

    /**
     * title : sample string 1
     * content : sample string 2
     * contentType : 0
     */



    private String title;
    private String content;
    private PostContentType contentType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostContentType getContentType() {
        return contentType;
    }

    public void setContentType(PostContentType contentType) {
        this.contentType = contentType;
    }

    public enum 	PostContentType{
        Ubb,Markdown
    }
}
