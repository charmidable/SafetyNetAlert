package com.openclassrooms.safetynet.dao;

import static   java.util.stream.Collectors.toSet;
import          java.util.HashSet;
import          java.util.List;
import          java.util.Map;


public class MapListComparison
{
    static <E> boolean areListAndMapOfEntityEqual(List<E> list, Map<Integer, E> map)
    {
        boolean b = true;

        for (E e : list)
        {
            b =  b & (map.containsKey(e.hashCode()))  && (e.equals(map.get(e.hashCode())));
        }

        b = b & new HashSet<>(map.values()).equals(new HashSet<>(list));

        return b;
    }


    static <E> boolean areListAndMapOfListOfEntityEqual(List<E> list, Map<Integer, List<E>> map)
    {
        boolean b = true;

        for (E e : list)
        {
            b = b & (map.containsKey(e.hashCode()));
        }

        List<Integer> keys = list.stream().map(E::hashCode).distinct().toList();


        for(int key: keys)
        {
            b = b & (map.get(key).equals(list.stream().filter(e -> e.hashCode() == key).toList()));
        }

        b = b & map.values().stream().flatMap(x -> x.stream()).collect(toSet()).equals(new HashSet<>(list));

        return b;
    }
}