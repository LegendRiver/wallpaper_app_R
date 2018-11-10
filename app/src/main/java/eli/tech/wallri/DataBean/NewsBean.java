package eli.tech.wallri.DataBean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 小雷 on 2018/1/16.
 * SingleImgBean
 */

public class NewsBean implements Serializable {


    /**
     * ret : 0
     * msg : ok
     * stime : 1516100314
     * pagination : {"hasMore":1,"count":30,"offset":30,"total":7719}
     * data : [{"alias_title":"nature-309607","title":"nature","author":"ishmad","download_count":410,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-29/326709_3ce2fb51.jpg","type":2,"tags":[{"tag_id":1881,"tag_name":"photography"},{"tag_id":1703,"tag_name":"trees"},{"tag_id":48192,"tag_name":"rock stairs"},{"tag_id":364,"tag_name":"road"},{"tag_id":375,"tag_name":"sea"},{"tag_id":363,"tag_name":"nature"},{"tag_id":34548,"tag_name":"Sri Lanka"}],"cpack":"index=0","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-29/326709_3ce2fb51.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-29/326709_3ce2fb51.jpg","favorite_count":0,"multi_screen":1},{"alias_title":"sky-104544","title":"sky","author":"Pc7","download_count":745,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-29/374497_a1e25ab4.jpg","type":2,"tags":[{"tag_id":1881,"tag_name":"photography"},{"tag_id":641,"tag_name":"forest"},{"tag_id":188,"tag_name":"sky"},{"tag_id":353,"tag_name":"landscape"},{"tag_id":363,"tag_name":"nature"},{"tag_id":1703,"tag_name":"trees"},{"tag_id":1013,"tag_name":"house"},{"tag_id":1566,"tag_name":"greece"},{"tag_id":33419,"tag_name":"Meteora"}],"cpack":"index=1","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-29/374497_a1e25ab4.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-29/374497_a1e25ab4.jpg","favorite_count":3,"multi_screen":1},{"alias_title":"romantic-sakura-live-wallpaper-1936171","title":"Romantic Sakura Live Wallpaper","author":"","download_count":0,"thumbnail_url":"http://img2.launcher.ksmobile.com/wallpapers/thumbnail/cml/wphd_de099e52ce.jpg","type":1,"tags":[],"cpack":"index=2","wp_apk_url":"https://play.google.com/store/apps/details?id=live.wallpaper.romantic.sakura","package":"live.wallpaper.romantic.sakura","previews":["http://img2.launcher.ksmobile.com/wallpapers/live/ima_name_0_1728a79fcc.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/ima_name_1_2602922e5f.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/ima_name_2_1fd6a2f305.jpg"],"favorite_count":0,"multi_screen":1},{"alias_title":"landscape-118877","title":"landscape","author":"microcosmos","download_count":519,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-29/297534_25f80c86.jpg","type":2,"tags":[{"tag_id":1636,"tag_name":"scotland"},{"tag_id":375,"tag_name":"sea"},{"tag_id":353,"tag_name":"landscape"}],"cpack":"index=3","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-29/297534_25f80c86.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-29/297534_25f80c86.jpg","favorite_count":0,"multi_screen":1},{"alias_title":"graffiti-wall-live-wallpaper-1936306","title":"Graffiti Wall Live Wallpaper","author":"","download_count":0,"thumbnail_url":"http://img2.launcher.ksmobile.com/wallpapers/thumbnail/cml/wphd_e34344f423.jpg","type":1,"tags":[],"cpack":"index=4","wp_apk_url":"https://play.google.com/store/apps/details?id=live.wallpaper.graffiti.street.art","package":"live.wallpaper.graffiti.street.art","previews":["http://img2.launcher.ksmobile.com/wallpapers/live/wp_dafea2c40582a6370858b80be5a124c6.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/wp_9f674b1b7d9b91f743596f3a15e6217a.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/wp_12ca7573f6d5a3149d783bdbb3edec99.jpg"],"favorite_count":0,"multi_screen":1},{"alias_title":"brown-281374","title":"brown","author":"Dreadnaught","download_count":571,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-29/374228_97845422.jpg","type":2,"tags":[{"tag_id":2665,"tag_name":"ancient"},{"tag_id":1974,"tag_name":"brown"},{"tag_id":13142,"tag_name":"chinese dragon"}],"cpack":"index=5","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-29/374228_97845422.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-29/374228_97845422.jpg","favorite_count":0,"multi_screen":1},{"alias_title":"abstract-329438","title":"abstract","author":"mattilius258","download_count":608,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-29/164749_7ae89de7.jpg","type":2,"tags":[{"tag_id":6,"tag_name":"abstract"}],"cpack":"index=6","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-29/164749_7ae89de7.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-29/164749_7ae89de7.jpg","favorite_count":9,"multi_screen":1},{"alias_title":"aquarium-1970452","title":"Aquarium","author":"Priti Tari","download_count":0,"thumbnail_url":"http://img2.launcher.ksmobile.com/wallpapers/thumbnail/cml/wp_c792fbfc23.jpg","type":1,"tags":[],"cpack":"index=7","wp_apk_url":"https://play.google.com/store/apps/details?id=live.wallpaper.animation.aquarium.fish.tanks","package":"live.wallpaper.animation.aquarium.fish.tanks","previews":["http://img2.launcher.ksmobile.com/wallpapers/live/ima_name_0_b8d073ca88.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/ima_name_1_eab773d0ab.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/ima_name_2_f62ffc0ed4.jpg"],"favorite_count":0,"multi_screen":1},{"alias_title":"forest-307651","title":"forest","author":"microcosmos","download_count":412,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-08/334067_45e0042b.jpg","type":2,"tags":[{"tag_id":641,"tag_name":"forest"},{"tag_id":1703,"tag_name":"trees"}],"cpack":"index=8","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-08/334067_45e0042b.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-08/334067_45e0042b.jpg","favorite_count":25,"multi_screen":1},{"alias_title":"water-288551","title":"water","author":"vfgx","download_count":482,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-29/339443_05db3fc3.jpg","type":2,"tags":[{"tag_id":1293,"tag_name":"rock"},{"tag_id":1604,"tag_name":"switzerland"},{"tag_id":1146,"tag_name":"lake"},{"tag_id":1703,"tag_name":"trees"},{"tag_id":128,"tag_name":"water"},{"tag_id":353,"tag_name":"landscape"},{"tag_id":1881,"tag_name":"photography"}],"cpack":"index=9","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-29/339443_05db3fc3.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-29/339443_05db3fc3.jpg","favorite_count":2,"multi_screen":1},{"alias_title":"turkey-373447","title":"turkey","author":"ssonel1","download_count":191,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-29/393969_6b842064.jpg","type":2,"tags":[],"cpack":"index=10","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-29/393969_6b842064.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-29/393969_6b842064.jpg","favorite_count":2,"multi_screen":1},{"alias_title":"fantasy-art-186645","title":"fantasy art","author":"montana29","download_count":1089,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-29/40684_13f2185b.jpg","type":2,"tags":[{"tag_id":78,"tag_name":"fantasy art"}],"cpack":"index=11","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-29/40684_13f2185b.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-29/40684_13f2185b.jpg","favorite_count":2,"multi_screen":1},{"alias_title":"clouds-720566","title":"clouds","author":"omiit","download_count":448,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-08/381962_770088b0.jpg","type":2,"tags":[{"tag_id":10112,"tag_name":"horizon"},{"tag_id":360,"tag_name":"clouds"},{"tag_id":375,"tag_name":"sea"},{"tag_id":1130,"tag_name":"sun"},{"tag_id":7686,"tag_name":"sun rays"},{"tag_id":377,"tag_name":"sunset"}],"cpack":"index=12","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-08/381962_770088b0.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-08/381962_770088b0.jpg","favorite_count":2,"multi_screen":1},{"alias_title":"landscape-565187","title":"landscape","author":"volkan1988","download_count":378,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-29/309233_87b540d4.jpg","type":2,"tags":[],"cpack":"index=13","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-29/309233_87b540d4.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-29/309233_87b540d4.jpg","favorite_count":0,"multi_screen":1},{"alias_title":"anime-537217","title":"anime","author":"Saresz","download_count":3364,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-08/21505_8ebe2a34.jpg","type":2,"tags":[{"tag_id":83,"tag_name":"anime"},{"tag_id":86,"tag_name":"long hair"},{"tag_id":51473,"tag_name":"Klein"},{"tag_id":32158,"tag_name":"Yuuki Asuna"},{"tag_id":32331,"tag_name":"anime boys"},{"tag_id":84,"tag_name":"anime girls"},{"tag_id":11746,"tag_name":"kirigaya kazuto"},{"tag_id":11713,"tag_name":"sword art online"}],"cpack":"index=14","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-08/21505_8ebe2a34.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-08/21505_8ebe2a34.jpg","favorite_count":11,"multi_screen":1},{"alias_title":"glowing-flower-1970477","title":"Glowing Flower","author":"Priti Tari","download_count":0,"thumbnail_url":"http://img2.launcher.ksmobile.com/wallpapers/thumbnail/cml/wp_0d02575d71.jpg","type":1,"tags":[],"cpack":"index=15","wp_apk_url":"https://play.google.com/store/apps/details?id=live.wallpaper.growing.flower.shining.peony.butterfly","package":"live.wallpaper.growing.flower.shining.peony.butterfly","previews":["http://img2.launcher.ksmobile.com/wallpapers/live/wp_a30bd45643.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/wp_b2d92fb4b9.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/wp_2ff7d4bb61.jpg"],"favorite_count":0,"multi_screen":1},{"alias_title":"colorful-382743","title":"colorful","author":"BoSsFiNaL4sRaT","download_count":1141,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-29/349909_19e00e30.jpg","type":2,"tags":[{"tag_id":353,"tag_name":"landscape"},{"tag_id":349,"tag_name":"colorful"},{"tag_id":1703,"tag_name":"trees"},{"tag_id":834,"tag_name":"reflection"},{"tag_id":1146,"tag_name":"lake"},{"tag_id":363,"tag_name":"nature"}],"cpack":"index=16","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-29/349909_19e00e30.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-29/349909_19e00e30.jpg","favorite_count":1,"multi_screen":1},{"alias_title":"stars-213431","title":"stars","author":"microcosmos","download_count":772,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-29/312485_f9dfbe8d.jpg","type":2,"tags":[{"tag_id":353,"tag_name":"landscape"},{"tag_id":100,"tag_name":"stars"},{"tag_id":606,"tag_name":"snow"}],"cpack":"index=17","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-29/312485_f9dfbe8d.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-29/312485_f9dfbe8d.jpg","favorite_count":2,"multi_screen":1},{"alias_title":"abstract-212682","title":"abstract","author":"Yahren","download_count":1438,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-08/361604_1324dc20.jpg","type":2,"tags":[{"tag_id":620,"tag_name":"glass"},{"tag_id":441,"tag_name":"geometry"},{"tag_id":12257,"tag_name":"minimalism"},{"tag_id":7,"tag_name":"blue"},{"tag_id":6,"tag_name":"abstract"}],"cpack":"index=18","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-08/361604_1324dc20.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-08/361604_1324dc20.jpg","favorite_count":51,"multi_screen":1},{"alias_title":"baroque-gilt-butterfly-hd-1970550","title":"Baroque Gilt Butterfly HD","author":"ahatheme","download_count":0,"thumbnail_url":"http://img2.launcher.ksmobile.com/wallpapers/thumbnail/cml/wp_1a0296afec.jpg","type":1,"tags":[],"cpack":"index=19","wp_apk_url":"https://play.google.com/store/apps/details?id=live.wallpaper.t910000812","package":"live.wallpaper.t910000812","previews":["http://img2.launcher.ksmobile.com/wallpapers/live/wp_6604210bb5.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/wp_b4f34639c1.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/wp_840e7b9a5d.jpg"],"favorite_count":0,"multi_screen":1},{"alias_title":"waves-186376","title":"waves","author":"Gubman","download_count":1186,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-29/39159_f1e84366.jpg","type":2,"tags":[{"tag_id":5494,"tag_name":"waves"},{"tag_id":14987,"tag_name":"surfing"}],"cpack":"index=20","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-29/39159_f1e84366.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-29/39159_f1e84366.jpg","favorite_count":1,"multi_screen":1},{"alias_title":"broken-screen-1970512","title":"Broken Screen","author":"Droid","download_count":0,"thumbnail_url":"http://img2.launcher.ksmobile.com/wallpapers/thumbnail/cml/wp_07bebcd302.jpg","type":1,"tags":[],"cpack":"index=21","wp_apk_url":"https://play.google.com/store/apps/details?id=live.wallpaper.broken.screen.samsung.lwp","package":"live.wallpaper.broken.screen.samsung.lwp","previews":["http://img2.launcher.ksmobile.com/wallpapers/live/wp_8a6e6f3f5f.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/wp_e8b123f732.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/wp_8a139bf9cc.jpg"],"favorite_count":0,"multi_screen":1},{"alias_title":"fire-tiger-live-wallpaper-1970535","title":"Fire Tiger Live Wallpaper","author":"ahatheme","download_count":0,"thumbnail_url":"http://img2.launcher.ksmobile.com/wallpapers/thumbnail/cml/wp_013cfacfba.jpg","type":1,"tags":[],"cpack":"index=22","wp_apk_url":"https://play.google.com/store/apps/details?id=live.wallpaper.t910000702&referrer=utm_source%3Dwpp_gcm","package":"live.wallpaper.t910000702","previews":["http://img2.launcher.ksmobile.com/wallpapers/live/ima_name_0_908d8aca62.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/ima_name_1_0f274c1649.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/ima_name_2_70920ce328.jpg"],"favorite_count":0,"multi_screen":1},{"alias_title":"dark-black-1936347","title":"Dark Black","author":"Priti Tari","download_count":0,"thumbnail_url":"http://img2.launcher.ksmobile.com/wallpapers/thumbnail/cml/wp_c5c2db0e1e.jpg","type":1,"tags":[],"cpack":"index=23","wp_apk_url":"https://play.google.com/store/apps/details?id=live.wallpaper.dark.black.cool.samsung","package":"live.wallpaper.dark.black.cool.samsung","previews":["http://img2.launcher.ksmobile.com/wallpapers/live/wp_8c611fb8601c9433b6d9dda96d38a232.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/wp_ba1c23c6c3d6870b983f138b4047fce4.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/wp_4a49fdb25082c807fe0774c17fff2c66.jpg"],"favorite_count":0,"multi_screen":1},{"alias_title":"artwork-169914","title":"artwork","author":"eggmcmuffin","download_count":944,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-29/86871_76860ee2.jpg","type":2,"tags":[{"tag_id":15524,"tag_name":"fan art"},{"tag_id":2418,"tag_name":"magic"},{"tag_id":80,"tag_name":"artwork"},{"tag_id":27087,"tag_name":"warp"},{"tag_id":40587,"tag_name":"David Fuhrer"},{"tag_id":2417,"tag_name":"glowing"},{"tag_id":40586,"tag_name":"geography"},{"tag_id":4702,"tag_name":"surreal"},{"tag_id":4996,"tag_name":"digital art"},{"tag_id":440,"tag_name":"3d"},{"tag_id":231,"tag_name":"space"},{"tag_id":1048,"tag_name":"earth"},{"tag_id":25884,"tag_name":"science fiction"}],"cpack":"index=24","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-29/86871_76860ee2.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-29/86871_76860ee2.jpg","favorite_count":1,"multi_screen":1},{"alias_title":"animals-114103","title":"animals","author":"microcosmos","download_count":383,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-08/322935_79f9ba52.jpg","type":2,"tags":[{"tag_id":4984,"tag_name":"macro"},{"tag_id":527,"tag_name":"animals"},{"tag_id":32384,"tag_name":"lepidoptera"}],"cpack":"index=25","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-08/322935_79f9ba52.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-08/322935_79f9ba52.jpg","favorite_count":12,"multi_screen":1},{"alias_title":"stars-685321","title":"stars","author":"brycied00d","download_count":1865,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-08/28120_cb06522b.jpg","type":2,"tags":[{"tag_id":100,"tag_name":"stars"},{"tag_id":231,"tag_name":"space"}],"cpack":"index=26","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-08/28120_cb06522b.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-08/28120_cb06522b.jpg","favorite_count":17,"multi_screen":1},{"alias_title":"spring-flower-live-wallpaper-1936163","title":"Spring Flower Live Wallpaper","author":"","download_count":0,"thumbnail_url":"http://img2.launcher.ksmobile.com/wallpapers/thumbnail/cml/wphd_e5be5f030e.jpg","type":1,"tags":[],"cpack":"index=27","wp_apk_url":"https://play.google.com/store/apps/details?id=live.wallpaper.nature.spring.flower","package":"live.wallpaper.nature.spring.flower","previews":["http://img2.launcher.ksmobile.com/wallpapers/live/ima_name_0_e79b3a8000.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/ima_name_1_85ec924c64.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/ima_name_2_16c023c162.jpg"],"favorite_count":0,"multi_screen":1},{"alias_title":"leaves-641396","title":"leaves","author":"maharaj","download_count":39685,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-08/302050_215030a5.jpg","type":2,"tags":[{"tag_id":1181,"tag_name":"morning"},{"tag_id":32801,"tag_name":"creeks"},{"tag_id":9955,"tag_name":"mist"},{"tag_id":1703,"tag_name":"trees"},{"tag_id":11632,"tag_name":"bulgaria"},{"tag_id":325,"tag_name":"leaves"},{"tag_id":1167,"tag_name":"fall"},{"tag_id":1545,"tag_name":"path"},{"tag_id":641,"tag_name":"forest"},{"tag_id":353,"tag_name":"landscape"},{"tag_id":363,"tag_name":"nature"}],"cpack":"index=28","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-08/302050_215030a5.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-08/302050_215030a5.jpg","favorite_count":183,"multi_screen":1},{"alias_title":"lion-634852","title":"lion","author":"izmirli","download_count":610,"thumbnail_url":"http://img2.launcher.ksmobile.com/534x474/2016-12-29/330050_9859dc17.jpg","type":2,"tags":[],"cpack":"index=29","image_path":"http://img2.launcher.ksmobile.com/2160x1920/2016-12-29/330050_9859dc17.jpg","preview":"http://img2.launcher.ksmobile.com/1215x1080/2016-12-29/330050_9859dc17.jpg","favorite_count":2,"multi_screen":1}]
     * upack : requestTime=0.0031359195709229&is_cache=1
     */

    private int ret;
    private String msg;
    private int stime;
    private PaginationBean pagination;
    private String upack;
    private List<DataBean> data;


    @Override
    public String toString() {
        return "NewsBean{" +
                "ret=" + ret +
                ", msg='" + msg + '\'' +
                ", stime=" + stime +
                ", pagination=" + pagination +
                ", upack='" + upack + '\'' +
                ", data=" + data +
                '}';
    }

    public static NewsBean objectFromData(String str) {

        return new Gson().fromJson(str, NewsBean.class);
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStime() {
        return stime;
    }

    public void setStime(int stime) {
        this.stime = stime;
    }

    public PaginationBean getPagination() {
        return pagination;
    }

    public void setPagination(PaginationBean pagination) {
        this.pagination = pagination;
    }

    public String getUpack() {
        return upack;
    }

    public void setUpack(String upack) {
        this.upack = upack;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PaginationBean implements Serializable {
        /**
         * hasMore : 1
         * count : 30
         * offset : 30
         * total : 7719
         */

        private int hasMore;
        private int count;
        private int offset;
        private int total;

        @Override
        public String toString() {
            return "PaginationBean{" +
                    "hasMore=" + hasMore +
                    ", count=" + count +
                    ", offset=" + offset +
                    ", total=" + total +
                    '}';
        }

        public static PaginationBean objectFromData(String str) {

            return new Gson().fromJson(str, PaginationBean.class);
        }

        public int getHasMore() {
            return hasMore;
        }

        public void setHasMore(int hasMore) {
            this.hasMore = hasMore;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public static class DataBean implements Serializable {
        /**
         * alias_title : nature-309607
         * title : nature
         * author : ishmad
         * download_count : 410
         * thumbnail_url : http://img2.launcher.ksmobile.com/534x474/2016-12-29/326709_3ce2fb51.jpg
         * type : 2
         * tags : [{"tag_id":1881,"tag_name":"photography"},{"tag_id":1703,"tag_name":"trees"},{"tag_id":48192,"tag_name":"rock stairs"},{"tag_id":364,"tag_name":"road"},{"tag_id":375,"tag_name":"sea"},{"tag_id":363,"tag_name":"nature"},{"tag_id":34548,"tag_name":"Sri Lanka"}]
         * cpack : index=0
         * image_path : http://img2.launcher.ksmobile.com/2160x1920/2016-12-29/326709_3ce2fb51.jpg
         * preview : http://img2.launcher.ksmobile.com/1215x1080/2016-12-29/326709_3ce2fb51.jpg
         * favorite_count : 0
         * multi_screen : 1
         * wp_apk_url : https://play.google.com/store/apps/details?id=live.wallpaper.romantic.sakura
         * package : live.wallpaper.romantic.sakura
         * previews : ["http://img2.launcher.ksmobile.com/wallpapers/live/ima_name_0_1728a79fcc.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/ima_name_1_2602922e5f.jpg","http://img2.launcher.ksmobile.com/wallpapers/live/ima_name_2_1fd6a2f305.jpg"]
         */

        private String alias_title;
        private String title;
        private String author;
        private int download_count;
        private String thumbnail_url;
        private int type;
        private String cpack;
        private String image_path;
        private String preview;
        private int favorite_count;
        private int multi_screen;
        private String wp_apk_url;
        @SerializedName("package")
        private String packageX;
        private List<TagsBean> tags;
        private List<String> previews;
        private String ad_image_url;
        private String ad_title;
        private String ad_body;
        private String ad_button;
        private int adtype = 0;
        private int IndexPosition = 0;
        private int PageIndex = 0;
        private boolean isNull = true;
        private double adRandomNum = 0.0;


        @Override
        public String toString() {
            return "DataBean{" +
                    "alias_title='" + alias_title + '\'' +
                    ", title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", download_count=" + download_count +
                    ", thumbnail_url='" + thumbnail_url + '\'' +
                    ", type=" + type +
                    ", cpack='" + cpack + '\'' +
                    ", image_path='" + image_path + '\'' +
                    ", preview='" + preview + '\'' +
                    ", favorite_count=" + favorite_count +
                    ", multi_screen=" + multi_screen +
                    ", wp_apk_url='" + wp_apk_url + '\'' +
                    ", packageX='" + packageX + '\'' +
                    ", tags=" + tags +
                    ", previews=" + previews +
                    '}';
        }


        public boolean isNull() {
            return isNull;
        }

        public void setNull(boolean aNull) {
            isNull = aNull;
        }

        public int getPageIndex() {
            return PageIndex;
        }

        public void setPageIndex(int pageIndex) {
            PageIndex = pageIndex;
        }

        public String getAlias_title() {
            return alias_title;
        }

        public void setAlias_title(String alias_title) {
            this.alias_title = alias_title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getDownload_count() {
            return download_count;
        }

        public void setDownload_count(int download_count) {
            this.download_count = download_count;
        }

        public String getThumbnail_url() {
            return thumbnail_url;
        }

        public void setThumbnail_url(String thumbnail_url) {
            this.thumbnail_url = thumbnail_url;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCpack() {
            return cpack;
        }

        public void setCpack(String cpack) {
            this.cpack = cpack;
        }

        public String getImage_path() {
            return image_path;
        }

        public void setImage_path(String image_path) {
            this.image_path = image_path;
        }

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public int getFavorite_count() {
            return favorite_count;
        }

        public void setFavorite_count(int favorite_count) {
            this.favorite_count = favorite_count;
        }

        public int getMulti_screen() {
            return multi_screen;
        }

        public void setMulti_screen(int multi_screen) {
            this.multi_screen = multi_screen;
        }

        public String getWp_apk_url() {
            return wp_apk_url;
        }

        public void setWp_apk_url(String wp_apk_url) {
            this.wp_apk_url = wp_apk_url;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public List<String> getPreviews() {
            return previews;
        }

        public void setPreviews(List<String> previews) {
            this.previews = previews;
        }

        public String getAd_image_url() {
            return ad_image_url;
        }

        public void setAd_image_url(String ad_image_url) {
            this.ad_image_url = ad_image_url;
        }

        public String getAd_title() {
            return ad_title;
        }

        public void setAd_title(String ad_title) {
            this.ad_title = ad_title;
        }

        public String getAd_body() {
            return ad_body;
        }

        public void setAd_body(String ad_body) {
            this.ad_body = ad_body;
        }

        public String getAd_button() {
            return ad_button;
        }

        public void setAd_button(String ad_button) {
            this.ad_button = ad_button;
        }

        public int getAdtype() {
            return adtype;
        }

        public void setAdtype(int adtype) {
            this.adtype = adtype;
        }

        public int getIndexPosition() {
            return IndexPosition;
        }

        public void setIndexPosition(int indexPosition) {
            IndexPosition = indexPosition;
        }

        public double getAdRandomNum() {
            return adRandomNum;
        }

        public void setAdRandomNum(double adRandomNum) {
            this.adRandomNum = adRandomNum;
        }

        public static class TagsBean implements Serializable {
            /**
             * tag_id : 1881
             * tag_name : photography
             */

            private int tag_id;
            private String tag_name;

            @Override
            public String toString() {
                return "TagsBean{" +
                        "tag_id=" + tag_id +
                        ", tag_name='" + tag_name + '\'' +
                        '}';
            }

            public static TagsBean objectFromData(String str) {

                return new Gson().fromJson(str, TagsBean.class);
            }

            public int getTag_id() {
                return tag_id;
            }

            public void setTag_id(int tag_id) {
                this.tag_id = tag_id;
            }

            public String getTag_name() {
                return tag_name;
            }

            public void setTag_name(String tag_name) {
                this.tag_name = tag_name;
            }
        }
    }
}
