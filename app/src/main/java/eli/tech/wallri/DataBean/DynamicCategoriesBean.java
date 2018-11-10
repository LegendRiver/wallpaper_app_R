package eli.tech.wallri.DataBean;

import java.util.List;

/**
 * Created by mac on 2018/3/22.
 */

public class DynamicCategoriesBean {
    /**
     * data : [{"display":"抽 象","id":1,"image":"http://vwall-cdn.jidiangame.cn/live_wallpaper/category/abstract.jpg","name":"Abstract"},{"display":"动 物","id":2,"image":"http://vwall-cdn.jidiangame.cn/live_wallpaper/category/animal.jpg","name":"Animal"},{"display":"动 画","id":3,"image":"http://vwall-cdn.jidiangame.cn/live_wallpaper/category/animation.jpg","name":"Animation"},{"display":"歌 舞","id":4,"image":"http://vwall-cdn.jidiangame.cn/live_wallpaper/category/dance.jpg","name":"Dance"},{"display":"自 然","id":7,"image":"http://vwall-cdn.jidiangame.cn/live_wallpaper/category/nature.jpg","name":"Nature"},{"display":"其 他","id":12,"image":"http://vwall-cdn.jidiangame.cn/live_wallpaper/category/undefine.jpg","name":"Undefine"}]
     * error : {"errorCode":0,"errorMsg":""}
     * result : 0
     * timestamp : 0
     */

    private ErrorBean error;
    private int result;
    private int timestamp;
    private List<DataBean> data;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class ErrorBean {
        /**
         * errorCode : 0
         * errorMsg :
         */

        private int errorCode;
        private String errorMsg;

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }
    }

    public static class DataBean {
        /**
         * display : 抽 象
         * id : 1
         * image : http://vwall-cdn.jidiangame.cn/live_wallpaper/category/abstract.jpg
         * name : Abstract
         */

        private String display;
        private int id;
        private String image;
        private String name;

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
