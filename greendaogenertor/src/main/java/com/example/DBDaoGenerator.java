package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DBDaoGenerator {

    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.linky.heaven.dao.orm");
        schema.enableKeepSectionsByDefault();
        schema.enableActiveEntitiesByDefault();

        addMovieScheme(schema);

        try {
            new DaoGenerator().generateAll(schema, "app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 创建电影数据库
    private static void addMovieScheme(Schema schema) {
        Entity entity = schema.addEntity("MovieBean");
        entity.setTableName("movie");
        entity.addIdProperty();
        entity.addIntProperty("type");                  // 电影类型
        entity.addStringProperty("name").notNull();
        entity.addDateProperty("date");
        entity.addStringProperty("parse_page_url");     // 解析电影 URL
        entity.addStringProperty("download_ftp");       // 下载电影 FTP 地址
    }
}
