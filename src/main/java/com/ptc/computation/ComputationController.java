package com.ptc.computation;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

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

            List<ComputationRow> rows = computationService.compute(filename);
            return exportCSV(rows, "computation-" + Calendar.getInstance().getTimeInMillis() + ".csv");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ResponseEntity<Object> exportCSV(List<ComputationRow> rows, String filename) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDisposition(ContentDisposition.parse("attachment; filename=" + filename));

        InputStreamResource csv = computationCSVService.exportCSV(rows);

        return ResponseEntity.ok().headers(headers).body(csv);
    }
}
