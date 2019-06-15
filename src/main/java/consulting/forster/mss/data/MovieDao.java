package consulting.forster.mss.data;

import consulting.forster.mss.utils.MovieLabels;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

public interface MovieDao {

    Map<MovieLabels, List<JSONObject>> getMovies();
}
