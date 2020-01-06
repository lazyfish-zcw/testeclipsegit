package com.shengsiyuan.netty.utils;

import java.io.File;

public class OtherUtils {

    public static void main(String[] args) {
        File files = new File("F:\\学习视频\\Netty零基础到精通-张龙\\【编程开发】2018年最新深入netty 零基础到精通 张龙老师（完结）");
        for (File file : files.listFiles()) {
//            System.out.println(file.getName());
            String newName = file.getName().substring(file.getName().indexOf(".") + 1, file.getName().lastIndexOf("(Av")) + ".mp4";
            System.out.println(newName);
            file.renameTo(new File("F:\\学习视频\\Netty零基础到精通-张龙\\【编程开发】2018年最新深入netty 零基础到精通 张龙老师（完结）\\" + newName));
        }
    }
}
