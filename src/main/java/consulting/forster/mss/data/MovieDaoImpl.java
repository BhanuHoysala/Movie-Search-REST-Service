package consulting.forster.mss.data;

import consulting.forster.mss.utils.MovieConstants;
import consulting.forster.mss.utils.MovieLabels;
import consulting.forster.mss.utils.MovieUtilities;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Slf4j
@Component
public class MovieDaoImpl implements MovieDao {

    /**
     * Map can be replaced by embedded DB or other caching tools
     * Hold the each movie categorized under FSK label ratings, can get the count as well from this Map
     */
    private static Map<MovieLabels, List<JSONObject>> moviesCache;

    @Value("${source.file.name}")
    private String dataSource;

    /**
     * Initializes the HashMap moviesCache
     */
    @PostConstruct
    private void init() {

        if (this.moviesCache == null) { // Since it needed to be loaded only once

            this.moviesCache = new HashMap<>();
            Arrays.stream(MovieLabels.values()).forEach(label ->
                    this.moviesCache.put(label, new ArrayList<JSONObject>())
            );

            try {
                Optional<JSONArray> jsonArray = MovieUtilities.parseJsonObjectList(dataSource);
                if (jsonArray.isPresent()) {
                    moviesCache = parseMovieData(jsonArray.get());
                }
            } catch (IOException e) {
                log.error("Exception occurred while reading the file ", e);
            } catch (ParseException e) {
                log.error("Exception occurred while parsing the JSON the file ", e);
            }
        }
    }

    /**
     * @param jsonArray
     * @return
     */
    private Map<MovieLabels, List<JSONObject>> parseMovieData(JSONArray jsonArray) {

        jsonArray.stream().forEach(movie -> {
            JSONArray fskLabels = (JSONArray) ((JSONObject) movie).get(MovieConstants.FSK_JSON_KEY);
            fskLabels.stream().forEach(label ->
                    moviesCache.get(MovieLabels.valueOf(((String) label).toUpperCase())).add((JSONObject) movie)
            );
        });

        return moviesCache;
    }

    @Override
    public Map<MovieLabels, List<JSONObject>> getMovies() {

        return moviesCache;
    }
}
