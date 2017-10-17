package win.pipi.api.data;

public class TopicInfo implements TopicInfoInterface {

    /**
     * title : 欢迎来到 CC98
     * hitCount : 9898
     * id : 9898
     * boardId : 98
     * bestState : 2
     * topState : 4
     * replyCount : 98
     * isVote : false
     * isAnonymous : false
     * authorName : CC98
     * authorId : 98
     * isLocked : false
     * createTime : 1998-09-08T00:00:00
     * lastPostInfo : {"userName":"CC98","contentSummary":"欢迎来到 CC98。My CC98, My Home。","time":"1998-09-08T00:00:00"}
     */

    private String title;
    private int hitCount;
    private int id;
    private int boardId;
    private int bestState;
    private int topState;
    private int replyCount;
    private boolean isVote;
    private boolean isAnonymous;
    private String authorName;
    private int authorId;
    private boolean isLocked;
    private String createTime;
    private LastPostInfoBean lastPostInfo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public int getBestState() {
        return bestState;
    }

    public void setBestState(int bestState) {
        this.bestState = bestState;
    }

    public int getTopState() {
        return topState;
    }

    public void setTopState(int topState) {
        this.topState = topState;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public boolean isIsVote() {
        return isVote;
    }

    public void setIsVote(boolean isVote) {
        this.isVote = isVote;
    }

    public boolean isIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public boolean isIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public LastPostInfoBean getLastPostInfo() {
        return lastPostInfo;
    }

    public void setLastPostInfo(LastPostInfoBean lastPostInfo) {
        this.lastPostInfo = lastPostInfo;
    }

    public static class LastPostInfoBean {
        /**
         * userName : CC98
         * contentSummary : 欢迎来到 CC98。My CC98, My Home。
         * time : 1998-09-08T00:00:00
         */

        private String userName;
        private String contentSummary;
        private String time;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getContentSummary() {
            return contentSummary;
        }

        public void setContentSummary(String contentSummary) {
            this.contentSummary = contentSummary;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
