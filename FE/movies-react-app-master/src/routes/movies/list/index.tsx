import {Box} from "@material-ui/core";
import React, {useEffect, useMemo} from 'react';
import {useDispatch, useSelector} from "react-redux";
import Dropdown from "../../../components/dropdown";
import {ROUTE_PATHS} from "../../index";
import * as moviesActions from "../movies.actions";
import * as moviesSelectors from "../movies.selectors";
import GenresSelector from "./components/genres-selector";
import MovieCard from "./components/movie";
import ViewToggle from "./components/view-toggle";
import {Link} from "react-router-dom";


function Movies() {
    const dispatch = useDispatch();
    const filteredMovies = useSelector(moviesSelectors.selectFilteredMovies);
    const moviesYear = useSelector(moviesSelectors.selectMoviesYear);
    const years = useSelector(moviesSelectors.selectYears);
    const yearsDropdownList: string[] = useMemo(() => {
        return [
            "all",
            ...years.map(y => y.value.toString()),
        ];
    }, [years]);

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
            <Box mb={2}>
                <Link
                    className="btn btn-link"
                    to={ROUTE_PATHS.MOVIES_CREATE}
                >
                    Create new
                </Link>
            </Box>

            <div className="container p-5">
                <Dropdown
                    label="Year"
                    items={yearsDropdownList}
                    value={moviesYear?.value}
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
