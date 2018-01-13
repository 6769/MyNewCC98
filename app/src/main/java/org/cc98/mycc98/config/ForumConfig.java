package org.cc98.mycc98.config;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import win.pipi.api.data.GroupBoardInfo;

/**
 * Created by pipi6 on 2018/1/12.
 */

public class ForumConfig {
    private static Map<String,Integer> boardToId=new HashMap<>();
    private static Map<Integer,String> idToBoard=new HashMap<>();

    public static void setBoardMap(List<GroupBoardInfo> boardmap){
        for (GroupBoardInfo agroup :boardmap) {
            for(GroupBoardInfo.BoardsBean aboard:agroup.getBoards()){
                boardToId.put(aboard.getName(),aboard.getId());
                idToBoard.put(aboard.getId(),aboard.getName());
            }
        }
    }
    public static int getBoardIdViaName(String name){
        return boardToId.get(name);
    }
    public static String getBoardNameViaId(int id){
        return idToBoard.get(id);
    }
}
