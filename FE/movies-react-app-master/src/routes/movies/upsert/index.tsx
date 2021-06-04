import {Box, Button, TextField} from "@material-ui/core";
import {useValidatorContext, withValidator} from "@nvidia1997/react-js-validator";
import {useSnackbar} from "notistack";
import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {useParams} from "react-router-dom";
import Dropdown from "../../../components/dropdown";
import * as moviesSelectors from "../movies.selectors";
import {selectGenres} from "../movies.selectors";
import {Movie} from "../movies.typedef";
import * as  MoviesActions from "../movies.actions";
import * as  MoviesService from "../service";
import {VALIDATOR_IDS} from "./constants";

function Upsert() {
    const {id} = useParams();
    const dispatch = useDispatch();
    const years = useSelector(moviesSelectors.selectYears);
    const genres = useSelector(selectGenres);

    const {validator, createOnFormSubmitHandler} = useValidatorContext();
    const {enqueueSnackbar} = useSnackbar();
    const [overview, setOverview] = useState("");
    const [posterUrl, setPosterUrl] = useState("");
    const [title, setTitle] = useState("");
    const [genre, setGenre] = useState({id: -1, name: ""});
    const [year, setYear] = useState({id: -1, value: 2021});
    console.log(year, genre);
    const [isLoading, setIsLoading] = useState(false);

    const [movie, setMovie] = useState({id: -1});

    useEffect(() => {
        dispatch(MoviesActions.loadGenresAction());
        dispatch(MoviesActions.loadYearsAction());
    }, []);

    useEffect(() => {
        if (id === "create") {
            return;
        }

        setIsLoading(true);

        MoviesService.findById(id)
            .then(movie => {
                // @ts-ignore
                setMovie(movie);

                setTitle(movie.title);
                setOverview(movie.overview);
                setPosterUrl(movie.posterUrl);
                setGenre(movie.genre);
                setYear(movie.releaseYear);
            })
            .finally(() => setIsLoading(false));
    }, []);

    useEffect(() => {
        validator
            .string(title, VALIDATOR_IDS.title)
            .minLength(3, "Title must be atleast 3 chars");
    }, [title]);

    useEffect(() => {
        validator
            .string(overview, VALIDATOR_IDS.overview)
            .minLength(3, "Overview must be atleast 5 chars");
    }, [overview]);

    useEffect(() => {
        validator
            .string(posterUrl, VALIDATOR_IDS.posterUrl)
            .minLength(9, "Invalid url");
    }, [posterUrl]);

    const _onSave = () => {
        setIsLoading(true);

        const _movie: Movie = {
            ...movie,
            title,
            overview,
            posterUrl,
            // @ts-ignore
            genre,
            // @ts-ignore
            releaseYear: year
        };

        MoviesService
            .upsert(_movie)
            .then(() => {
                enqueueSnackbar("Saved", {variant: "success"});
            })
            .finally(() => setIsLoading(false));
    };

    return (
        <form className="container" onSubmit={createOnFormSubmitHandler(_onSave)}>
            <Box display="flex" justifyContent="center" alignItems="center" flexDirection="column">

                <Box mb={1}>
                    <TextField
                        fullWidth
                        label="Title"
                        value={title}
                        onChange={event => setTitle(event.target.value)}
                        error={validator.getValidator(VALIDATOR_IDS.title)?.hasErrors}
                        helperText={validator.getValidator(VALIDATOR_IDS.title)?.firstErrorMessage}
                    />
                </Box>

                <Box mb={1}>
                    <TextField
                        fullWidth
                        label="Overview"
                        value={overview}
                        onChange={event => setOverview(event.target.value)}
                        error={validator.getValidator(VALIDATOR_IDS.overview)?.hasErrors}
                        helperText={validator.getValidator(VALIDATOR_IDS.overview)?.firstErrorMessage}
                    />
                </Box>

                <Box mb={1}>
                    <TextField
                        fullWidth
                        type="url"
                        label="Poster url"
                        value={posterUrl}
                        onChange={event => setPosterUrl(event.target.value)}
                        error={validator.getValidator(VALIDATOR_IDS.posterUrl)?.hasErrors}
                        helperText={validator.getValidator(VALIDATOR_IDS.posterUrl)?.firstErrorMessage}
                    />
                </Box>

                <Box mb={1}>
                    <Dropdown
                        label="Year"
                        items={years.map(y => y.value)}
                        value={year.value}
                        // @ts-ignore
                        onChange={(e) => setYear(years.find(y => +y.value === +e.target.value))}
                    />
                </Box>

                <Box mb={1}>
                    <Dropdown
                        label="Genre"
                        items={genres.map(g => g.name)}
                        value={genre.name}
                        // @ts-ignore
                        onChange={(e) => setGenre(genres.find(g => g.name === e.target.value))}
                    />
                </Box>

                <Button
                    type="submit"
                    color="primary"
                    disabled={isLoading}
                >
                    {isLoading ? "Loading..." : " Save"}
                </Button>
            </Box>
        </form>
    );
}

export default withValidator(Upsert);
