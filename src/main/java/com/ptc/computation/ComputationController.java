package com.ptc.computation;

import com.opencsv.exceptions.CsvException;
import com.ptc.computation.rules.ComputationRule;
import com.ptc.computation.rules.Description;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/api")
public class ComputationController {

    private final ComputationService computationService;

    private final ComputationCSVService computationCSVService;

    public ComputationController(ComputationService computationService, ComputationCSVService computationCSVService) {
        this.computationService = computationService;
        this.computationCSVService = computationCSVService;
    }

    @PostMapping("/computation")
    public ResponseEntity<Object> computeFile(@RequestParam("file") MultipartFile file) {

        try {
            String filename = "/tmp/computation-" + Calendar.getInstance().getTimeInMillis() + ".csv";
            file.transferTo(new File(filename));

            List<ComputationRule> rows = computationService.compute(filename);
            return exportCSV(rows, "computation-" + Calendar.getInstance().getTimeInMillis() + ".csv");

        }
        catch (ComputeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (IOException | CsvException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/rules")
    @ResponseBody
    public ResponseEntity<Object> getAllRules() {
        Function<Description, HashMap<String, String>> descriptionToJson = description -> {
            HashMap<String, String> json = new HashMap<>();
            json.put("name", description.name());
            json.put("description", description.value());
            return json;
        };
        try {
            return ResponseEntity.ok(computationService.getAllRules().stream().map(descriptionToJson).toList());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ResponseEntity<Object> exportCSV(List<ComputationRule> rules, String filename) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDisposition(ContentDisposition.parse("attachment; filename=" + filename));

        InputStreamResource csv = computationCSVService.exportCSV(rules);

        return ResponseEntity.ok().headers(headers).body(csv);
    }
}
