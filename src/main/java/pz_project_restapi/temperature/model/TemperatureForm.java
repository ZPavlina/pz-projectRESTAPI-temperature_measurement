package pz_project_restapi.temperature.model;

import java.time.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TemperatureForm {

    /**
     *  Limit of temperature A for the searched period
     */
    private float temperatureA;

    /**
     *  Limit of temperature B for the searched period
     */
    private float temperatureB;

    /**
     *  Limit of time X for the searched period
     */
    private LocalDateTime timeX;

    /**
     *  Limit of time Y for the searched period
     */
    private LocalDateTime timeY;

}
