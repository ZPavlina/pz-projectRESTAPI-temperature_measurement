package pz_project_restapi.temperature.model;

import java.time.*;
import com.fasterxml.jackson.annotation.*;
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
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime TimeX;

    /**
     *  Limit of time Y for the searched period
     */
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime TimeY;

}
