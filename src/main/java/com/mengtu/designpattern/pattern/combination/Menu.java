package com.mengtu.designpattern.pattern.combination;

import java.util.LinkedList;
import java.util.List;

/**
 * 菜单类 属于树枝节点
 */
public class Menu extends MenuComponent{

    //菜单可以有多个子菜单或者子菜单项
    private List<MenuComponent> menuComponentList = new LinkedList<>();

    public Menu(String name,int level){
        this.name = name;
        this.level = level;
    }
    @Override
    public void add(MenuComponent menuComponent) {
        menuComponentList.add(menuComponent);
    }

    @Override
    public void remove(MenuComponent menuComponent) {
        menuComponentList.remove(menuComponent);
    }

    @Override
    public MenuComponent getChild(int index) {
        return menuComponentList.get(index);
    }

    @Override
    public void print() {
        for (int i = 0; i < level; i++) {
            System.out.print("--");
        }
        //打印菜单名称
        System.out.println(name);
        //打印子菜单或者子菜单项名称
        for (MenuComponent menuComponent : menuComponentList) {
            menuComponent.print();
        }
    }
}
