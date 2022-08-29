package com.mengtu.designpattern.principle.dependencyreverse;

public class ComputerTest {
    public static void main(String[] args) {
        Computer computer = new Computer();
        HardDisk disk = new XiJieHardDisk();
        CPU cpu = new InterCPU();
        Memory memory = new KingstonMemory();

        computer.setCpu(cpu);
        computer.setDisk(disk);
        computer.setMemory(memory);

        computer.run();
    }
}
