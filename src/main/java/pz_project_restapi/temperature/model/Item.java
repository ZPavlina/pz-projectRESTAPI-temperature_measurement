package pz_project_restapi.temperature.model;

import java.time.*;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "measurement")
public class Item {

    /**
     *  Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Date and time
     */
    @Column(name = "dateandtime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime dateAndTime;

    /**
     * Temperature
     */
    @Column(name = "temperature")
    private float temperature;


}
