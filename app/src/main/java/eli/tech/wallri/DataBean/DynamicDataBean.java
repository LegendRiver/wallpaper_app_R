package eli.tech.wallri.DataBean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 小雷 on 2018/3/20.
 */

public class DynamicDataBean implements Serializable {

    /**
     * data : [{"id":707,"key":"my_MTZfZTUyY2M5OTg3MDk2YTBkNjcxY2QzZDcwMWRkZDE1N2EubXA0","cate":"Undefine","preview":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_MTZfZTUyY2M5OTg3MDk2YTBkNjcxY2QzZDcwMWRkZDE1N2EubXA0_preview.mp4","video":"http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_MTZfZTUyY2M5OTg3MDk2YTBkNjcxY2QzZDcwMWRkZDE1N2EubXA0.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_MTZfZTUyY2M5OTg3MDk2YTBkNjcxY2QzZDcwMWRkZDE1N2EubXA0.jpg@!thumb","title":"","label":"","desc":"","size":13713452,"pixels":"720x1280","downloads":0,"duration":27480,"hasAudio":1,"visible":1,"legal":1,"score":0,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_MTZfZTUyY2M5OTg3MDk2YTBkNjcxY2QzZDcwMWRkZDE1N2EubXA0_preview.m3u8","likecount":0,"hotValue":0,"preId":4431,"countryCode":"default","tags":[{"id":1411,"lang":"zh-CN","tag":"梦幻汽车","weight":8},{"id":1384,"lang":"zh-CN","tag":"超级跑车","weight":3},{"id":1386,"lang":"zh-CN","tag":"豪华车","weight":7}]},{"id":954,"key":"my_ZGFqaW5neXUubXA0","cate":"Undefine","preview":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_ZGFqaW5neXUubXA0_preview.mp4","video":"http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_ZGFqaW5neXUubXA0.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_ZGFqaW5neXUubXA0.jpg@!thumb","title":"","label":"","desc":"","size":5987371,"pixels":"608x1080","downloads":0,"duration":37230,"hasAudio":1,"visible":1,"legal":1,"score":0,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_ZGFqaW5neXUubXA0_preview.m3u8","likecount":0,"hotValue":0,"preId":4432,"countryCode":"default","tags":[{"id":189,"lang":"zh-CN","tag":"水下","weight":21},{"id":1377,"lang":"zh-CN","tag":"鱼","weight":11},{"id":1413,"lang":"zh-CN","tag":"跳","weight":9}]},{"key":"437955976577752_490735817966434","cate":"ads","type":"facebook"},{"id":889,"key":"my_eXVxdW4ubXA0","cate":"Undefine","preview":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_eXVxdW4ubXA0_pr  eview.mp4","video":"http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_eXVxdW4ubXA0.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_eXVxdW4ubXA0.jpg@!thumb","title":"","label":"","desc":"","size":1126555,"pixels":"720x1280","downloads":0,"duration":3090,"hasAudio":1,"visible":1,"legal":1,"score":100,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_eXVxdW4ubXA0_preview.m3u8","likecount":0,"hotValue":0,"preId":4433,"countryCode":"default","tags":[{"id":189,"lang":"zh-CN","tag":"水下","weight":21},{"id":1358,"lang":"zh-CN","tag":"水族馆","weight":8},{"id":1377,"lang":"zh-CN","tag":"鱼","weight":11}]},{"id":920,"key":"my_54Kt54GrLm1wNA==","cate":"Undefine","preview":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_54Kt54GrLm1wNA==_preview.mp4","video":"http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_54Kt54GrLm1wNA==.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_54Kt54GrLm1wNA==.jpg@!thumb","title":"","label":"","desc":"","size":3450002,"pixels":"608x1080","downloads":0,"duration":10090,"hasAudio":1,"visible":1,"legal":1,"score":0,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_54Kt54GrLm1wNA==_preview.m3u8","likecount":0,"hotValue":0,"preId":4434,"countryCode":"default","tags":[{"id":171,"lang":"zh-CN","tag":"黑暗","weight":47},{"id":1405,"lang":"zh-CN","tag":"冷","weight":10},{"id":201,"lang":"zh-CN","tag":"火焰","weight":15}]},{"id":460,"key":"my_VWx0cmEgSEQgRmlzaCBUYW5rIFZpZGVvIGFuZCBTY3JlZW5zYXZlcl8xLm1wNA==","cate":"Nature","preview":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_VWx0cmEgSEQgRmlzaCBUYW5rIFZpZGVvIGFuZCBTY3JlZW5zYXZlcl8xLm1wNA==_preview.mp4","video":"http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_VWx0cmEgSEQgRmlzaCBUYW5rIFZpZGVvIGFuZCBTY3JlZW5zYXZlcl8xLm1wNA==.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_VWx0cmEgSEQgRmlzaCBUYW5rIFZpZGVvIGFuZCBTY3JlZW5zYXZlcl8xLm1wNA==.jpg","title":"","label":"label","desc":"desc","  size":6591157,"pixels":"720x1280","downloads":0,"duration":23960,"hasAudio":1,"visible":1,"legal":1,"score":100,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_VWx0cmEgSEQgRmlzaCBUYW5rIFZpZGVvIGFuZCBTY3JlZW5zYXZlcl8xLm1wNA==_preview.m3u8","likecount":0,"hotValue":0,"preId":4435,"countryCode":"default","tags":[{"id":189,"lang":"zh-CN","tag":"水下","weight":21},{"id":163,"lang":"zh-CN","tag":"性质","weight":125},{"id":1377,"lang":"zh-CN","tag":"鱼","weight":11}]},{"id":963,"key":"my_ZXJoYWhhLm1wNA==","cate":"Undefine","preview":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_ZXJoYWhhLm1wNA==_preview.mp4","video":"http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_ZXJoYWhhLm1wNA==.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_ZXJoYWhhLm1wNA==.jpg@!thumb","title":"","label":"","desc":"","size":1286433,"pixels":"540x960","downloads":0,"duration":9500,"hasAudio":1,"visible":1,"legal":1,"score":0,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_ZXJoYWhhLm1wNA==_preview.m3u8","likecount":0,"hotValue":0,"preId":4436,"countryCode":"default","tags":[{"id":1347,"lang":"zh-CN","tag":"滑稽","weight":4},{"id":1353,"lang":"zh-CN","tag":"有趣的时刻","weight":4},{"id":203,"lang":"zh-CN","tag":"狗","weight":14}]},{"id":781,"key":"my_eGlhb2Nob3V5dS5tcDQ=","cate":"Undefine","preview":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_eGlhb2Nob3V5dS5tcDQ=_preview.mp4","video":"http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_eGlhb2Nob3V5dS5tcDQ=.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_eGlhb2Nob3V5dS5tcDQ=.jpg@!thumb","title":"","label":"","desc":"","size":1893587,"pixels":"720x1280","downloads":0,"duration":3000,"hasAudio":1,"visible":1,"legal":1,"score":100,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_eGlhb2Nob3V5dS5tcDQ=_preview.m3u8","likecount":0,"hotValue":0,"preId":4437,"countryCode":"default","tags":[{"id":1377,"lang":"zh-CN","tag":"鱼","weight  ":11},{"id":189,"lang":"zh-CN","tag":"水下","weight":21},{"id":1358,"lang":"zh-CN","tag":"水族馆","weight":8}]},{"id":532,"key":"my_Q2F0cyBJbiBIRCBJSUkubXA0","cate":"Animal","preview":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_Q2F0cyBJbiBIRCBJSUkubXA0_preview.mp4","video":"http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_Q2F0cyBJbiBIRCBJSUkubXA0.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_Q2F0cyBJbiBIRCBJSUkubXA0.jpg","title":"","label":"label","desc":"desc","size":3387868,"pixels":"720x1280","downloads":0,"duration":14780,"hasAudio":1,"visible":1,"legal":1,"score":0,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_Q2F0cyBJbiBIRCBJSUkubXA0_preview.m3u8","likecount":0,"hotValue":0,"preId":4438,"countryCode":"default","tags":[{"id":166,"lang":"zh-CN","tag":"可爱","weight":68},{"id":192,"lang":"zh-CN","tag":"宠物","weight":19},{"id":193,"lang":"zh-CN","tag":"猫","weight":17}]},{"id":946,"key":"my_YWlxaW5nbmlhby5tcDQ=","cate":"Undefine","preview":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_YWlxaW5nbmlhby5tcDQ=_preview.mp4","video":"http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_YWlxaW5nbmlhby5tcDQ=.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_YWlxaW5nbmlhby5tcDQ=.jpg@!thumb","title":"","label":"","desc":"","size":2143882,"pixels":"336x600","downloads":0,"duration":58000,"hasAudio":1,"visible":1,"legal":1,"score":100,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_YWlxaW5nbmlhby5tcDQ=_preview.m3u8","likecount":0,"hotValue":0,"preId":4439,"countryCode":"default","tags":[{"id":194,"lang":"zh-CN","tag":"放松音乐","weight":17},{"id":186,"lang":"zh-CN","tag":"浪漫","weight":24},{"id":1363,"lang":"zh-CN","tag":"轻松的音乐","weight":17},{"id":1434,"lang":"zh-CN","tag":"鸟类","weight":6}]},{"id":972,"key":"my_ZmYxMC5tcDQ=","cate":"Undefine","preview":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_ZmYxMC5tcDQ=_preview.mp4","video":"http://vwall-cdn.jidianga  me.cn/live_wallpaper/hd/my_ZmYxMC5tcDQ=.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_ZmYxMC5tcDQ=.jpg@!thumb","title":"","label":"","desc":"","size":17858475,"pixels":"608x1080","downloads":0,"duration":58390,"hasAudio":1,"visible":1,"legal":1,"score":100,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_ZmYxMC5tcDQ=_preview.m3u8","likecount":0,"hotValue":0,"preId":4440,"countryCode":"default","tags":[{"id":189,"lang":"zh-CN","tag":"水下","weight":21},{"id":186,"lang":"zh-CN","tag":"浪漫","weight":24},{"id":5,"lang":"zh-CN","tag":"动漫","weight":85}]},{"id":987,"key":"my_YW5qaW5nLm1wNA==","cate":"Undefine","preview":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_YW5qaW5nLm1wNA==_preview.mp4","video":"http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_YW5qaW5nLm1wNA==.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_YW5qaW5nLm1wNA==.jpg@!thumb","title":"","label":"","desc":"","size":322598,"pixels":"336x600","downloads":0,"duration":10050,"hasAudio":1,"visible":1,"legal":1,"score":0,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_YW5qaW5nLm1wNA==_preview.m3u8","likecount":0,"hotValue":0,"preId":4441,"countryCode":"default","tags":[{"id":5,"lang":"zh-CN","tag":"动漫","weight":85},{"id":210,"lang":"zh-CN","tag":"冷静","weight":11},{"id":174,"lang":"zh-CN","tag":"冥想","weight":39}]},{"id":965,"key":"my_aGFpc2hhZ25yaWNodS5tcDQ=","cate":"Undefine","preview":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_aGFpc2hhZ25yaWNodS5tcDQ=_preview.mp4","video":"http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_aGFpc2hhZ25yaWNodS5tcDQ=.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_aGFpc2hhZ25yaWNodS5tcDQ=.jpg@!thumb","title":"","label":"","desc":"","size":6066037,"pixels":"608x1080","downloads":0,"duration":20270,"hasAudio":1,"visible":1,"legal":1,"score":0,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_aGFpc2hhZ25yaWNodS5tcDQ=_p  review.m3u8","likecount":0,"hotValue":0,"preId":4442,"countryCode":"default","tags":[{"id":208,"lang":"zh-CN","tag":"海洋","weight":12},{"id":187,"lang":"zh-CN","tag":"没有音乐","weight":24},{"id":9,"lang":"zh-CN","tag":"天空","weight":28}]},{"key":"437955976577752_490735891299760","cate":"ads","type":"facebook"},{"id":956,"key":"my_Z2FvbG91Lm1wNA==","cate":"Undefine","preview":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_Z2FvbG91Lm1wNA==_preview.mp4","video":"http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_Z2FvbG91Lm1wNA==.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_Z2FvbG91Lm1wNA==.jpg@!thumb","title":"","label":"","desc":"","size":252903,"pixels":"336x600","downloads":0,"duration":3270,"hasAudio":1,"visible":1,"legal":1,"score":0,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_Z2FvbG91Lm1wNA==_preview.m3u8","likecount":0,"hotValue":0,"preId":4443,"countryCode":"default","tags":[{"id":177,"lang":"zh-CN","tag":"声音","weight":38},{"id":171,"lang":"zh-CN","tag":"黑暗","weight":47},{"id":179,"lang":"zh-CN","tag":"超高清","weight":30}]},{"id":911,"key":"my_5rW36Z2iLm1wNA==","cate":"Undefine","preview":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_5rW36Z2iLm1wNA==_preview.mp4","video":"http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_5rW36Z2iLm1wNA==.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_5rW36Z2iLm1wNA==.jpg@!thumb","title":"","label":"","desc":"","size":17302897,"pixels":"720x1280","downloads":0,"duration":60330,"hasAudio":1,"visible":1,"legal":1,"score":0,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_5rW36Z2iLm1wNA==_preview.m3u8","likecount":0,"hotValue":0,"preId":4444,"countryCode":"default","tags":[{"id":163,"lang":"zh-CN","tag":"性质","weight":125},{"id":208,"lang":"zh-CN","tag":"海洋","weight":12},{"id":9,"lang":"zh-CN","tag":"天空","weight":28}]},{"id":974,"key":"my_ZGFoYWkubXA0","cate":"Undefine","preview":"http://vwall-cdn.jidiangame.cn/liv  e_wallpaper/preview/my_ZGFoYWkubXA0_preview.mp4","video":"http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_ZGFoYWkubXA0.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_ZGFoYWkubXA0.jpg@!thumb","title":"","label":"","desc":"","size":377516,"pixels":"336x600","downloads":0,"duration":2870,"hasAudio":1,"visible":1,"legal":1,"score":0,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_ZGFoYWkubXA0_preview.m3u8","likecount":0,"hotValue":0,"preId":4445,"countryCode":"default","tags":[{"id":163,"lang":"zh-CN","tag":"性质","weight":125},{"id":177,"lang":"zh-CN","tag":"声音","weight":38},{"id":208,"lang":"zh-CN","tag":"海洋","weight":12}]},{"id":916,"key":"my_6aaZ6L2m576O5aWzLm1wNA==","cate":"Undefine","preview":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_6aaZ6L2m576O5aWzLm1wNA==_preview.mp4","video":"http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_6aaZ6L2m576O5aWzLm1wNA==.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_6aaZ6L2m576O5aWzLm1wNA==.jpg@!thumb","title":"","label":"","desc":"","size":9651508,"pixels":"720x1280","downloads":0,"duration":53520,"hasAudio":1,"visible":1,"legal":1,"score":100,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_6aaZ6L2m576O5aWzLm1wNA==_preview.m3u8","likecount":0,"hotValue":0,"preId":4446,"countryCode":"default","tags":[{"id":1384,"lang":"zh-CN","tag":"超级跑车","weight":3},{"id":1411,"lang":"zh-CN","tag":"梦幻汽车","weight":8},{"id":1386,"lang":"zh-CN","tag":"豪华车","weight":7}]},{"id":270,"key":"my_d2FsbDIuOS5tcDQ=","cate":"Abstract","preview":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_d2FsbDIuOS5tcDQ=_preview.mp4","video":"http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_d2FsbDIuOS5tcDQ=.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_d2FsbDIuOS5tcDQ=.jpg@!thumb","title":"wall2.9","label":"label","desc":"desc","size":1686496,"pixels":"720x1280","downloads":0,"duration":15000,"hasAudio":1,"visible":1,"lega l":1,"score":0,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_d2FsbDIuOS5tcDQ=_preview.m3u8","likecount":0,"hotValue":0,"preId":4447,"countryCode":"default","tags":[{"id":1388,"lang":"zh-CN","tag":"高科技","weight":2},{"id":1349,"lang":"zh-CN","tag":"VFX","weight":111},{"id":164,"lang":"zh-CN","tag":"视觉特效","weight":111},{"id":167,"lang":"zh-CN","tag":"颜色","weight":60}]},{"id":982,"key":"my_aml0YS5tcDQ=","cate":"Undefine","preview":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_aml0YS5tcDQ=_preview.mp4","video":"http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_aml0YS5tcDQ=.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_aml0YS5tcDQ=.jpg@!thumb","title":"","label":"","desc":"","size":449238,"pixels":"336x600","downloads":0,"duration":10570,"hasAudio":1,"visible":1,"legal":1,"score":0,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_aml0YS5tcDQ=_preview.m3u8","likecount":0,"hotValue":0,"preId":4448,"countryCode":"default","tags":[{"id":1390,"lang":"zh-CN","tag":"放松","weight":10},{"id":177,"lang":"zh-CN","tag":"声音","weight":38},{"id":5,"lang":"zh-CN","tag":"动漫","weight":85}]},{"id":122,"key":"my_VmlzdWFsIGJhY2tncm91bmQg4oCeRFZEReKAnCAobG9vcCkgYnkgYmVlcGxlLm1wNA==","cate":"Abstract","preview":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_VmlzdWFsIGJhY2tncm91bmQg4oCeRFZEReKAnCAobG9vcCkgYnkgYmVlcGxlLm1wNA==_preview.mp4","video":"http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_VmlzdWFsIGJhY2tncm91bmQg4oCeRFZEReKAnCAobG9vcCkgYnkgYmVlcGxlLm1wNA==.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_VmlzdWFsIGJhY2tncm91bmQg4oCeRFZEReKAnCAobG9vcCkgYnkgYmVlcGxlLm1wNA==.jpg@!thumb","title":"rock road","label":"label","desc":"desc","size":7065073,"pixels":"720x1280","downloads":0,"duration":20100,"hasAudio":1,"visible":1,"legal":1,"score":0,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_VmlzdWFsIGJhY2tncm91bmQg4oCeRFZEReKAnCAobG9vcCkgYnkgYm  VlcGxlLm1wNA==_preview.m3u8","likecount":0,"hotValue":0,"preId":4449,"countryCode":"default","tags":[{"id":174,"lang":"zh-CN","tag":"冥想","weight":39},{"id":1349,"lang":"zh-CN","tag":"VFX","weight":111},{"id":167,"lang":"zh-CN","tag":"颜色","weight":60},{"id":164,"lang":"zh-CN","tag":"视觉特效","weight":111}]},{"id":842,"key":"my_bG9sbGlxaW5zaGVucXVhbi5tcDQ=","cate":"Undefine","preview":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_bG9sbGlxaW5zaGVucXVhbi5tcDQ=_preview.mp4","video":"http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_bG9sbGlxaW5zaGVucXVhbi5tcDQ=.mp4","thumb":"http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_bG9sbGlxaW5zaGVucXVhbi5tcDQ=.jpg@!thumb","title":"","label":"","desc":"","size":8989079,"pixels":"336x600","downloads":0,"duration":96080,"hasAudio":1,"visible":1,"legal":1,"score":0,"preview2":"http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_bG9sbGlxaW5zaGVucXVhbi5tcDQ=_preview.m3u8","likecount":0,"hotValue":0,"preId":4450,"countryCode":"default","tags":[{"id":164,"lang":"zh-CN","tag":"视觉特效","weight":111},{"id":179,"lang":"zh-CN","tag":"超高清","weight":30},{"id":1349,"lang":"zh-CN","tag":"VFX","weight":111},{"id":5,"lang":"zh-CN","tag":"动漫","weight":85}]}]
     * error : {"errorCode":0,"errorMsg":"成功"}
     * result : 0
     * timestamp : 1521635759801
     */

    private ErrorBean error;
    private int result;
    private long timestamp;
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "DynamicDataBean{" +
                "error=" + error +
                ", result=" + result +
                ", timestamp=" + timestamp +
                ", data=" + data +
                '}';
    }

    public static class ErrorBean implements Serializable {
        /**
         * errorCode : 0
         * errorMsg : 成功
         */

        private int errorCode;
        private String errorMsg;

        @Override
        public String toString() {
            return "ErrorBean{" +
                    "errorCode=" + errorCode +
                    ", errorMsg='" + errorMsg + '\'' +
                    '}';
        }

        public static ErrorBean objectFromData(String str) {

            return new Gson().fromJson(str, ErrorBean.class);
        }

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

    public static class DataBean implements Serializable {
        /**
         * id : 707
         * key : my_MTZfZTUyY2M5OTg3MDk2YTBkNjcxY2QzZDcwMWRkZDE1N2EubXA0
         * cate : Undefine
         * preview : http://vwall-cdn.jidiangame.cn/live_wallpaper/preview/my_MTZfZTUyY2M5OTg3MDk2YTBkNjcxY2QzZDcwMWRkZDE1N2EubXA0_preview.mp4
         * video : http://vwall-cdn.jidiangame.cn/live_wallpaper/hd/my_MTZfZTUyY2M5OTg3MDk2YTBkNjcxY2QzZDcwMWRkZDE1N2EubXA0.mp4
         * thumb : http://vwall-cdn.jidiangame.cn/live_wallpaper/thumb/my_MTZfZTUyY2M5OTg3MDk2YTBkNjcxY2QzZDcwMWRkZDE1N2EubXA0.jpg@!thumb
         * title :
         * label :
         * desc :
         * size : 13713452
         * pixels : 720x1280
         * downloads : 0
         * duration : 27480
         * hasAudio : 1
         * visible : 1
         * legal : 1
         * score : 0
         * preview2 : http://vwall-cdn.jidiangame.cn/live_wallpaper/preview_m3u8/my_MTZfZTUyY2M5OTg3MDk2YTBkNjcxY2QzZDcwMWRkZDE1N2EubXA0_preview.m3u8
         * likecount : 0
         * hotValue : 0
         * preId : 4431
         * countryCode : default
         * tags : [{"id":1411,"lang":"zh-CN","tag":"梦幻汽车","weight":8},{"id":1384,"lang":"zh-CN","tag":"超级跑车","weight":3},{"id":1386,"lang":"zh-CN","tag":"豪华车","weight":7}]
         * type : facebook
         * size : 6591157
         * lega l : 1
         */

        private int id;
        private String key;
        private String cate;
        private String preview;
        private String video;
        private String thumb;
        private String title;
        private String label;
        private String desc;
        private String pixels;
        private int downloads;
        private int duration;
        private int hasAudio;
        private int visible;
        private int legal;
        private int score;
        private String preview2;
        private int likecount;
        private int hotValue;
        private int preId;
        private String countryCode;
        private String type;
        private int size;
        @com.google.gson.annotations.SerializedName("lega l")
        private int _$LegaL23; // FIXME check this code
        private List<TagsBean> tags;
        private int imageType = 0;
        private boolean whetherOrNotToPay;


        public boolean isWhetherOrNotToPay() {
            return whetherOrNotToPay;
        }

        public void setWhetherOrNotToPay(boolean whetherOrNotToPay) {
            this.whetherOrNotToPay = whetherOrNotToPay;
        }

        public int getImageType() {
            return imageType;
        }

        public void setImageType(int imageType) {
            this.imageType = imageType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getCate() {
            return cate;
        }

        public void setCate(String cate) {
            this.cate = cate;
        }

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getPixels() {
            return pixels;
        }

        public void setPixels(String pixels) {
            this.pixels = pixels;
        }

        public int getDownloads() {
            return downloads;
        }

        public void setDownloads(int downloads) {
            this.downloads = downloads;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getHasAudio() {
            return hasAudio;
        }

        public void setHasAudio(int hasAudio) {
            this.hasAudio = hasAudio;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getLegal() {
            return legal;
        }

        public void setLegal(int legal) {
            this.legal = legal;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getPreview2() {
            return preview2;
        }

        public void setPreview2(String preview2) {
            this.preview2 = preview2;
        }

        public int getLikecount() {
            return likecount;
        }

        public void setLikecount(int likecount) {
            this.likecount = likecount;
        }

        public int getHotValue() {
            return hotValue;
        }

        public void setHotValue(int hotValue) {
            this.hotValue = hotValue;
        }

        public int getPreId() {
            return preId;
        }

        public void setPreId(int preId) {
            this.preId = preId;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", key='" + key + '\'' +
                    ", cate='" + cate + '\'' +
                    ", preview='" + preview + '\'' +
                    ", video='" + video + '\'' +
                    ", thumb='" + thumb + '\'' +
                    ", title='" + title + '\'' +
                    ", label='" + label + '\'' +
                    ", desc='" + desc + '\'' +
                    ", pixels='" + pixels + '\'' +
                    ", downloads=" + downloads +
                    ", duration=" + duration +
                    ", hasAudio=" + hasAudio +
                    ", visible=" + visible +
                    ", legal=" + legal +
                    ", score=" + score +
                    ", preview2='" + preview2 + '\'' +
                    ", likecount=" + likecount +
                    ", hotValue=" + hotValue +
                    ", preId=" + preId +
                    ", countryCode='" + countryCode + '\'' +
                    ", type='" + type + '\'' +
                    ", size=" + size +
                    ", _$LegaL23=" + _$LegaL23 +
                    ", tags=" + tags +
                    '}';
        }

        public static class TagsBean implements Serializable {
            /**
             * id : 1411
             * lang : zh-CN
             * tag : 梦幻汽车
             * weight : 8
             */

            private int id;
            private String lang;
            private String tag;
            private int weight;


            @Override
            public String toString() {
                return "TagsBean{" +
                        "id=" + id +
                        ", lang='" + lang + '\'' +
                        ", tag='" + tag + '\'' +
                        ", weight=" + weight +
                        '}';
            }

            public static TagsBean objectFromData(String str) {

                return new Gson().fromJson(str, TagsBean.class);
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLang() {
                return lang;
            }

            public void setLang(String lang) {
                this.lang = lang;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public int getWeight() {
                return weight;
            }

            public void setWeight(int weight) {
                this.weight = weight;
            }
        }
    }
}
