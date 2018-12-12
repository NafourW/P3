package dk.aau.cs.ds308e18.function.tourgen;

public class TourGeneratorSettings {
    public enum planningMethod{
        leastTime,
        shortestDistance,
        mostEconomic
    }

    public planningMethod method;
    public int workTime;
    public int breakTime;
    public boolean forceOrdersOnTour;
}
