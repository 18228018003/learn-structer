package cn.cast.pattern.offon;

/**
 * description
 *
 * @author 周德永
 * @date 2022/3/6 21:54
 */
public class Client {
    public static void main(String[] args) {
        //1。创建搜狗输入法皮肤
        SougouInput input = new SougouInput();
        /*2.创建皮肤对象*/
        DefaultSkin skin = new DefaultSkin();
        /*3.将皮肤设置到输入法中*/
        input.setSkin(skin);
        /*4.显示皮肤*/
        input.display();

    }
}
