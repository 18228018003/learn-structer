package com.mengtu.designpattern.principle.dependencyreverse;
//依赖倒置原则
public class Computer {

   private CPU cpu;
   private Memory memory;
   private HardDisk disk;

   public void run(){
       System.out.println("数据是 :"+disk.get());
       cpu.run();
       memory.save();
   }
    public CPU getCpu() {
        return cpu;
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public HardDisk getDisk() {
        return disk;
    }

    public void setDisk(HardDisk disk) {
        this.disk = disk;
    }
}
