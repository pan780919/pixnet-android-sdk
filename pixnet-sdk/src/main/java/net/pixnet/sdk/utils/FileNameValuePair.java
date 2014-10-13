package net.pixnet.sdk.utils;

import java.io.File;

public class FileNameValuePair {

    private String name;
    private File file;

    public FileNameValuePair(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public File getValue() {
        return file;
    }
}

