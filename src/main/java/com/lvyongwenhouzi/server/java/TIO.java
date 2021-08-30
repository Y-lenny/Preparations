package com.lvyongwenhouzi.server.java;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * IO异常会导致线程资源无法释放 ?
 */
public class TIO {

    public static Executor executor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        executor.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                extracted();
            }
        });
    }

    private static void extracted() throws IOException {

        FileReader fis = new FileReader(TIO.class.getClassLoader().getResource("IO.txt").getFile());
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(">>>>>>>>>>>>" + line);
        }
    }

}