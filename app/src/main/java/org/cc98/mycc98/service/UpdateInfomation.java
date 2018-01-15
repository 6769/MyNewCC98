package org.cc98.mycc98.service;

/**
 * Created by pipi6 on 2018/1/15.
 */
public class UpdateInfomation {


    /**
     * latestVersion : 1.1
     * latestVersionCode : 3
     * url : http://192.168.123.119:8080/
     * releaseNotes : {"title":"new version1","msgcontent":"content","extra":"lolo"}
     */

    private String latestVersion;
    private int latestVersionCode;
    private String url;
    private ReleaseNotesBean releaseNotes;

    public String getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(String latestVersion) {
        this.latestVersion = latestVersion;
    }

    public int getLatestVersionCode() {
        return latestVersionCode;
    }

    public void setLatestVersionCode(int latestVersionCode) {
        this.latestVersionCode = latestVersionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ReleaseNotesBean getReleaseNotes() {
        return releaseNotes;
    }

    public void setReleaseNotes(ReleaseNotesBean releaseNotes) {
        this.releaseNotes = releaseNotes;
    }

    public static class ReleaseNotesBean {
        /**
         * title : new version1
         * msgcontent : content
         * extra : lolo
         */

        private String title;
        private String msgcontent;
        private String extra;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMsgcontent() {
            return msgcontent;
        }

        public void setMsgcontent(String msgcontent) {
            this.msgcontent = msgcontent;
        }

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }
    }
}
