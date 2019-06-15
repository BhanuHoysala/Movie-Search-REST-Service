package consulting.forster.mss.utils;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.ResourceUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public interface MovieUtilities {

    JSONParser parser = new JSONParser();

    /**
     * Parses the list of Json object data from the given file path
     *
     * @param dataSource as absolute file path
     * @return Optional<JSONArray>
     * @throws IOException
     * @throws ParseException
     */
    static Optional<JSONArray> parseJsonObjectList(String dataSource) throws IOException, ParseException {

        try (FileReader fileReader = new FileReader(ResourceUtils.getFile(dataSource))) {
            JSONArray jsonArray = (JSONArray) parser.parse(fileReader);
            return Optional.of(jsonArray);
        }
    }

}
