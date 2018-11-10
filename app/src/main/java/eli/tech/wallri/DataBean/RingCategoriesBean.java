package eli.tech.wallri.DataBean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by 小雷 on 2018/1/26.
 * 这个是铃声分类的bean
 */

public class RingCategoriesBean {


    private List<CategoriesBean> categories;

    public static RingCategoriesBean objectFromData(String str) {

        return new Gson().fromJson(str, RingCategoriesBean.class);
    }

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        this.categories = categories;
    }


    @Override
    public String toString() {
        return "RingCategoriesBean{" +
                "categories=" + categories +
                '}';
    }

    public static class CategoriesBean {
        /**
         * cat_id : 46
         * icon : r-Alarms.png
         * category_name : Alarms
         * active : y
         */

        private String cat_id;
        private String icon;
        private String category_name;
        private String active;

        public static CategoriesBean objectFromData(String str) {

            return new Gson().fromJson(str, CategoriesBean.class);
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        @Override
        public String toString() {
            return "CategoriesBean{" +
                    "cat_id='" + cat_id + '\'' +
                    ", icon='" + icon + '\'' +
                    ", category_name='" + category_name + '\'' +
                    ", active='" + active + '\'' +
                    '}';
        }
    }
}
