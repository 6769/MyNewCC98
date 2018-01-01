package win.pipi.api;


import rx.Observable;
import rx.Subscriber;
import win.pipi.api.data.*;
import win.pipi.api.network.CC98APIInterface;
import win.pipi.api.network.CC98APIManager;

import java.util.ArrayList;

public class CC98ApiList {
    public static void main(String[] args) {
        CC98ApiList apiList = new CC98ApiList();
        apiList.request();
    }

    public void request() {

        //basic usage for api under java.

        // 步骤5:创建 网络请求接口 的实例
        CC98APIInterface request = CC98APIManager.createApiClient();

        //对 发送请求 进行封装
        Observable<ArrayList<TopicInfo>> call = request.getTopicNew(1, 20);

        call.subscribe(new Subscriber<ArrayList<TopicInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();

            }

            @Override
            public void onNext(ArrayList<TopicInfo> hotTopicInfos) {
                for (TopicInfo i : hotTopicInfos) {
                    print(i.getTitle());
                }


            }
        });

        System.out.println();


    }


    private void print(Object msg) {
        System.out.println(msg);
    }


}
