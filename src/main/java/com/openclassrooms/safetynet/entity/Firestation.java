package com.openclassrooms.safetynet.entity;


import java.util.Comparator;
import java.util.Objects;

public record Firestation(Integer station, String address) implements Comparable<Firestation>
{

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Firestation)) return false;
        Firestation that = (Firestation) o;
        return station.equals(that.station) && address.equals(that.address);
    }


    @Override
    public int hashCode()
    {
        return Objects.hash(station, address);
    }


    @Override
    public int compareTo(Firestation o)
    {
        return Comparator.comparing(Firestation::station)
                         .thenComparing(Firestation::address)
                         .compare(this, o);
    }


    @Override
    public String toString()
    {
        return "Firestation{" +
                "station=" + station +
                ", address='" + address + '\'' +
                '}';
    }

}