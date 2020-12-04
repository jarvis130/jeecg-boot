package org.jeecg.modules.order.controller;

import org.jeecg.modules.order.entity.GoodsVo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Copyright (C), 2019-2020, XXX有限公司
 * FileName: Main
 * Author:   kehaojian
 * Date:     2020/12/3 10:28
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public class Main {
    public static void main(String[] args) {
        List<GoodsVo> goodsVos = new ArrayList<>();
        GoodsVo goodsVo = new GoodsVo();
        goodsVo.setGoodsId("a111");
        goodsVo.setGoodsCount(3);
        goodsVos.add(goodsVo);
        GoodsVo goodsVo1 = new GoodsVo();
        goodsVo1.setGoodsId("c222");
        goodsVo1.setGoodsCount(34);
        goodsVos.add(goodsVo1);
        GoodsVo goodsVo2 = new GoodsVo();
        goodsVo2.setGoodsId("b232");
        goodsVo2.setGoodsCount(4);
        goodsVos.add(goodsVo2);
//        goodsVos.sort(Comparator.comparing(GoodsVo::getGoodsId).reversed());
//        System.out.println(goodsVos);
//        List<String> goodsIds = goodsVos.stream().map(GoodsVo::getGoodsId).collect(Collectors.toList());
//        System.out.println(goodsIds);
//        System.out.println(goodsVos);
//        int sum = goodsVos.stream().mapToInt(GoodsVo::getGoodsCount).sum();
//        System.out.println(sum);
        List<String> output = goodsVos.stream().map(GoodsVo::getGoodsId).map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(output);
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        Stream<Integer> outputStream = inputStream.
                flatMap((childList) -> childList.stream());
        System.out.println(outputStream.collect(Collectors.toList()));
        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
        Integer[] evens =
                Stream.of(sixNums).filter(n -> n%2 == 0).toArray(Integer[]::new);
        System.out.println(evens);
        List list = Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());
        System.out.println(list);
    }
}