package pz_project_restapi.temperature.service;

import java.util.*;
import pz_project_restapi.temperature.model.*;

public interface IItemService {

    public List<Item> getPeriodByTemperature(TemperatureForm newLimits);


}
