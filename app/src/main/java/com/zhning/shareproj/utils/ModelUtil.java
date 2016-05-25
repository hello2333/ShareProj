package com.zhning.shareproj.utils;

import android.content.Context;

import com.zhning.shareproj.R;
import com.zhning.shareproj.entity.Post;
import com.zhning.shareproj.entity.PostComment;
import com.zhning.shareproj.entity.PostFollow;
import com.zhning.shareproj.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhning on 2016/5/23.
 */
public class ModelUtil {
    private static List<Post> postList = new ArrayList<>();
    private static List<PostFollow> postFollows = new ArrayList<>();
    private static List<PostComment> postCommentList = new ArrayList<>();

    private Context context;

    public ModelUtil(Context context) {
        initPostList();
        initPostFollowList();
        initPostCommentList();
        this.context = context;
    }

    public static User getUser(long id){
        User user = null;
        switch ((int)id){
            case 1:
                user = new User(1, R.drawable.user1p, "杉杉", "喜欢摄影喜欢科幻希望能认识更多的朋友");
                break;
            case 2:
                user = new User(1, R.drawable.user2p, "静夜思", "喜欢摄影喜欢科幻希望能认识更多的朋友");
                break;
            case 3:
                user = new User(1, R.drawable.user3p, "将进酒", "喜欢摄影喜欢科幻希望能认识更多的朋友");
                break;
            case 4:
                user = new User(1, R.drawable.user4p, "蝶恋花", "喜欢摄影喜欢科幻希望能认识更多的朋友");
                break;
            default:
                user = new User(1, R.drawable.user1p, "杉杉", "喜欢摄影喜欢科幻希望能认识更多的朋友");
        }

        return user;
    }

    private void initPostList(){
        String content = "茉莉餐厅是一家全国连锁电影主题餐厅，24小时经营以港式粤菜为基础的中西融合菜，" +
                "特聘香港融合菜大师主理，打造新派主题时尚餐厅。它专业的厨师团队，开发和研究新派融合菜，" +
                "定期推出特色菜品，聘请专业艺术团队研发造型，让茉莉每一款菜品成为一件艺术品，让客人感受别样的饮食文化。";
        postList.add(new Post(Constants.POST_1, Constants.USER_1, Constants.POST_FOOD, "快来吃这里的香锅！", content,
                R.drawable.post_food4));

        postList.add(new Post(Constants.POST_2, Constants.USER_2, Constants.POST_BEAUTY, "酷炫的理发店！", content,
                R.drawable.post_beauty1));

        postList.add(new Post(Constants.POST_3, Constants.USER_3, Constants.POST_FOOD, "美味的日式料理", content,
                R.drawable.post_food1));

        //content = "";
        postList.add(new Post(Constants.POST_4, Constants.USER_4, Constants.POST_BEAUTY, "适合学生党的理发店", content,
                R.drawable.post_beauty2));

        //content = "";
        postList.add(new Post(Constants.POST_5, Constants.USER_5, Constants.POST_FOOD, "满足你味蕾的烤肉店", content,
                R.drawable.post_food2));

        //content = "";
        postList.add(new Post(Constants.POST_6, Constants.USER_1, Constants.POST_BEAUTY, "手艺娴熟的理发师傅", content,
                R.drawable.post_beauty3));

        //content = "";
        postList.add(new Post(Constants.POST_7, Constants.USER_2, Constants.POST_FOOD, "难以忘怀的芝士披萨", content,
                R.drawable.post_food3));

        //content = "";
        postList.add(new Post(Constants.POST_8, Constants.USER_3, Constants.POST_FOOD, "外婆味道的糖醋里脊", content,
                R.drawable.post_food5));
    }

    private void initPostFollowList(){

        /*String content = "和小伙伴约饭，烤什么名堂离我们几个都很近，看评价也很高，就来尝试一下。\n" +
                "位置：西门里顺着城墙向南走大概三四百米的一个丁字路口，店面很显眼，门口的门帘是个古装的衣服挺个性的。\n" +
                "环境：装修很有特点古色古香的，很多都是农家菜為了配合菜品吧，装修很考究不错。\n" +
                "服务：服务员的眉心都有画的莲花吧，给我们服务的大妈看着年纪有点大了，服务态度不是太好哦。\n" +
                "菜品：#烤油饼#小小的一个，味道还不错，烤的酥酥的，料也很足。#涮牛肚豆皮#麻辣味重，蒜泥给了很多，芝麻酱略少，总体味道不错。#麻酱酿皮#味道好值得推荐#辣炒蛤蜊#壳也太多了吧，有肉的肉也很小，还有三四个没张嘴的。#烤牛肉#烤的略干，调料就显得味重。整体还不错";*/
        String content = "说得好";
        postFollows.add(new PostFollow(Constants.POSTFOLLOW_1, Constants.POST_1, Constants.USER_2,
                content, (int)Constants.POSTFOLLOW_1 + 1));
        postFollows.add(new PostFollow(Constants.POSTFOLLOW_2, Constants.POST_1, Constants.USER_3,
                content, (int)Constants.POSTFOLLOW_2 + 1));
        postFollows.add(new PostFollow(Constants.POSTFOLLOW_3, Constants.POST_1, Constants.USER_4,
                content, (int)Constants.POSTFOLLOW_3 + 1));
        postFollows.add(new PostFollow(Constants.POSTFOLLOW_4, Constants.POST_1, Constants.USER_5,
                content, (int)Constants.POSTFOLLOW_4 + 1));
        postFollows.add(new PostFollow(Constants.POSTFOLLOW_5, Constants.POST_1, Constants.USER_2,
                content, (int)Constants.POSTFOLLOW_5 + 1));
        postFollows.add(new PostFollow(Constants.POSTFOLLOW_6, Constants.POST_1, Constants.USER_3,
                content, (int)Constants.POSTFOLLOW_6 + 1));
        postFollows.add(new PostFollow(Constants.POSTFOLLOW_7, Constants.POST_1, Constants.USER_4,
                content, (int)Constants.POSTFOLLOW_7 + 1));
        postFollows.add(new PostFollow(Constants.POSTFOLLOW_8, Constants.POST_1, Constants.USER_5,
                content, (int)Constants.POSTFOLLOW_8 + 1));
        postFollows.add(new PostFollow(Constants.POSTFOLLOW_9, Constants.POST_1, Constants.USER_2,
                content, (int)Constants.POSTFOLLOW_9 + 1));
        postFollows.add(new PostFollow(Constants.POSTFOLLOW_10, Constants.POST_1, Constants.USER_3,
                content, (int)Constants.POSTFOLLOW_10 + 1));
        postFollows.add(new PostFollow(Constants.POSTFOLLOW_11, Constants.POST_1, Constants.USER_4,
                content, (int)Constants.POSTFOLLOW_11 + 1));
        postFollows.add(new PostFollow(Constants.POSTFOLLOW_12, Constants.POST_1, Constants.USER_5,
                content, (int)Constants.POSTFOLLOW_12 + 1));
        postFollows.add(new PostFollow(Constants.POSTFOLLOW_13, Constants.POST_1, Constants.USER_2,
                content, (int)Constants.POSTFOLLOW_13 + 1));
        postFollows.add(new PostFollow(Constants.POSTFOLLOW_14, Constants.POST_1, Constants.USER_3,
                content, (int)Constants.POSTFOLLOW_14 + 1));
        postFollows.add(new PostFollow(Constants.POSTFOLLOW_15, Constants.POST_1, Constants.USER_4,
                content, (int)Constants.POSTFOLLOW_15 + 1));
    }

    private void initPostCommentList(){
        String content = "同意楼上的说法";
        postCommentList.add(new PostComment(1, Constants.POSTFOLLOW_2, Constants.USER_3, 0, content));
        content = "不过不太喜欢那里的环境，感觉脏脏的";
        postCommentList.add(new PostComment(1, Constants.POSTFOLLOW_2, Constants.USER_4, 0, content));
        content = "是的呐，哎毕竟人太多没办法";
        postCommentList.add(new PostComment(1, Constants.POSTFOLLOW_2, Constants.USER_1, Constants.USER_4, content));
    }

    public static List<Post> getAllPost(){
        return postList;
    }

    public static Post getPostDetail(long postId){
        return postList.get((int)postId - 1);
    }

    public static List<PostFollow> getPostFollowByPostId(long postId){
        if (postId % 2 == 0)
            return  postFollows;
        else
            return null;
    }

    public static List<PostComment> getCommentByFollowId(long postFollowId){
        if (1 == postFollowId || 5 == postFollowId)
            return postCommentList;
        else
            return null;
    }
}
