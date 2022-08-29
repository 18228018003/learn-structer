package com.mengtu.designpattern.principle.demeter;

public class MainTest {
    public static void main(String[] args) {
        Agent agent = new Agent();
        Star star = new Star("煞笔");
        agent.setStar(star);
        Fans fans = new Fans("脑残1号");
        agent.setFans(fans);
        Company company = new Company("煞笔公司");
        agent.setCompany(company);

        agent.meeting();
        agent.business();
    }
}
