package com.openclassrooms.safetynet.dao;

import java.util.List;
import java.util.Map;


public class MapListComparison
{
    static<E> boolean areListAndMapOfEntityAreEqual(List<E> list, Map<Integer, E> map)
    {
        boolean b = true;

        for (E e : list)
        {
            b =  (map.containsKey(e.hashCode()))  && (e.equals(map.get(e.hashCode())));
        }

        return b;
    }


    static<E> boolean areListAndMapOfListOfEntityAreEqual(List<E> list, Map<Integer, List<E>> map)
    {
        boolean b = true;

        for (E e : list)
        {
            b =  (map.containsKey(e.hashCode()));
        }

        List<Integer> keys = list.stream().map(E::hashCode).distinct().toList();


        for(int i: keys)
        {
            b = b & (map.get(i).equals(list.stream().filter(e -> e.hashCode() == i).toList()));
        }

        return b;
    }
}