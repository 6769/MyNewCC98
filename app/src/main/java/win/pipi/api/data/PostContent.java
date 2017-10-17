package win.pipi.api.data;

public class PostContent {

    /**
     * id : 1
     * title : sample string 2
     * content : sample string 3
     * time : 2017-10-15T03:05:57.3313302+08:00
     * isDeleted : true
     * topicId : 6
     * needAudit : true
     * floor : 8
     * userName : sample string 9
     * userId : 1
     * isAnonymous : true
     * contentType : 0
     * lastUpdateAuthor : sample string 11
     * lastUpdateTime : 2017-10-15T03:05:57.3313302+08:00
     */

    private int id;
    private String title;
    private String content;
    private String time;
    private boolean isDeleted;
    private int topicId;
    private boolean needAudit;
    private int floor;
    private String userName;
    private int userId;
    private boolean isAnonymous;
    private int contentType;
    private String lastUpdateAuthor;
    private String lastUpdateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public boolean isNeedAudit() {
        return needAudit;
    }

    public void setNeedAudit(boolean needAudit) {
        this.needAudit = needAudit;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getLastUpdateAuthor() {
        return lastUpdateAuthor;
    }

    public void setLastUpdateAuthor(String lastUpdateAuthor) {
        this.lastUpdateAuthor = lastUpdateAuthor;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
