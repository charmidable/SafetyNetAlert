package com.openclassrooms.safetynet.entity;


public record Firestation(Integer station, String address)
{

    // ======================================
    // =           Object Methods           =
    // ======================================

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
        return station;
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