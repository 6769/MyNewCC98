package win.pipi.api.data;

import java.util.List;

public class BoardInfo {

    /**
     * id : 98
     * name : 测试板块
     * description : 这个版面仅供内部测试使用。
     * childBoardCount : 20
     * parentId : 0
     * rootId : 0
     * totalPostCount : 989898
     * totalTopicCount : 98
     * todayPostCount : 9898
     * isHidden : false
     * isCategory : false
     * isEncrypted : false
     * isAnomynous : false
     * isLocked : false
     * masters : ["管理员1","管理员2"]
     * lastPostInfo : {"boardId":100,"topicId":1009898,"postId":98989898,"dateTime":"1998-09-08T00:00:00","userName":"CC98","userId":9898,"topicTitle":"最后一个主题的标题"}
     * postTimeLimit : 00:00:00
     * needAudit : false
     */

    private int id;
    private String name;
    private String description;
    private int childBoardCount;
    private int parentId;
    private int rootId;
    private int totalPostCount;
    private int totalTopicCount;
    private int todayPostCount;
    private boolean isHidden;
    private boolean isCategory;
    private boolean isEncrypted;
    private boolean isAnomynous;
    private boolean isLocked;
    private LastPostInfoBean lastPostInfo;
    private String postTimeLimit;
    private boolean needAudit;
    private List<String> masters;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getChildBoardCount() {
        return childBoardCount;
    }

    public void setChildBoardCount(int childBoardCount) {
        this.childBoardCount = childBoardCount;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getRootId() {
        return rootId;
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
    }

    public int getTotalPostCount() {
        return totalPostCount;
    }

    public void setTotalPostCount(int totalPostCount) {
        this.totalPostCount = totalPostCount;
    }

    public int getTotalTopicCount() {
        return totalTopicCount;
    }

    public void setTotalTopicCount(int totalTopicCount) {
        this.totalTopicCount = totalTopicCount;
    }

    public int getTodayPostCount() {
        return todayPostCount;
    }

    public void setTodayPostCount(int todayPostCount) {
        this.todayPostCount = todayPostCount;
    }

    public boolean isIsHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public boolean isIsCategory() {
        return isCategory;
    }

    public void setIsCategory(boolean isCategory) {
        this.isCategory = isCategory;
    }

    public boolean isIsEncrypted() {
        return isEncrypted;
    }

    public void setIsEncrypted(boolean isEncrypted) {
        this.isEncrypted = isEncrypted;
    }

    public boolean isIsAnomynous() {
        return isAnomynous;
    }

    public void setIsAnomynous(boolean isAnomynous) {
        this.isAnomynous = isAnomynous;
    }

    public boolean isIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public LastPostInfoBean getLastPostInfo() {
        return lastPostInfo;
    }

    public void setLastPostInfo(LastPostInfoBean lastPostInfo) {
        this.lastPostInfo = lastPostInfo;
    }

    public String getPostTimeLimit() {
        return postTimeLimit;
    }

    public void setPostTimeLimit(String postTimeLimit) {
        this.postTimeLimit = postTimeLimit;
    }

    public boolean isNeedAudit() {
        return needAudit;
    }

    public void setNeedAudit(boolean needAudit) {
        this.needAudit = needAudit;
    }

    public List<String> getMasters() {
        return masters;
    }

    public void setMasters(List<String> masters) {
        this.masters = masters;
    }

    public static class LastPostInfoBean {
        /**
         * boardId : 100
         * topicId : 1009898
         * postId : 98989898
         * dateTime : 1998-09-08T00:00:00
         * userName : CC98
         * userId : 9898
         * topicTitle : 最后一个主题的标题
         */

        private int boardId;
        private int topicId;
        private int postId;
        private String dateTime;
        private String userName;
        private int userId;
        private String topicTitle;

        public int getBoardId() {
            return boardId;
        }

        public void setBoardId(int boardId) {
            this.boardId = boardId;
        }

        public int getTopicId() {
            return topicId;
        }

        public void setTopicId(int topicId) {
            this.topicId = topicId;
        }

        public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
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

        public String getTopicTitle() {
            return topicTitle;
        }

        public void setTopicTitle(String topicTitle) {
            this.topicTitle = topicTitle;
        }
    }
}
