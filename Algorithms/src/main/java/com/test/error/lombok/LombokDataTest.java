package com.test.error.lombok;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.alibaba.fastjson.serializer.SerializerFeature.DisableCircularReferenceDetect;
import static java.util.stream.Collectors.toMap;


/**
 *
 * @see lombok.Data 重写了equals和hashCode方法，
 * 导致实体中如果包含集合类，且集合中元素不一致时，其equals和hashCode不等，
 * 最终导致{@link Set}无法正确去重
 *
 * @author zhangbo
 * @date 2019-08-13
 */
public class LombokDataTest {

    public static void main(String[] args) {

        List<Region> regionList = init();

        Map<Integer, Region> regionIdMap = regionList.stream().collect(toMap(Region::getCode, (region) -> {
            return region;
        }));

        Set<Region> regionCascade = new HashSet<>();

        regionIdMap.forEach((regionId, region) -> {
            if (region.getParentCode() == 0) {
                // 第一级
                regionCascade.add(region);
            } else {
                Integer parentCode = region.getParentCode();
                Region parent = regionIdMap.get(parentCode);

                if (parent.getParentCode() == 0) {
                    // 第二级
                    parent.getChildren().add(region);
                    regionCascade.add(parent);
                } else {
                    // 第三级
                    Integer pParentCode = parent.getParentCode();
                    Region pParent = regionIdMap.get(pParentCode);

                    parent.getChildren().add(region);
                    pParent.getChildren().add(parent);
                    regionCascade.add(pParent);
                }
            }

        });

        System.out.println(JSON.toJSONString(regionCascade,DisableCircularReferenceDetect));

    }

    public static List<Region> init() {
        List<Region> list = new ArrayList<>();
        list.add(new Region(510000, "四川省", 0));
        list.add(new Region(510100, "成都市", 510000));

        list.add(new Region(510101, "青羊区", 510100));
        list.add(new Region(510102, "武侯区", 510100));

//        list.add(new Region(510000, "四川省", 0));

        return list;
    }

}
