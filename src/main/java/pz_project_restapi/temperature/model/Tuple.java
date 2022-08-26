package pz_project_restapi.temperature.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tuple {

    /**
     * Index where sequence begin.
     */
    private int start;

    /**
     * Size of sequence.
     */
    private int length;

}
