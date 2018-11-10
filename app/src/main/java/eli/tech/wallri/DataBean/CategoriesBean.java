package eli.tech.wallri.DataBean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by 小雷 on 2018/1/16.
 */

public class CategoriesBean {


    /**
     * ret : 0
     * msg : ok
     * stime : 1516095006
     * pagination : {"hasMore":0,"count":26,"offset":26,"total":0}
     * data : [{"category_id":30,"category_name":"Cool","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/category_cool.jpg","lang_text":"Cool"},{"category_id":41,"category_name":"Featured","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/category_featured.jpg","lang_text":"Featured"},{"category_id":11,"category_name":"Love","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/category_love.jpg","lang_text":"Love"},{"category_id":6,"category_name":"Vehicle","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/category_vehicle.jpg","lang_text":"Vehicle"},{"category_id":46,"category_name":"Landscape","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/wphd_1bd942a879.jpg","lang_text":"Landscape"},{"category_id":7,"category_name":"Design","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/category_design.jpg","lang_text":"Design"},{"category_id":43,"category_name":"Gril","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/wphd_331f728282.jpg","lang_text":"Girl"},{"category_id":31,"category_name":"Abstract","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/category_abstract.jpg","lang_text":"Abstract"},{"category_id":52,"category_name":"Space","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/wphd_634e320649.jpg","lang_text":"Space"},{"category_id":12,"category_name":"Animal","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/category_animal.jpg","lang_text":"Animal"},{"category_id":28,"category_name":"Text","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/category_text.jpg","lang_text":"Text"},{"category_id":45,"category_name":"Mountains","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/wphd_7c0b265570.jpg","lang_text":"Mountains"},{"category_id":49,"category_name":"Blue","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/wphd_1a22d021f9.jpg","lang_text":"Blue"},{"category_id":53,"category_name":"Sunset","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/wphd_fbcc0b9e87.jpg","lang_text":"Sunset"},{"category_id":54,"category_name":"Trees","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/wphd_46d6fd73f7.jpg","lang_text":"Trees"},{"category_id":55,"category_name":"Water","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/wphd_8eafcb0824.jpg","lang_text":"Water"},{"category_id":1,"category_name":"Nature","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/category_nature.jpg","lang_text":"Nature"},{"category_id":50,"category_name":"Sea","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/wphd_7ec429b33d.jpg","lang_text":"Sea"},{"category_id":47,"category_name":"Food","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/wphd_5083b27bf5.jpg","lang_text":"Food"},{"category_id":51,"category_name":"Snow","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/wphd_e4d2b2f79b.jpg","lang_text":"Snow"},{"category_id":29,"category_name":"Flower","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/category_flower.jpg","lang_text":"Flower"},{"category_id":25,"category_name":"Entertainment","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/category_entertainment.jpg","lang_text":"Entertainment"},{"category_id":15,"category_name":"Sports","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/category_sport.jpg","lang_text":"Sport"},{"category_id":48,"category_name":"Clouds","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/wphd_e3dec0344b.jpg","lang_text":"Clouds"},{"category_id":4,"category_name":"Festival","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/category_festival.jpg","lang_text":"Festival"},{"category_id":21,"category_name":"Others","thumbnail":"http://img2.launcher.ksmobile.com/wallpapers/category/category_others.jpg","lang_text":"Others"}]
     * upack : requestTime=0.0018010139465332&is_cache=1
     */

    private int ret;
    private String msg;
    private int stime;
    private PaginationBean pagination;
    private String upack;
    private List<DataBean> data;

    public static CategoriesBean objectFromData(String str) {

        return new Gson().fromJson(str, CategoriesBean.class);
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


    @Override
    public String toString() {
        return "CategoriesBean{" +
                "ret=" + ret +
                ", msg='" + msg + '\'' +
                ", stime=" + stime +
                ", pagination=" + pagination +
                ", upack='" + upack + '\'' +
                ", data=" + data +
                '}';
    }

    public static class PaginationBean {
        /**
         * hasMore : 0
         * count : 26
         * offset : 26
         * total : 0
         */

        private int hasMore;
        private int count;
        private int offset;
        private int total;

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

        @Override
        public String toString() {
            return "PaginationBean{" +
                    "hasMore=" + hasMore +
                    ", count=" + count +
                    ", offset=" + offset +
                    ", total=" + total +
                    '}';
        }
    }


    public static class DataBean {
        /**
         * category_id : 30
         * category_name : Cool
         * thumbnail : http://img2.launcher.ksmobile.com/wallpapers/category/category_cool.jpg
         * lang_text : Cool
         */

        private int category_id;
        private String category_name;
        private String thumbnail;
        private String lang_text;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getLang_text() {
            return lang_text;
        }

        public void setLang_text(String lang_text) {
            this.lang_text = lang_text;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "category_id=" + category_id +
                    ", category_name='" + category_name + '\'' +
                    ", thumbnail='" + thumbnail + '\'' +
                    ", lang_text='" + lang_text + '\'' +
                    '}';
        }
    }
}
