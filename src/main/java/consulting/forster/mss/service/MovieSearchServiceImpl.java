package consulting.forster.mss.service;

import consulting.forster.mss.data.MovieDao;
import consulting.forster.mss.utils.MovieLabels;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MovieSearchServiceImpl implements MovieSearchService {

    @Autowired
    private MovieDao movieDao;

    /**
     * @param fskLabel
     * @return
     */
    @Override
    public JSONArray findMoviesByLabel(final MovieLabels fskLabel) {

        Map<MovieLabels, List<JSONObject>> movies = movieDao.getMovies();

        JSONArray jsonArray = new JSONArray();
        movies.get(fskLabel).stream().forEach(m -> {

            JSONObject movie = new JSONObject();
            movie.put("id", Integer.parseInt((String) m.get("id")));
            movie.put("title", ((String) m.get("title")).replace("\"", ""));
            movie.put("year", m.get("production_year"));

            jsonArray.add(movie);
        });

        return jsonArray;
    }

}
