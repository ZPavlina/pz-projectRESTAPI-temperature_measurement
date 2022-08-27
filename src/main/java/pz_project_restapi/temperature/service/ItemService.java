package pz_project_restapi.temperature.service;

import java.time.*;
import java.util.*;
import org.springframework.stereotype.*;
import lombok.*;
import pz_project_restapi.temperature.model.*;
import pz_project_restapi.temperature.repository.*;

@Component
public class ItemService implements IItemService {

    /**
     * Instance of class Itemrepository
     */
    private ItemRepository itemRepository;

    /**
     * @param itemRepository
     */
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * Find all data from database, then sorted by date and time.
     * Convert to binary list to find, which of them are in limits.
     * Find longest sequence from founded periods.
     *
     * @param newLimits of Temperature A and B
     * @return List of two items - initial and last items of the longest sequence
     * bounded by temperature A and B
     */
    public List<Item> getPeriodByTemperature(TemperatureForm newLimits) {
        List<Item> input = itemRepository.findAll();
        sortLocalDateTime(input);
        float A = newLimits.getTemperatureA();
        float B = newLimits.getTemperatureB();
        List<Integer> values = convertToBinaryListFromTe(input, A, B);
        List<Tuple> seqs = ItemService.longestEqualSeq(values);
        return oneLongestSequence(seqs, input);
    }

    /**
     *  Find all data from database, then sorted by date and time.
     *  Convert to binary list to find, which of them are in limits.
     *  Find longest sequence from founded periods.
     * @param newLimits of Temperature A and B, Time X and Y
     * @return List of two items - initial and last items of the longest sequence
     *  bounded by temperature A and B and time X and Y
     */
    public List<Item> getPeriodByTemperatureAndTime(TemperatureForm newLimits) {
        List<Item> input = itemRepository.findAll();
        sortLocalDateTime(input);
        float A = newLimits.getTemperatureA();
        float B = newLimits.getTemperatureB();
        LocalTime X = newLimits.getTimeX();
        LocalTime Y = newLimits.getTimeY();

        List<Integer> values = convertToBinaryListFromTeTi(input, A, B, X, Y);
        List<Tuple> seqs = ItemService.longestEqualSeq(values);
        return oneLongestSequence(seqs, input);
}

    /**
     * Sort List of items comparing by date and time
     *
     * @param item
     * @return sorted list of items
     */
    private static List<Item> sortLocalDateTime(List<Item> item) {
        item.sort(Comparator.comparing(Item::getDateAndTime));
        return item;
    }

    /**
     * Method for convert list of items to list of integers 0 and 1. The parameter for assigning value
     * of 0 or 1 is given to the limits of temperature (A and B).
     * @param item
     * @param temperatureA
     * @param temperatureB
     * @return list of Integer with values 0 and 1
     */
    private static List<Integer> convertToBinaryListFromTe(List<Item> item, float temperatureA,
                                                           float temperatureB) {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < item.size(); i++) {
            if (((item.get(i).getTemperature() >= temperatureA) &&
                    (item.get(i).getTemperature() <= temperatureB))) {
                values.add(1);
            } else {
                values.add(0);
            }
        }
        return values;
    }

    /**
     * Method for convert list of items to list of integers 0 and 1. The parameter for assigning value
     * of 0 or 1 is given to the limits of temperature (A and B) and time (X and Y).
     * @param item
     * @param temperatureA
     * @param temperatureB
     * @param timeX
     * @param timeY
     * @return list of Integer with values 0 and 1
     */
    private static List<Integer> convertToBinaryListFromTeTi(List<Item> item, float temperatureA,
                                                             float temperatureB, LocalTime timeX,
                                                             LocalTime timeY) {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < item.size(); i++) {
            float tempTemperature = item.get(i).getTemperature();
            LocalDateTime dateTime = item.get(i).getDateAndTime();
            LocalTime time = dateTime.toLocalTime();
            if (((tempTemperature >= temperatureA) && (tempTemperature <= temperatureB)) &&
                    ((time.equals(timeX) || time.isAfter(timeX)) &&
                            ((time.equals(timeY) || time.isBefore(timeY))))) {
                values.add(1);
            } else {
                values.add(0);
            }
        }   return values;
    }

    /**
     * Method to get the initial and last items of the longest sequence
     * @param tuples
     * @param items
     * @return List of two items - initial and last items of the longest sequence
     */
    public static List<Item> oneLongestSequence(List<Tuple> tuples, List<Item> items){
        int theCount = tuples.get(0).getLength();
        int theIdx = tuples.get(0).getStart();

        List<Item> finalPeriod = new ArrayList<>();
        for (int i = theIdx; i < (theCount + theIdx); i++) {
            finalPeriod.add(new Item(items.get(i).getId(),items.get(i).getDateAndTime(),
                    items.get(i).getTemperature()));
        }
        List<Item> startEndPeriod = new ArrayList<>();
        startEndPeriod.add(finalPeriod.get(0));
        startEndPeriod.add(finalPeriod.get(finalPeriod.size() - 1));
        return startEndPeriod;

    }

    /**
     * Method for finding longest sequence from periods, that were found by temperature limits.
     * @param values
     * @return List of Tuple, where is two values - index where sequence begin; size of sequence.
     */
    private static List<Tuple> longestEqualSeq(List<Integer> values) {
        int theCount = 0;
        int theIdx = 0;
        int count = 1;
        List<Tuple> out = new ArrayList<Tuple>();
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i - 1).equals(1) && (values.get(i).equals(1))) {
                count++;
                if (theCount < count) {
                    theCount = count;
                    theIdx = i;
                }
            } else {
                if (count > 1) {
                    out.add(new Tuple(theIdx - (theCount - 1), theCount));
                }
                count = 1;
                theCount = 0;
            }
        }
        if (count > 1) {
            out.add(new Tuple(theIdx - (theCount - 1), theCount));
        }
        out.sort(Comparator.comparing(Tuple::getLength).reversed());
        return out;
    }
}

