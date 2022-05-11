package com.ptc.computation;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class ComputationRow {

    private List<Computation> computations;

    private List<Integer> results;

    public ComputationRow(List<Computation> computations) {
        this.computations = computations;
        this.results = Arrays.asList(0, 0, 0);

        for (Computation computation : computations) {
            this.results.set(computation.getPosition() - 1, computation.getResult());
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

    public String toCSV() {
        if (this.computations.size() == 1) {
            Computation computation = this.computations.get(0);
            if (!computation.isCustom())
                return computation.toCSV();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("CUSTOM");
        this.results.forEach(result -> sb.append(";").append(result));
        return sb.toString();
    }
}
