package com.openclassrooms.safetynet.entity;


import java.util.Objects;

public record Firestation(String address, int station) implements Comparable<Firestation>
{

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Firestation)) return false;
        Firestation that = (Firestation) o;
        return station == that.station && address.equals(that.address);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(station);
    }

    @Override
    public int compareTo(Firestation o)
    {
        return station - o.station();
    }
}