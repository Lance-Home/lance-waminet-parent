

package com.waminet.common.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * MyBatis Plus 代码生成器
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public class CodeAutoGenerator {

    // 作者
    private static final String AUTHOR = "Lance";
    // 表名前缀
    private static final String[] TABLEPREFIX = {"sys_"};
    // 数据库连接URL
    private static final String URL = "jdbc:mysql://192.168.88.203:3306/513_sixth?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
    // 数据库驱动
    private static final String DRIVERNAME = "com.mysql.jdbc.Driver";
    // 用户名
    private static final String USERNAME = "root";
    // 密码
    private static final String PASSWORD = "root";
    // 表名
    private static final String[] TABLENAMES = {"sys_user", "sys_role", "sys_menu", "sys_dept", "sys_dict", "sys_log", "sys_user_role", "sys_role_menu", "sys_dept_user"};
    // 包名称
    private static final String PACKAGENAME = "com.waminet";

    public static void main(String[] args) {

        AutoGenerator mpg = new AutoGenerator();

        /**
         * 全局变量配置
         */
        GlobalConfig gc = new GlobalConfig();
        // 当前项目
        final String projectPath = System.getProperty("user.dir") + "/badger-waybill-commons/badger-waybill-commons-mybatis-plus-generator";
        // 输出路径
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setFileOverride(true);
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);
        gc.setSwagger2(true);
        gc.setBaseResultMap(true);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setBaseColumnList(true);

        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sDao");
        gc.setXmlName("%sDao");
        gc.setEntityName("%sEntity");
        mpg.setGlobalConfig(gc);

        /**
         * 数据源配置
         */
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName(DRIVERNAME);
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASSWORD);
        dsc.setUrl(URL);
        mpg.setDataSource(dsc);

        /**
         * 数据库表配置
         */
        StrategyConfig strategy = new StrategyConfig();

        strategy.setTablePrefix(TABLEPREFIX)
                .setRestControllerStyle(true)
                .setEntityLombokModel(true);

        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setCapitalMode(true);
        strategy.setInclude(TABLENAMES);
        mpg.setStrategy(strategy);

        /**
         * 包配置
         */
        PackageConfig pc = new PackageConfig();
        pc.setParent(PACKAGENAME);
        pc.setModuleName("");
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("dao");
        pc.setXml("mapper");
        pc.setEntity("entity");
        mpg.setPackageInfo(pc);

        /**
         * 自定义配置
         */
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };

        /**
         * 将xml生成到resource下面
         */
        String templatePath = "/templates/mapper.xml.ftl";

        // 自定义输出配置
        List focList = new ArrayList();

        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper" + "/" + tableInfo.getXmlName() + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        /**
         * 配置模板
         */
        TemplateConfig templateConfig = new TemplateConfig();

        templateConfig.setController("templates/controller.java");
        templateConfig.setService("templates/service.java");
        templateConfig.setServiceImpl("templates/serviceImpl.java");
        templateConfig.setMapper("templates/dao.java");
        templateConfig.setXml(null);
        templateConfig.setEntity("templates/entity.java");
        mpg.setTemplate(templateConfig);

        /**
         * 模板引擎
         */
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        mpg.execute();
    }

}
