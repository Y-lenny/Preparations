package com.lvyongwenhouzi.server.java.io;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * IO异常会导致线程资源无法释放 ?
 */
public class TIOException {

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

        FileReader fis = new FileReader(TIOException.class.getClassLoader().getResource("IO.txt").getFile());
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(">>>>>>>>>>>>" + line);
        }
    }

}
