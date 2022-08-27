package pz_project_restapi.temperature.service;

import java.util.*;
import pz_project_restapi.temperature.model.*;

public interface IItemService {

    /**
     * GET longest period in days between temperature A and B
     * @param newLimits
     * @return List of two items - initial and last items of the longest sequence
     *  bounded by temperature A and B
     */
    public List<Item> getPeriodByTemperature(TemperatureForm newLimits);

    /**
     * GET longest period in days between temperature A and B, and time X and Y
     * @param newLimits
     * @return List of two items - initial and last items of the longest sequence
     *  bounded by temperature A and B and time X and Y
     */
    public List<Item> getPeriodByTemperatureAndTime(TemperatureForm newLimits);


}
