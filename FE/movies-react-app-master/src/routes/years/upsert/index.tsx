import {Box, Button, TextField} from "@material-ui/core";
import {useValidatorContext, withValidator} from "@nvidia1997/react-js-validator";
import {useSnackbar} from "notistack";
import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {useParams} from "react-router-dom";
import {loadYearsAction} from "../../movies/movies.actions";
import {selectYears} from "../../movies/movies.selectors";
import {MovieYear} from "../../movies/movies.typedef";
import * as  YearsService from "../service";

function Upsert() {
    const {id} = useParams();
    const dispatch = useDispatch();
    const {validator, createOnFormSubmitHandler} = useValidatorContext();
    const {enqueueSnackbar} = useSnackbar();
    const years = useSelector(selectYears) || [];
    const [value, setValue] = useState(2021);
    const [isLoading, setIsLoading] = useState(false);
    const [year, setYear] = useState({});
    const validatorId = "value";

    const _loadItems = () => dispatch(loadYearsAction());

    useEffect(() => {
        _loadItems();
    }, []);

    useEffect(() => {
        if (id === "create") {
            return;
        }

        setIsLoading(true);

        YearsService.findById(id)
            .then(year => {
                // @ts-ignore
                setYear(year);
                setValue(year.value);
            })
            .finally(() => setIsLoading(false));
    }, []);


    useEffect(() => {
        validator
            .boolean(!years.some(y => y.value === value), validatorId)
            .isTrue("Year already exists");
    }, [value, years]);

    const _onSave = () => {
        setIsLoading(true);

        // @ts-ignore
        const _year: MovieYear = {
            ...year,
            value: value,
        };

        YearsService
            .upsert(_year)
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
                        label="Value"
                        type="number"
                        value={value}
                        onChange={event => setValue(+event.target.value)}
                        error={validator.getValidator(validatorId)?.hasErrors}
                        helperText={validator.getValidator(validatorId)?.firstErrorMessage}
                        inputProps={{
                            min: 1950,
                            max: 2021
                        }}
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
