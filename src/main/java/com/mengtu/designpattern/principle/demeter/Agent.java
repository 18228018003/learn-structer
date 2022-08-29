package com.mengtu.designpattern.principle.demeter;

public class Agent {
    private Star star;
    private Fans fans;
    private Company company;


    public void meeting(){
        System.out.println(star.getName() + "和粉丝" + fans.getName());
    }

    public void business(){
        System.out.println(star.getName() + "和" + company.getName() +" 谈合作");
    }

    public Star getStar() {
        return star;
    }

    public void setStar(Star star) {
        this.star = star;
    }

    public Fans getFans() {
        return fans;
    }

    public void setFans(Fans fans) {
        this.fans = fans;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
