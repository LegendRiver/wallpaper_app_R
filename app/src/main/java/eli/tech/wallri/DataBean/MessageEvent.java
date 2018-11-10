package eli.tech.wallri.DataBean;

/**
 * Created by 小雷 on 2017/12/27.
 * 这个是eventbus的消息bean类
 */

public class MessageEvent {

    private int message;

    public MessageEvent(int message) {
        this.message = message;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }


}
