package com.learn.woniu;
/*产品*/
class Product{
    public Integer no;
    public String name;
    public int stock;

    public Integer getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
/*仓库*/
public class Storage {
    private Product[] products = new Product[10];
    private int size;
    public void inStore(Product product){
        // 先找仓库中是否存在编号一样的产品
        boolean flag = false;
        for (int i = 0; i < 10; i++) {
            if (product.no == products[i].no){
                flag = true;
                return;
            }
        }
        if (flag && size < 10){
            products[size++] = product;
        }
    }
}
 class Storage1 {
    private Product[] products = new Product[10]; // 存储产品

    private int size;// 产品数，技巧

    /**
     * 入库
     *
     * @param product
     */
    public void in(Product product) {
        // 先找仓库中是否存在编号一样的产品
//  boolean b = true;
        for (int i = 0; i < size; i++) {
            if (products[i].getNo().equals(product.getNo())) {
                products[i].setStock(products[i].getStock() + product.getStock()); // 更新库存
                return;
            }
        }
//  if (b) { // 没找到
        if (size == products.length) {
            System.out.println("已满");
            return;
        } else {
            products[size++] = product;// 增加新的产品，一定自增
            // size++;
        }
//  }
        // 有：直接增加产品的库存
        // 没有：先判断仓库是否已满，如果满，加不进去；没有满直接加到数组中
    }
}
