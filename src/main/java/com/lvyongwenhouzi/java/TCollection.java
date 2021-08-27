package com.lvyongwenhouzi.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 为什么不要在 foreach 循环里进行元素的 remove/add 操作 ？
 * 普通for循环无法处理数组元素size变化导致循环得到的元素位置错误，所以iterator维护了游标和元素size变化
 * 游标也会动态变化的能力使得循环得到的元素位置正确、值正确。
 */
public class TCollection {


    public static void main(String[] args) {

        List<Integer> list = new ArrayList(6);

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        removeFor(list);
        removeIterator(list);

        Integer[] array = new Integer[]{1, 2, 3, 4, 5};
        // arrayFor(array);
    }


    /**
     * for 循环删除操作
     *
     * @param array
     */
    public static void arrayFor(Integer[] array) {

        // for(int var3 = 0; var3 < var2; ++var3)
        for (Integer i : array) {
            try {
                System.out.println(i);
            } catch (Exception e) {
                System.out.println("array for exception >>>>>>>>>>>>>>>" + e);
            }
        }
    }

    /**
     * for 循环删除操作
     * 遍历是通过迭代器，删除是通过arrayList.remove(Object o)
     *
     * @param list
     */
    public static void removeFor(List<Integer> list) {

        // Iterator var1 = list.iterator();
        // while(var1.hasNext())
        for (Integer i : list) {
            try {
                list.remove(i);
            } catch (Exception e) {
                System.out.println("remove for exception >>>>>>>>>>>>>>>" + e);
            }
        }
    }

    /**
     * iterator 循环删除操作
     * 遍历是通过迭代器，删除是通过iterator.remove()
     *
     * @param list
     */
    public static void removeIterator(List<Integer> list) {

        // Iterator var1 = list.iterator();
        // while(var1.hasNext())
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            try {
                System.out.println("iterator element >>>>>>>>>>>>>" + iterator.next());
                iterator.remove();
            } catch (Exception e) {
                System.out.println("remove iterator exception >>>>>>>>>>>>>>>" + e);
            }
        }
    }


}
