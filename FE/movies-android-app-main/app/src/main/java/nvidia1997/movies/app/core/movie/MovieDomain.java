package nvidia1997.movies.app.core.movie;

import nvidia1997.movies.app.core.Domain;

public class MovieDomain extends Domain<Integer> {

    private int id;

    private int genreId;

    private int releaseYearId;

    private String posterUrl;

    private String title;

    private String overview;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public int getReleaseYearId() {
        return releaseYearId;
    }

    public void setReleaseYearId(int releaseYearId) {
        this.releaseYearId = releaseYearId;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}

