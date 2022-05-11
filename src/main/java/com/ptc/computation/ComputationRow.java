package com.ptc.computation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ComputationRow {

    private List<Computation> computations;

    private Integer result1;

    private Integer result2;

    private Integer result3;

    private Integer position1;

    private Integer position2;

    private Integer position3;

    public ComputationRow(List<Computation> computations) {
        this.computations = computations;
        if (!computations.isEmpty()) {
            this.result1 = computations.get(0).getResult();
            this.position1 = computations.get(0).getPosition();
        }
        if (computations.size() > 1) {
            this.result2 = computations.get(1).getResult();
            this.position2 = computations.get(1).getPosition();
        }
        if (computations.size() > 2) {
            this.result3 = computations.get(2).getResult();
            this.position3 = computations.get(2).getPosition();
        }
    }

    public String toString() {
        if (this.computations.size() == 1) {
            return this.computations.get(0).toString();
        }

        StringBuilder sb = new StringBuilder();
        computations.forEach(sb::append);
        return sb.toString();
    }
}
