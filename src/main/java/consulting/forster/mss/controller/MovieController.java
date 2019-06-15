package consulting.forster.mss.controller;

import consulting.forster.mss.service.MovieSearchService;
import consulting.forster.mss.utils.MovieLabels;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;

@Api(value = "Movie Search", tags = {"Movie REST Controller"})
@Slf4j
@RestController
@RequestMapping("v1/movies")
public class MovieController {

    @Autowired
    private MovieSearchService movieSearchService;

    /**
     * Returns the movies list as JSON formatted payload against searched FSK label
     *
     * @param fskLabel
     * @return
     */
    @ApiOperation(value = "Search movies by FSK/S labels ex: FSK12, FSF12", response = ResponseEntity.class)
    @GetMapping("/search")
    public ResponseEntity<?> getMotionPictures(@RequestParam("fskLabel") String fskLabel) {

        log.info("Motion Picture list request for the FSK label {}", fskLabel);

        try {
            MovieLabels label = MovieLabels.valueOf(fskLabel.toUpperCase()); // Validating for the user input

            JSONArray movies = movieSearchService.findMoviesByLabel(label);
            return new ResponseEntity<>(movies, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid search Label - " + fskLabel, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Internal Server Exception", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns the movies list as JSON file instead of JSON formatted payload
     *
     * @param fskLabel
     * @return
     */
    @ApiOperation(value = "Search and download JSON document of movies by FSK/S labels ex: FSK12, FSF12", response = ResponseEntity.class)
    @GetMapping("/download")
    public ResponseEntity getMotionPicturesJsonFile(@RequestParam("fskLabel") String fskLabel) {

        log.info("Motion Picture JSON file requested for the FSK label {}", fskLabel);

        File moviesJsonFile = null;

        try {
            MovieLabels label = MovieLabels.valueOf(fskLabel.toUpperCase()); // Validating for the user input
            JSONArray movies = movieSearchService.findMoviesByLabel(label);

            // Creating a temporary json file
            moviesJsonFile = File.createTempFile(System.currentTimeMillis() + "", ".json");
            log.info("Temporary file created at {}", moviesJsonFile.getAbsoluteFile());

            // Writing the Json data to temporarily created json file
            try (FileWriter file = new FileWriter(moviesJsonFile)) {
                file.write(movies.toString());
                file.flush();
            }

            // Sending data client
            try (InputStream inputStream = new FileInputStream(moviesJsonFile)) {

                byte[] outFileBytes = org.apache.commons.io.IOUtils.toByteArray(inputStream);
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.add("content-disposition", "attachment; filename=" + fskLabel);
                responseHeaders.add("Content-Type", "application/json");
                return new ResponseEntity(outFileBytes, responseHeaders, HttpStatus.OK);
            }

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid search Label - " + fskLabel, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Internal Server Exception", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (moviesJsonFile != null) {
                moviesJsonFile.delete();
            }
        }
    }

    /**
     * Application Heart Beat test REST end point
     *
     * @return
     */
    @ApiOperation(value = "Service heart beat check, HTTP status 200 OK", response = ResponseEntity.class)
    @GetMapping("welcome")
    public ResponseEntity<String> getHeartBeat() {

        return new ResponseEntity<>(HttpStatus.OK.toString(), HttpStatus.OK);
    }

}
