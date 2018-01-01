package win.pipi.api.data;

import java.util.List;

public class GroupBoardInfo {

    /**
     * id : 763
     * name : 置顶
     * order : 1
     * masters : []
     * boards : [{"id":758,"name":"似水流年","boardMasters":["磊磊1010","CC98Durian"],"topicCount":1084,"postCount":161766,"todayCount":20},{"id":182,"name":"心灵之约","boardMasters":["6781287","朱小朱。","鸭子菌"],"topicCount":90578,"postCount":1948666,"todayCount":10}]
     */

    private int id;
    private String name;
    private int order;
    private List<String> masters;
    private List<BoardsBean> boards;

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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<?> getMasters() {
        return masters;
    }

    public void setMasters(List<String> masters) {
        this.masters = masters;
    }

    public List<BoardsBean> getBoards() {
        return boards;
    }

    public void setBoards(List<BoardsBean> boards) {
        this.boards = boards;
    }

    public static class BoardsBean {
        /**
         * id : 758
         * name : 似水流年
         * boardMasters : ["磊磊1010","CC98Durian"]
         * topicCount : 1084
         * postCount : 161766
         * todayCount : 20
         */

        private int id;
        private String name;
        private int topicCount;
        private int postCount;
        private int todayCount;
        private List<String> boardMasters;

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

        public int getTopicCount() {
            return topicCount;
        }

        public void setTopicCount(int topicCount) {
            this.topicCount = topicCount;
        }

        public int getPostCount() {
            return postCount;
        }

        public void setPostCount(int postCount) {
            this.postCount = postCount;
        }

        public int getTodayCount() {
            return todayCount;
        }

        public void setTodayCount(int todayCount) {
            this.todayCount = todayCount;
        }

        public List<String> getBoardMasters() {
            return boardMasters;
        }

        public void setBoardMasters(List<String> boardMasters) {
            this.boardMasters = boardMasters;
        }
    }
}
