package com.test.hutool.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.annotation.ExcelProperty;
import com.books.jdbc.JdbcUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * @author zhangbo
 * @date 2020/8/19
 */
public class DistrictTest {

    public static void main(String[] args) throws Exception {

        // 基于国家统计局2020年5月数据 http://www.mca.gov.cn///article/sj/xzqh/2020/2020/2020072805001.html
//        InputStream inputStream = DistrictTest.class.getClassLoader().getResourceAsStream("行政区域.xlsx");
        InputStream inputStream = new FileInputStream("D:\\IdeaProjects\\github\\zb-demo\\Algorithms\\src\\main\\resources\\行政区域.xlsx");

//        ExcelReader excelReader = new ExcelReader(inputStream, 0, true);
//        List<Map<String, Object>> list = excelReader.readAll();
//        System.out.println(list.size());

        List<Region> originRegionList = EasyExcelFactory.read(inputStream).sheet(1).head(Region.class).doReadSync();
        List<Region> regionList = new ArrayList<>(originRegionList.size());

        System.out.println("共读取到区域：" + originRegionList.size());

        Region one = null;
        Region two = null;

        for (Region region : originRegionList) {
            if (region.getName().startsWith("   ")) {
                region.setLevel(3);
                region.setParentCode(two.getCode());
            } else if (region.getName().startsWith(" ")) {
                two = region;
                region.setLevel(2);
                region.setParentCode(one.getCode());
            } else {
                one = region;
                region.setLevel(1);
            }
            region.setName(region.getName().replaceAll(" ", ""));
            regionList.add(region);
            System.out.println(region);
        }
        System.out.println(regionList.size());

        // 保存数据库
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into t_region (code, name, parent_code, level) values (?, ?, ?, ?)");
        for (Region region : regionList) {
            preparedStatement.setString(1, region.code);
            preparedStatement.setString(2, region.name);
            preparedStatement.setString(3, StringUtils.trimToEmpty(region.parentCode));
            preparedStatement.setInt(4, region.level);
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();

//        process(originRegionList, regionList);

//        Map<String, List<Region>> one = originRegionList.stream().collect(groupingBy(region -> {
//            return region.getCode().substring(0, 2);
//        }));
//
//        List<Map<String, List<Region>>> twoList = new ArrayList<>();
//        for (List<Region> regions : one.values()) {
//            Map<String, List<Region>> two = regions.stream().collect(groupingBy(region -> {
//                return region.getCode().substring(0, 4);
//            }));
//            twoList.add(two);
//        }
//
//        System.out.println(twoList.size());
//
//        for (Map<String, List<Region>> map : twoList) {
//
//        }

    }

    private static void process(List<Region> originRegionList, List<Region> regionList) {
        List<Region> oneLevelList = originRegionList.stream().filter(region -> {
            return region.getCode().endsWith("0000");
        }).collect(Collectors.toList());

        Map<String, Region> oneLevelMap = oneLevelList.stream().collect(toMap(region -> {
            return region.getCode().substring(0, 2);
        }, region -> {
            return region;
        }));

        oneLevelList.forEach(region -> {
            region.setLevel(1);
        });


        List<Region> twoLevelList = originRegionList.stream().filter(region -> {
            return region.getCode().endsWith("00");
        }).collect(Collectors.toList());

        Map<String, Region> twoLevelMap = twoLevelList.stream().collect(toMap(region -> {
            return region.getCode().substring(0, 4);
        }, region -> {
            return region;
        }));

        originRegionList.removeAll(oneLevelList);
        originRegionList.removeAll(twoLevelList);

        for (Region two : twoLevelList) {
            two.setLevel(2);
            String subCode = two.getCode().substring(0, 2);
            Region one = oneLevelMap.get(subCode);
            two.setParentCode(one == null ? null : one.getCode());
        }

        for (Region three : originRegionList) {
            three.setLevel(3);
            String subCode = three.getCode().substring(0, 4);
            Region two = twoLevelMap.get(subCode);
            three.setParentCode(two == null ? null : two.getCode());
        }

        regionList.addAll(oneLevelList);
        regionList.addAll(twoLevelList);
        regionList.addAll(originRegionList);

        regionList.sort(Comparator.comparing(Region::getCode));

        for (Region region : regionList) {
            System.out.println(region);
        }

        System.out.println(regionList.size());
    }

    @Data
    public static class Region {
        @ExcelProperty(value = "行政区划代码")
        private String code;
        @ExcelProperty(value = "单位名称")
        private String name;
        private String parentCode;
        private int level;

        public void setCode(String code) {
            this.code = StringUtils.trimToEmpty(code).replaceAll(" ", "");
        }

        public void setName(String name) {
//            this.name = StringUtils.trimToEmpty(name).replaceAll(" ", "");
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Region region = (Region) o;
            return code.equals(region.code);
        }

        @Override
        public int hashCode() {
            return Objects.hash(code);
        }

    }

}
