package cn.cast.unionfind;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/3 23:18
 */
public class UnionFindMY {
    /*样本进来会包一层 Element*/
    public static class Element<V>{
        public V value;

        public Element(V value){
            this.value = value;
        }
    }

    public static class UnionFindSet<V> {
        public HashMap<V,Element<V>> elementMap;
        /*key 某个元素  value 该元素的父元素*/
        public HashMap<Element<V>,Element<V>> fatherMap;
        /*key 某个集合的代表元素，value该集合的大小*/
        public HashMap<Element<V>,Integer> sizeMap;

        public UnionFindSet(List<V> list){
            elementMap = new HashMap<>();
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V value : list) {
                Element<V> element = new Element<>(value);
                elementMap.put(value,element);
                fatherMap.put(element,element);
                sizeMap.put(element,1);
            }
        }

        public boolean isSameSet(V a,V b){
            if (elementMap.containsKey(a) && elementMap.containsKey(b)){
                return findHead(elementMap.get(a)) == findHead(elementMap.get(b));
            }
            return false;
        }

        public void union(V a,V b){
            if (elementMap.containsKey(a) && elementMap.containsKey(b)){
                Element<V> af = findHead(elementMap.get(a));
                Element<V> bf = findHead(elementMap.get(b));
                if (af != bf){
                    Element<V> big = sizeMap.get(af) >= sizeMap.get(bf) ? af : bf;
                    Element<V> small = big == af ? bf : af;
                    fatherMap.put(small,big);
                    sizeMap.put(big,sizeMap.get(af)+sizeMap.get(bf));
                    sizeMap.remove(small);
                }
            }
        }

        private Element<V> findHead(Element<V> element) {
            Stack<Element<V>> stack = new Stack<>();
            while (element != fatherMap.get(element)){
                stack.push(element);
                element = fatherMap.get(element);
            }
            while (!stack.isEmpty()){
                fatherMap.put(stack.pop(),element);
            }
            return element;
        }
    }
}
