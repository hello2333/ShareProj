package com.zhning.shareproj.utils;

import com.zhning.shareproj.R;
import com.zhning.shareproj.entity.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhning on 2016/5/23.
 */
public class ModelUtil {
    public static User getUser(long id){
        User user = null;
        switch ((int)id){
            case 1:
                user = new User(1, R.drawable.user1p, "杉杉", "喜欢摄影喜欢科幻希望能认识更多的朋友",
                        22, false, "自由音乐人", "四川大学", "陕西西安", 1, "电影,读书");
                List<String> labels = Arrays.asList("水瓶座", "电影", "单身");
                user.setLabels(labels);
                break;
            case 2:
                user = new User(1, R.drawable.user2p, "静夜思", "喜欢摄影喜欢科幻希望能认识更多的朋友",
                        26, true, "教育", "厦门大学", "湖南", 2, "电影,读书");
                labels = Arrays.asList("金牛座","读书","恋爱");
                user.setLabels(labels);
                break;
            case 3:
                user = new User(1, R.drawable.user3p, "将进酒", "喜欢摄影喜欢科幻希望能认识更多的朋友",
                        20, false, "IT通信行业", "山东大学", "浙江", 1, "电影,读书");
                labels = Arrays.asList("摩羯座","跑步","已婚");
                user.setLabels(labels);
                break;
            case 4:
                user = new User(1, R.drawable.user4p, "蝶恋花", "喜欢摄影喜欢科幻希望能认识更多的朋友",
                        22, true, "自由音乐人", "香港大学", "广州", 1, "电影,读书");
                labels = Arrays.asList("双鱼座","绘画","单身");
                user.setLabels(labels);
                break;
            default:
                user = new User(1, R.drawable.user1p, "杉杉", "喜欢摄影喜欢科幻希望能认识更多的朋友",
                        22, false, "自由音乐人", "四川大学", "陕西西安", 1, "电影,读书");
                labels = Arrays.asList("水瓶座","电影","单身");
                user.setLabels(labels);
        }

        return user;
    }
}
