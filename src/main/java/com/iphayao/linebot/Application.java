package com.iphayao.linebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class Application {
    static Path downloadedContentDir;

    public static void main(String[] args) throws IOException {
        downloadedContentDir = FileSystems.getDefault().getPath("/home/nick/File_Line/");
        SpringApplication.run(Application.class, args);
    }
}
