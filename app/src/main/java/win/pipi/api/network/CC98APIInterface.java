package win.pipi.api.network;

import retrofit2.http.*;
import rx.Observable;
import win.pipi.api.data.*;

import java.util.ArrayList;
import java.util.List;

public interface CC98APIInterface {

    public final String BASE_URL = "https://api-v2.cc98.org/";

    @GET("Topic/Hot")
    Observable<ArrayList<HotTopicInfo>> getTopicHot();
    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // getTopicHot()是接受网络请求数据的方法



    //page split limit to 20 per list;
    //  eg:1-20,21-40;41-60;61-80;81-100;
    //@Headers("range:bytes=21-40")
    @GET("Topic/New")
    Observable<ArrayList<TopicInfo>> getTopicNewRaw(@Header("range") String paging);

    @GET("topic/new")
    Observable<ArrayList<TopicInfo>> getTopicNew(@Query("from") Integer from,
                                                 @Query("size") Integer size);

    @GET("board/{boardId}/topic")
    Observable<ArrayList<TopicInfo>> getTopicBoard(@Path("boardId") Integer boardId,
                                                   @Query("from") Integer from,
                                                   @Query("size") Integer size);


    @GET("Post/Topic/{topicId}")
    Observable<ArrayList<PostContent>> getPostTopic(@Path("topicId")Integer id,
                                              @Query("from") Integer from,
                                              @Query("to")Integer to
                                              );


    //create new topic;
    // need OAuth
    @POST ("Topic/Board/{boardId}")
    Observable<String>            postTopicBoard(@Path("boardId") Integer boardId,
                                           @Body NewPostInfo newPostInfo);


    //append post to an existed topic;
    // need Oauth;
    @POST ("Post/Topic/{topicId}")
    Observable<String>            postPostTopic(@Path("topicId")Integer id,
                                        @Body NewPostInfo newPostInfo
                                              );

    @GET("User/{id}")
    Observable<UserInfo>              getUserInfoViaId(@Path("id")Integer id);

    @GET("User/Name/{name}")
    Observable<UserInfo>              getUserInfoViaName(@Path("name")String name);

    @GET("Me")
    Observable<UserInfo>              getMe();


    @GET("Board/All")
    Observable<ArrayList<GroupBoardInfo>> getBoardAll();

    @GET("Board/Root")
    Observable<ArrayList<RootBoardInfo>> getBoardRoot();

    @GET("Board/{boardId}/Sub")
    Observable<ArrayList<BoardInfo>> getBoardSub(@Path("boardId") Integer id);

    @GET("Board/{id}")
    Observable<ArrayList<BoardInfo>> getBoardId(@Path("id")Integer id);


    @GET ("Message?userName={userName}&filter={filter}")
    Observable<ArrayList<MessageInfo>> getMessage(@Path("userName") String user,
                                            @Path("filter") String filter);

    @GET ("Message/{id}")
    Observable<MessageInfo>           getMessage(@Path("id")Integer id);

    @POST ("Message")
    Observable<String>                postMessage(@Body MessageInfo.NewMessageInfo mess);


    @DELETE ("Message/{id}")
    Observable<String>                deleteMessage(@Path("id")Integer id);


}