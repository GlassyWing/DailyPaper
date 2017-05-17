package com.example.manlier.dailypaper;

import com.example.manlier.dailypaper.beans.NewsBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by manlier on 2017/5/14.
 */

public class GsonTest {

    private String json ="{\"T1348647909107\":"+
            "[{\"url_3w\": \"http://news.163.com/17/0513/16/CKB4J41C000189FH.html\"," +
                    "\"postid\": \"CKB4J41C000189FH\"," +
                    "\"votecount\": 2093," +
                    "\"replyCount\": 2662," +
                    "\"ltitle\": \"领航“一带一路”\"," +
                    "\"digest\": \"2013年，习近平总书记在哈萨克斯坦纳扎尔巴耶夫大学和印度尼西亚国会发表演讲时，分别提出共同建设“丝绸之路经济带”和“21世纪海上丝绸之路”合作倡议。习近平高度\"," +
                    "\"url\": \"http://3g.163.com/news/17/0513/16/CKB4J41C000189FH.html\"," +
                    "\"title\": \"领航“一带一路”\"," +
                    "\"source\": \"央视新闻客户端\"," +
                    "\"priority\": 310," +
                    "\"lmodify\": \"2017-05-14 00:21:44\"," +
                    "\"imgsrc\": \"http://cms-bucket.nosdn.127.net/d11e35afefbd468994d6f4060cb7077720170513170259.png\"," +
                    "\"subtitle\": \"\"," +
                    "\"boardid\": \"news2_bbs\"," +
                    "\"ptime\": \"2017-05-13 16:53:36\"" +
                    "}]}";
    private String newsBeanJson = "{" +
            "\"postid\": \"PHOT24Q8N000100A\"," +
            "\"hasCover\": false," +
            "\"hasHead\": 1," +
            "\"replyCount\": 26956," +
            "\"hasImg\": 1," +
            "\"digest\": \"\"," +
            "\"hasIcon\": false," +
            "\"docid\": \"9IG74V5H00963VRO_CKBIQEGBpingguanupdateDoc\"," +
            "\"title\": \"洪秀柱赴民进党总部抗议 呛蔡英文下台\"," +
            "\"order\": 1," +
            "\"priority\": 354," +
            "\"lmodify\": \"2017-05-14 00:21:41\"," +
            "\"boardid\": \"photoview_bbs\"," +
            "\"ads\": [" +
            "{" +
            "\"title\": \"\\\"一带一路\\\"合作共赢 北京\\\"美颜\\\"迎盛会\"," +
            "\"skipID\": \"00AN0001|2255018\"," +
            "\"tag\": \"photoset\"," +
            "\"imgsrc\": \"http://cms-bucket.nosdn.127.net/a847388389ab4830952a076936cf592420170512204041.jpeg\"," +
            "\"subtitle\": \"\"," +
            "\"skipType\": \"photoset\"," +
            "\"url\": \"00AN0001|2255018\"" +
            "}," +
            "{" +
            "\"title\": \"特朗普携夫人出席母亲节活动 当众亲吻\"," +
            "\"skipID\": \"00AO0001|2255055\"," +
            "\"tag\": \"photoset\"," +
            "\"imgsrc\": \"http://cms-bucket.nosdn.127.net/bbb02ff52c1846e7ae8204fb55d8ea4920170513091928.jpeg\"," +
            "\"subtitle\": \"\"," +
            "\"skipType\": \"photoset\"," +
            "\"url\": \"00AO0001|2255055\"" +
            "}," +
            "{" +
            "\"title\": \"【一周图片精选】马克龙赢法总统大选\"," +
            "\"skipID\": \"19BR0001|2254952\"," +
            "\"tag\": \"photoset\"," +
            "\"imgsrc\": \"http://cms-bucket.nosdn.127.net/d698b97dc4f147678259a5ab770232af20170513072022.jpeg\"," +
            "\"subtitle\": \"\"," +
            "\"skipType\": \"photoset\"," +
            "\"url\": \"19BR0001|2254952\"" +
            "}," +
            "{" +
            "\"title\": \"文在寅出席活动 成民众最佳自拍背景\"," +
            "\"skipID\": \"00AO0001|2255033\"," +
            "\"tag\": \"photoset\"," +
            "\"imgsrc\": \"http://cms-bucket.nosdn.127.net/b4e9d2edfb5449a9a4e4a406b3ac0e8420170513032801.jpeg\"," +
            "\"subtitle\": \"\"," +
            "\"skipType\": \"photoset\"," +
            "\"url\": \"00AO0001|2255033\"" +
            "}," +
            "{" +
            "\"title\": \"重庆万州发生山体滑坡 巨石砸中民房\"," +
            "\"skipID\": \"00AP0001|2255002\"," +
            "\"tag\": \"photoset\"," +
            "\"imgsrc\": \"http://cms-bucket.nosdn.127.net/264c5b1d72cb4e4e956417fa4a126d8d20170512204437.jpeg\"," +
            "\"subtitle\": \"\"," +
            "\"skipType\": \"photoset\"," +
            "\"url\": \"00AP0001|2255002\"" +
            "}" +
            "]," +
            "\"photosetID\": \"00AN0001|2255127\"," +
            "\"imgsum\": 4," +
            "\"topic_background\": \"http://img2.cache.netease.com/m/newsapp/reading/cover1/C1348646712614.jpg\"," +
            "\"template\": \"normal1\"," +
            "\"votecount\": 25816," +
            "\"skipID\": \"00AN0001|2255127\"," +
            "\"alias\": \"Top News\"," +
            "\"skipType\": \"photoset\"," +
            "\"cid\": \"C1348646712614\"," +
            "\"hasAD\": 1," +
            "\"imgextra\": [" +
            "{" +
            "\"imgsrc\": \"http://cms-bucket.nosdn.127.net/ea530b04b5e04be9bfe09aba2ac9c6b020170513210511.png\"" +
            "}," +
            "{" +
            "\"imgsrc\": \"http://cms-bucket.nosdn.127.net/ec9d5f29a022446a9d9b0fd6b4aa00b020170513210517.png\"" +
            "}" +
            "]," +
            "\"source\": \"中国网\"," +
            "\"ename\": \"androidnews\"," +
            "\"tname\": \"头条\"," +
            "\"imgsrc\": \"http://cms-bucket.nosdn.127.net/eec9900e13a340fab49c5b158217a86e20170513210511.png\"," +
            "\"ptime\": \"2017-05-13 21:02:16\"" +
            "}";

    @Test
    public void parseToMap() {
        String json = "[{\"subKey1\":\"val1\"}, {\"subKey1\":\"val1\"}]";
        String json2 = "{\"KEY\":[{\"subKey1\":\"val1\"}, {\"subKey1\":\"val1\"}]}";
        Gson gson = new Gson();
        List<Map<String, String>> result = gson.fromJson(json, new TypeToken<List<Map<String, String>>>(){}.getType());
        Map<String, List<Map<String, String>>> result2 = gson.fromJson(json2, new TypeToken<Map<String, List<Map<String, String>>>>(){}.getType());
        System.out.println(result);
        System.out.println(result2);
    }

    @Test
    public void parseToNewsBean() {
        Gson gson = new Gson();
        NewsBean newsBean = gson.fromJson(newsBeanJson, NewsBean.class);
        System.out.println(newsBean);
    }

    @Test
    public void parseToNewsBeans() throws Exception {
        File file = new File("./src/main/java/com/example/manlier/dailypaper/news.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder builder = new StringBuilder();
        String temp;
        while ((temp = reader.readLine()) != null) {
            builder.append(temp);
        }
        Gson gson = new Gson();
        Map<String, List<NewsBean>> result = gson.fromJson(builder.toString(), new TypeToken<Map<String, List<NewsBean>>>(){}.getType());
        List<NewsBean> beanList = result.get("T1348647909107");
        List<NewsBean> collect = new ArrayList<>();
        beanList.stream().filter(bean -> !bean.getDigest().equals("")).map(bean -> {
            bean.setDocid(bean.getDocid().replace("_special", ""));
            return bean;
        }).forEach(collect::add);
        System.out.println(collect.size());
        collect.stream().map(NewsBean::getDocid).forEach(System.out::println);
    }


}
