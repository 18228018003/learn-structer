package com.mengtu.net.nio.test;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class TestWalkFileTree {
    @Test
    public void testWalkFileTree() throws IOException {
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("C:\\develop\\Go"),new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("===>"+dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir,attrs);
            };

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }
        });
        System.out.println("dir count: "+ dirCount);
        System.out.println("file count: "+ fileCount);
    }
    @Test
    public void testWalkFileTree1() throws IOException {
        AtomicInteger fileCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("C:\\develop\\Go"),new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".go")){
                    System.out.println(file);
                    fileCount.incrementAndGet();
                }
                return super.visitFile(file, attrs);
            }
        });
        System.out.println(fileCount);
    }

    @Test
    public void deleteFiles() throws IOException {
        Files.walkFileTree(Paths.get("C:\\develop\\oslearn - 副本"),new SimpleFileVisitor<Path>(){

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }

}
