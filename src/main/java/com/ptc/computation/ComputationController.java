package com.ptc.computation;

import com.opencsv.exceptions.CsvException;
import com.ptc.computation.rules.ComputationRule;
import com.ptc.computation.rules.Description;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static com.ptc.computation.ComputeConst.*;

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
            String filename = FILEPATH + FILENAME + Calendar.getInstance().getTimeInMillis() + CSV_EXTENSION;
            file.transferTo(new File(filename));

            List<ComputationRule> rows = computationService.compute(filename);
            return exportCSV(rows, FILENAME + Calendar.getInstance().getTimeInMillis() + CSV_EXTENSION);

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

    @PostMapping("/async/computation")
    public ResponseEntity<Object> computeFileAsync(@RequestParam("file") MultipartFile file) {

        try {
            UUID uuid = UUID.randomUUID();

            String filename = FILEPATH + FILENAME + uuid + CSV_EXTENSION + ".in";
            file.transferTo(new File(filename));

            computationService.computeAsync(uuid);

            HashMap<String, String> json = new HashMap<>();
            json.put("uuid", uuid.toString());

            return ResponseEntity.ok(json);
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/async/computation/{uuid}")
    public ResponseEntity<Object> getAsyncComputation(@PathVariable("uuid") UUID uuid) {
        String filename = FILEPATH + FILENAME + uuid + CSV_EXTENSION;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("text/csv"));
            headers.setContentDisposition(ContentDisposition.parse("attachment; filename=" + filename));
            InputStreamResource body = new InputStreamResource(new FileInputStream(filename));
            return ResponseEntity.ok().headers(headers).body(body);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/async/status/{uuid}")
    public ResponseEntity<Object> getAsyncStatus(@PathVariable("uuid") UUID uuid) {
        String filename = FILEPATH + FILENAME + uuid + CSV_EXTENSION;
        File file = new File(filename);
        if (file.exists()) {
            return ResponseEntity.ok("COMPLETED");
        }
        else {
            return ResponseEntity.ok("NOT_STARTED OR IN_PROGRESS");
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
