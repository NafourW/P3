package dk.aau.cs.ds308e18.function.tourgen;

/*
This class is used to store the chosen settings
for tour generation. It is passed to the tour generator
from GeneratorProgressController, which gets it from TourGeneratorController
*/
public class TourGeneratorSettings {
    public enum planningMethod{
        leastTime,
        shortestDistance,
        mostEconomic
    }

    public planningMethod method;
    public String region;
    public String date;
    public int workTime;
    public int breakTime;
    public boolean forceOrdersOnTour;
}
