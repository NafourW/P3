package dk.aau.cs.ds308e18.function.tourgen;

import com.graphhopper.util.shapes.GHPoint;
import dk.aau.cs.ds308e18.io.database.Database;
import dk.aau.cs.ds308e18.model.Order;
import dk.aau.cs.ds308e18.model.Tour;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

public class TourGeneratorTest {
    static Database dbSetup;

    @BeforeAll
    static void RefreshDatabaseBefore() throws IOException {
        dbSetup = new Database();
        dbSetup.reloadDatabase();

        Database.dbImport.importAll("resources/data");
    }

    @AfterAll
    static void RefreshDatabaseAfter() throws IOException {
        dbSetup.reloadDatabase();
    }

    @Test
    void TestToursHaveAtLeastMinimumOrders() {
        ArrayList<Order> orders = new ArrayList<>();

        TourGeneratorSettings settings = new TourGeneratorSettings();
        settings.method = TourGeneratorSettings.planningMethod.leastTime;

        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setDate(LocalDate.now());
            order.setLatLon(new GHPoint(57.0122539,9.9910615));
            order.setRegion("København");
            order.setTotalTime(100);
            orders.add(order);
        }

        TourGenerator tourGenerator = new TourGenerator();
        ArrayList<Tour> tours = tourGenerator.generateTours(orders, settings);

        for (Tour t : tours) {
            if (t.getOrders().size() < TourGenerator.MIN_ORDERS_PER_TOUR)
                fail("Tour size below minimum (" + t.getOrders().size() + "/" + TourGenerator.MIN_ORDERS_PER_TOUR + ")");
        }
    }

    @Test
    void TestOrdersPerTourDoesNotExceedTimeLimit() {
        ArrayList<Order> orders = new ArrayList<>();

        TourGeneratorSettings settings = new TourGeneratorSettings();
        settings.method = TourGeneratorSettings.planningMethod.leastTime;
        settings.workTime = 480;

        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setDate(LocalDate.now());
            order.setLatLon(new GHPoint(57.0122539,9.9910615));
            order.setRegion("København");
            order.setTotalTime(100);
            orders.add(order);
        }

        TourGenerator tourGenerator = new TourGenerator();
        ArrayList<Tour> tours = tourGenerator.generateTours(orders, settings);

        for (Tour t : tours) {
            if (t.getTourTime() > settings.workTime)
                fail("Tour time exceeds work time (" + t.getTourTime() + "/" + settings.workTime + ")");
        }
    }

    @Test
    void TestTourRegionsMatchOrderRegions() {
        ArrayList<Order> orders = new ArrayList<>();

        TourGeneratorSettings settings = new TourGeneratorSettings();
        settings.method = TourGeneratorSettings.planningMethod.leastTime;

        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setDate(LocalDate.now());
            order.setLatLon(new GHPoint(57.0122539,9.9910615));
            order.setRegion("København");
            order.setTotalTime(100);
            orders.add(order);
        }

        TourGenerator tourGenerator = new TourGenerator();
        ArrayList<Tour> tours = tourGenerator.generateTours(orders, settings);

        for (Tour t : tours) {
            for (Order o : t.getOrders()) {
                if (!o.getRegion().equals(t.getRegion()))
                    fail("Regions don't match: " + t.getRegion() + " - " + o.getRegion());
            }
        }
    }

    @Test
    void TestTourDatesMatchOrderDates() {
        ArrayList<Order> orders = new ArrayList<>();

        TourGeneratorSettings settings = new TourGeneratorSettings();
        settings.method = TourGeneratorSettings.planningMethod.leastTime;

        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setDate(LocalDate.now());
            order.setLatLon(new GHPoint(57.0122539,9.9910615));
            order.setRegion("København");
            order.setTotalTime(100);
            orders.add(order);
        }

        TourGenerator tourGenerator = new TourGenerator();
        ArrayList<Tour> tours = tourGenerator.generateTours(orders, settings);

        for (Tour t : tours) {
            for (Order o : t.getOrders()) {
                if (!o.getDate().equals(t.getTourDate()))
                    fail("Dates don't match: " + t.getTourDate() + " - " + o.getDate());
            }
        }
    }

    @Test
    void TestAllOrdersAssignedToTour() {
        ArrayList<Order> orders = new ArrayList<>();

        TourGeneratorSettings settings = new TourGeneratorSettings();
        settings.method = TourGeneratorSettings.planningMethod.leastTime;
        settings.forceOrdersOnTour = true;

        for (int i = 0; i < 100 + 1; i++) {
            Order order = new Order();
            order.setDate(LocalDate.now());
            order.setLatLon(new GHPoint(57.0122539,9.9910615));
            order.setRegion("København");
            order.setTotalTime(100);
            orders.add(order);
        }

        TourGenerator tourGenerator = new TourGenerator();
        ArrayList<Tour> tours = tourGenerator.generateTours(orders, settings);

        for (Order o : orders) {
            boolean hasBeenAssigned = false;
            for (Tour t : tours) {
                if (t.getOrders().contains(o)) {
                    hasBeenAssigned = true;
                }
            }
            if (!hasBeenAssigned)
                fail("Order has not been assigned to a tour");
        }
    }

    @Test
    void TestOrderOnlyAssignedToSingleTour() {
        TourGeneratorSettings settings = new TourGeneratorSettings();
        settings.method = TourGeneratorSettings.planningMethod.leastTime;

        ArrayList<Order> orders = Database.dbExport.exportUnassignedOrders();

        /*
        for (int i = 0; i < 100 + 1; i++) {
            Order order = new Order();
            order.setDate(LocalDate.now());
            order.setLatLon(new GHPoint(57.0122539,9.9910615));
            order.setRegion("København");
            order.setTotalTime(100);
            orders.add(order);
        }
        */

        System.out.println(orders.size());

        TourGenerator tourGenerator = new TourGenerator();
        ArrayList<Tour> tours = tourGenerator.generateTours(orders, settings);

        for (Tour t : tours) {
            if (t.getOrders().size() < 1)
                fail("fail army");
        }

        for (Order o : orders) {
            boolean foundInTour = false;
            for (Tour t : tours) {
                if (t.getOrders().contains(o)) {
                    if (foundInTour)
                        fail("Order was already found in different tour.");
                    else
                        foundInTour = true;
                }
            }
        }
    }

    @Test
    void firstOrderTest() {
        TourGenerator tourGenerator = new TourGenerator();
        Order orderAAB = new Order();
        Order orderSkagen = new Order();
        Order orderHJ = new Order();
        Order orderStovring = new Order();
        Tour orderList = new Tour();
        Tour correctList = new Tour();

        orderStovring.setAddress("Hobrovej 55");
        orderStovring.setZipCode(9530);
        orderAAB.setAddress("selma lagerløfs vej 300");
        orderAAB.setZipCode(9000);
        orderSkagen.setAddress("Hans ruths vej 1");
        orderSkagen.setZipCode(9990);
        orderHJ.setAddress("Kærparken 9");
        orderHJ.setZipCode(9800);

        orderAAB.requestLatLonFromAddress();
        orderHJ.requestLatLonFromAddress();
        orderSkagen.requestLatLonFromAddress();
        orderStovring.requestLatLonFromAddress();

        orderList.addOrder(orderSkagen);
        orderList.addOrder(orderAAB);
        orderList.addOrder(orderStovring);
        orderList.addOrder(orderHJ);


        correctList.addOrder(orderStovring);
        correctList.addOrder(orderAAB);
        correctList.addOrder(orderHJ);
        correctList.addOrder(orderSkagen);

        Assertions.assertEquals(correctList.getOrders().toString(), tourGenerator.firstOrder(orderList).getOrders().toString());
    }
}
