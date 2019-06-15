package consulting.forster.mss.service;

import consulting.forster.mss.utils.MovieLabels;
import org.json.simple.JSONArray;

public interface MovieSearchService {

   JSONArray findMoviesByLabel(MovieLabels fskLabel);
}
