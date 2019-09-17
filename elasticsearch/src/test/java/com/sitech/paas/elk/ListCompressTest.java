package com.sitech.paas.elk;

import org.junit.Test;

import java.util.*;

/**
 *
 * @author liwei_paas
 * @date 2019/9/10
 */
public class ListCompressTest {

    @Test
    public void test02(){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("c");
        list.add("c");
        list.add("d");
        list.add("e");
        System.out.println(new Compress(list).compress());
    }

    @Test
    public void test01(){
        Random random = new Random();
        List<String> list = new ArrayList<>();
        int a = random.nextInt(10000000)+1000000;
        int b = random.nextInt(10000000)+1000000;
        int c = random.nextInt(1000000)+1000000;
        int d = random.nextInt(10000000)+1000000;
        System.out.println(a+","+b+","+c+","+d);
        for (int i = 0; i < b; i++) {
            list.add("b");
        }
        for (int i = 0; i < a; i++) {
            list.add("a");
        }

        for (int i = 0; i < c; i++) {
            list.add("c");
        }
        for (int i = 0; i < d; i++) {
            list.add("d");
        }

        long c1 = System.currentTimeMillis();
        System.out.println(new Compress(list).compress());
        long c2 = System.currentTimeMillis();


        System.out.println(new Iterator(list).iterator());
        long c3 = System.currentTimeMillis();

        System.out.println((c2-c1));
        System.out.println((c3-c2));
    }

    class Iterator{
        List<String> list;
        Map<String,Integer> map;

        public Iterator(List<String> list) {
            this.list = list;
            map = new HashMap<>();
        }

        public Map<String,Integer> iterator(){
            for (String s : list){
                if (map.containsKey(s)){
                    map.put(s,map.get(s)+1);
                }else{
                    map.put(s,1);
                }
            }
            return map;
        }
    }


    class Compress{
        List<String> list;
        Map<String,Integer> map;

        int size ;

        public Compress(List<String> list) {
            this.list = list;
            this.map = new HashMap<>();
            this.size = list.size();
        }

        public Map<String,Integer>  compress(){
            compress(0,size);
            return map;
        }

        private int compress(int start ,int end){
            if (start==end){
                return size;
            }
            String currentKey = list.get(start);
            if (!map.containsKey(currentKey)){
                map.put(currentKey,0);
            }
            //如果end的值还是等于current,说明start-end这么多个都是同样的
            if (list.get(end-1).equals(currentKey)){
                Integer cSize = map.get(currentKey);
                map.put(currentKey,cSize+(end-start));
                //继续扩大end
                int nextEnd = (size+end+1)/2;
                return compress(end,nextEnd);
            }
            //不然就继续缩小end
            int nextEnd = (end+start+1)/2;
            return compress(start,nextEnd);
        }
    }
}
