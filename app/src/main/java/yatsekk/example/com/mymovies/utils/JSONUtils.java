package yatsekk.example.com.mymovies.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import yatsekk.example.com.mymovies.data.Movie;
import yatsekk.example.com.mymovies.data.Review;
import yatsekk.example.com.mymovies.data.Trailer;

public class JSONUtils {

    public static final String KEY_RESULTS = "results";

    // Для отзывов
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_CONTENT = "content";

    // Для видео
    public static final String KEY_KEY_OF_VIDEO = "key";
    public static final String KEY_NAME = "name";
    public static final String BASE_YOUTUBE_URL = "https://www.youtube.com/watch?v=";

    // Вся информация о фильме
    public static final String KEY_VOTE_COUNT = "vote_count";
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ORIGINAL_TITLE = "original_title";
    public static final String KEY_OVERVIEW = "overview";
    public static final String KEY_POSTER_PATH = "poster_path";
    public static final String KEY_BACKDROP_PATH = "backdrop_path";
    public static final String KEY_VOTE_AVERAGE = "vote_average";
    public static final String KEY_RELEASE_DATE = "release_date";

    public static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    public static final String SMALL_POSTER_SIZE = "w185";
    public static final String BIG_POSTER_SIZE = "w780";

    public static ArrayList<Review> getReviewsFromJSON(JSONObject jsonObject) {
        ArrayList<Review> result = new ArrayList<>();
        if (jsonObject == null) return result;
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectReview = jsonArray.getJSONObject(i);
                String author = jsonObjectReview.getString(KEY_AUTHOR);
                String content = jsonObjectReview.getString(KEY_CONTENT);
                Review review = new Review(author, content);
                Log.i("Review", String.valueOf(review));
                result.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<Trailer> getTrailersFromJSON(JSONObject jsonObject) {
        ArrayList<Trailer> result = new ArrayList<>();
        if (jsonObject == null) return result;
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectTrailers = jsonArray.getJSONObject(i);
                String key = BASE_YOUTUBE_URL + jsonObjectTrailers.getString(KEY_KEY_OF_VIDEO);
                String name = jsonObjectTrailers.getString(KEY_NAME);
                Trailer trailer = new Trailer(key, name);
                Log.i("Review", String.valueOf(trailer));
                result.add(trailer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<Movie> getMoviesFromJSON(JSONObject jsonObject) {
        ArrayList<Movie> result = new ArrayList<>();
        if (jsonObject == null) return result;
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objectMovie = jsonArray.getJSONObject(i);
                int id = objectMovie.getInt(KEY_ID);
                int voteCount = objectMovie.getInt(KEY_VOTE_COUNT);
                String title = objectMovie.getString(KEY_TITLE);
                String originalTitle = objectMovie.getString(KEY_ORIGINAL_TITLE);
                String overview = objectMovie.getString(KEY_OVERVIEW);
                String posterPath = BASE_POSTER_URL + SMALL_POSTER_SIZE + objectMovie.getString(KEY_POSTER_PATH);
                String bigPosterPath = BASE_POSTER_URL + BIG_POSTER_SIZE + objectMovie.getString(KEY_POSTER_PATH);
                String backdropPath = objectMovie.getString(KEY_BACKDROP_PATH);
                double voteAverage = objectMovie.getDouble(KEY_VOTE_AVERAGE);
                String releaseDate = objectMovie.getString(KEY_RELEASE_DATE);
                Movie movie = new Movie(id, voteCount, title, originalTitle, overview, posterPath, bigPosterPath, backdropPath, voteAverage, releaseDate);
                result.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

}
