package cn.cast.pattern.offon;

/**
 * description
 *
 * @author 周德永
 * @date 2022/3/6 21:52
 */
public class SougouInput {

    private AbstractSkin skin;

    public void setSkin(AbstractSkin skin) {
        this.skin = skin;
    }

    public void display()
    {
        skin.display();
    }
}
