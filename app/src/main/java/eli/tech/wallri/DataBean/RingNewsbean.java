package eli.tech.wallri.DataBean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 小雷 on 2018/1/26.
 * 这个是铃声和内容的bean
 */

public class RingNewsbean implements Serializable {


    private List<RingtonesBean> ringtones;

    public static RingNewsbean objectFromData(String str) {

        return new Gson().fromJson(str, RingNewsbean.class);
    }

    public List<RingtonesBean> getRingtones() {
        return ringtones;
    }

    public void setRingtones(List<RingtonesBean> ringtones) {
        this.ringtones = ringtones;
    }

    @Override
    public String toString() {
        return "RingNewsbean{" +
                "ringtones=" + ringtones +
                '}';
    }

    public static class RingtonesBean implements Serializable {
        /**
         * id : 256914
         * title : Whistle Tone
         * artist : chick
         * user_name : tejas6789mevada
         * ringtone : 256914_Samsung_Whistle_galaxy.mp3
         * category_name : Sound Effects
         * thumbnail : thumb_256914_160.jpg
         * avg_rating : 1.9
         * download_count : 420290
         * total_votes : 6601
         */

        private String id;
        private String title;
        private String artist;
        private String user_name;
        private String ringtone;
        private String category_name;
        private String thumbnail;
        private String avg_rating;
        private String download_count;
        private String total_votes;
        private String ad_image_url;
        private String ad_title;
        private String ad_body;
        private String ad_button;
        private int type = 0;





        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getRingtone() {
            return ringtone;
        }

        public void setRingtone(String ringtone) {
            this.ringtone = ringtone;
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

        public String getAvg_rating() {
            return avg_rating;
        }

        public void setAvg_rating(String avg_rating) {
            this.avg_rating = avg_rating;
        }

        public String getDownload_count() {
            return download_count;
        }

        public void setDownload_count(String download_count) {
            this.download_count = download_count;
        }

        public String getTotal_votes() {
            return total_votes;
        }

        public void setTotal_votes(String total_votes) {
            this.total_votes = total_votes;
        }

//        public Object getObject() {
//            return object;
//        }
//
//        public void setObject(Object object) {
//            this.object = object;
//        }


        public String getAd_image_url() {
            return ad_image_url;
        }

        public String getAd_title() {
            return ad_title;
        }

        public String getAd_body() {
            return ad_body;
        }

        public String getAd_button() {
            return ad_button;
        }


        public void setAd_image_url(String ad_image_url) {
            this.ad_image_url = ad_image_url;
        }

        public void setAd_title(String ad_title) {
            this.ad_title = ad_title;
        }

        public void setAd_body(String ad_body) {
            this.ad_body = ad_body;
        }

        public void setAd_button(String ad_button) {
            this.ad_button = ad_button;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "RingtonesBean{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", artist='" + artist + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", ringtone='" + ringtone + '\'' +
                    ", category_name='" + category_name + '\'' +
                    ", thumbnail='" + thumbnail + '\'' +
                    ", avg_rating='" + avg_rating + '\'' +
                    ", download_count='" + download_count + '\'' +
                    ", total_votes='" + total_votes + '\'' +
                    '}';
        }
    }
}
