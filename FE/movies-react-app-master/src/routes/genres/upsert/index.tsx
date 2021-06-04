import {Box, Button, TextField} from "@material-ui/core";
import {useValidatorContext, withValidator} from "@nvidia1997/react-js-validator";
import {useSnackbar} from "notistack";
import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {useParams} from "react-router-dom";
import {loadGenresAction} from "../../movies/movies.actions";
import {selectGenres} from "../../movies/movies.selectors";
import {MovieGenre} from "../../movies/movies.typedef";
import * as  GenresService from "../service";

function Upsert() {
    const {id} = useParams();
    const dispatch = useDispatch();
    const {enqueueSnackbar} = useSnackbar();
    const {validator, createOnFormSubmitHandler} = useValidatorContext();
    const genres = useSelector(selectGenres) || [];
    const [value, setValue] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const [genre, setGenre] = useState({});
    const validatorId = "value";

    const _loadItems = () => dispatch(loadGenresAction());

    useEffect(() => {
        _loadItems();
    }, []);

    useEffect(() => {
        if (id === "create") {
            return;
        }

        setIsLoading(true);

        GenresService.findById(id)
            .then(genre => {
                // @ts-ignore
                setGenre(genre);
                setValue(genre.name);
            })
            .finally(() => setIsLoading(false));
    }, []);


    useEffect(() => {
        validator
            .boolean(!genres.some(g => g.name.trim() === value.trim()), validatorId)
            .isTrue("Genre already exists");
    }, [value, genres]);

    const _onSave = () => {
        setIsLoading(true);

        // @ts-ignore
        const _genre: MovieGenre = {
            ...genre,
            name: value,
        };

        GenresService
            .upsert(_genre)
            .then(() => {
                enqueueSnackbar("Saved", {variant: "success"});
                _loadItems();
            })
            .finally(() => setIsLoading(false));
    };

    return (
        <form className="container" onSubmit={createOnFormSubmitHandler(_onSave)}>
            <Box display="flex" justifyContent="center" alignItems="center" flexDirection="column">

                <Box mb={1}>
                    <TextField
                        fullWidth
                        label="Name"
                        value={value}
                        onChange={event => setValue(event.target.value)}
                        error={validator.getValidator(validatorId)?.hasErrors}
                        helperText={validator.getValidator(validatorId)?.firstErrorMessage}
                    />
                </Box>

                <Button
                    type="submit"
                    variant="contained"
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
