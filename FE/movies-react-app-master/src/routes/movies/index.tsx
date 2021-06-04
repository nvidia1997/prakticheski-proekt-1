import React, {useEffect} from 'react';
import {useDispatch, useSelector} from "react-redux";
import Dropdown from "../../components/dropdown";
import GenresSelector from "./components/genres-selector";
import MovieCard from "./components/movie";
import ViewToggle from "./components/view-toggle";
import * as moviesActions from "./movies.actions";
import {MOVIES_SORT_BY} from "./movies.constants";
import * as moviesSelectors from "./movies.selectors";
import {MovieYear} from "./movies.typedef";
import {getYearsList} from "./movies.utils";

function Movies() {
    const dispatch = useDispatch();
    const filteredMovies = useSelector(moviesSelectors.selectFilteredMovies) || [];
    const moviesYear: MovieYear = useSelector(moviesSelectors.selectMoviesYear);

    useEffect(() => {
        dispatch(moviesActions.loadMoviesAction());
    }, [dispatch]);

    useEffect(() => {
        dispatch(moviesActions.loadGenresAction());
    }, [dispatch]);

    useEffect(() => {
        dispatch(moviesActions.loadYearsAction());
    }, [dispatch]);

    const _onYearChange = (e: React.ChangeEvent) => {
        // @ts-ignore
        dispatch(moviesActions.setMoviesYearAction(e.target.value));
    };

    return (
        <div className="App">
            <div className="container p-5">
                <Dropdown
                    label="Year"
                    items={getYearsList()}
                    value={moviesYear.value}
                    // @ts-ignore
                    onChange={_onYearChange}
                />

                <GenresSelector/>
                <ViewToggle/>

                <div id="movies" className="row view-group">
                    {
                        filteredMovies
                            .map(movie => <MovieCard
                                key={`movie-${movie.id}`}
                                movie={movie}
                            />)
                    }
                </div>
            </div>
        </div>
    );
}

export default Movies;
