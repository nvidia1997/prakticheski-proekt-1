import {Box, Button} from "@material-ui/core";
import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {Link} from "react-router-dom";
import ResponsiveTable from "../../../components/responsive-table/responsive-table";
import {createColumn} from "../../../components/responsive-table/responsive-table.utils";
import {ROUTE_PATHS} from "../../index";
import * as moviesSelectors from "../../movies/movies.selectors";
import * as moviesActions from "../../movies/movies.actions";
import * as GenresService from "../service";

function GenresList() {
    const dispatch = useDispatch();
    const genres = useSelector(moviesSelectors.selectGenres) || [];

    useEffect(() => {
        dispatch(moviesActions.loadGenresAction());
    }, []);

    const _onDeleteClick = (id: number) => {
        GenresService.deleteById(id)
            .then(() => {
                dispatch(moviesActions.loadGenresAction());
            });
    };

    return (
        <Box>
            <Box mb={2}>
                <Link
                    className="btn btn-link"
                    to={ROUTE_PATHS.GENRES_CREATE}
                >
                    Create new
                </Link>
            </Box>

            <ResponsiveTable
                tableId="GenresTable"
                noItemsNode="No items found..."
                columns={[
                    createColumn({
                        xs: true,
                        name: "id"
                    }),
                    createColumn({
                        xs: true,
                        name: "name"
                    }),
                    createColumn({
                        xs: true,
                        name: "edit"
                    }),
                    createColumn({
                        xs: true,
                        name: "delete"
                    })
                ]}
                rows={genres.map(
                    (item) => ([
                        item.id,
                        item.name,
                        <Link
                            className="btn btn-primary"
                            to={ROUTE_PATHS.GENRES_EDIT(item.id.toString())}
                        >
                            Edit
                        </Link>,
                        <Button
                            size="small"
                            variant="contained"
                            color="secondary"
                            onClick={() => _onDeleteClick(item.id)}
                        >
                            Delete
                        </Button>
                    ]),
                )}
            />
        </Box>
    );
}

export default GenresList;
