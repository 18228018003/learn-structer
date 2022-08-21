package com.zdy.learn.proxy;

/**
 *  代理类  动态代理 静态代理
 * @author 周德永
 * @date 2021/11/7 15:01
 */

interface ClothFactory{
    void produceCloth();
}
/*代理类*/
class ProxyClothFactory implements ClothFactory{

    private ClothFactory factory;

    public ProxyClothFactory(ClothFactory factory){
        this.factory = factory;
    }

    @Override
    public void produceCloth() {
        System.out.println("代理工厂做一些准备工作");
        factory.produceCloth();
        System.out.println("代理工厂做一些后续收尾工作");
    }

}
/*被代理类*/
class NikeClothFactory implements ClothFactory{

    @Override
    public void produceCloth() {
        System.out.println("NIKE 工厂生产一批衣服");
    }
}

public class StaticProxyText {

    public static void main(String[] args) {
        /*被代理类对象*/
        NikeClothFactory factory = new NikeClothFactory();
        /*代理类对象*/
        ProxyClothFactory proxyClothFactory = new ProxyClothFactory(factory);

        proxyClothFactory.produceCloth();
    }
}
