package win.pipi.api.data;

public class MessageInfo {

    /**
     * id : 1
     * senderName : sample string 2
     * receiverName : sample string 3
     * title : sample string 4
     * content : sample string 5
     * isDraft : true
     * isRead : true
     * sendTime : 2017-10-15T15:25:42.4925418+08:00
     */

    private int id;
    private String senderName;
    private String receiverName;
    private String title;
    private String content;
    private boolean isDraft;
    private boolean isRead;
    private String sendTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
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

    public boolean isIsDraft() {
        return isDraft;
    }

    public void setIsDraft(boolean isDraft) {
        this.isDraft = isDraft;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public static class NewMessageInfo{

        public NewMessageInfo(String receiverName, String title, String content) {
            this.receiverName = receiverName;
            this.title = title;
            this.content = content;
        }

        /**
         * receiverName : sample string 1
         * title : sample string 2
         * content : sample string 3
         */


        private String receiverName;
        private String title;
        private String content;

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
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
    }
}
