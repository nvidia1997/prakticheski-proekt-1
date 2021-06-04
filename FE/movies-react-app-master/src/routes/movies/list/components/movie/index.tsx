import {Box, Button} from "@material-ui/core";
import clsx from "clsx";
import React from "react";
import {useDispatch, useSelector} from "react-redux";
import {ROUTE_PATHS} from "../../../../index";
import {loadMoviesAction} from "../../../movies.actions";
import {VIEW_MODES} from "../../../movies.constants";
import {selectMoviesViewMode} from "../../../movies.selectors";
import {Movie} from "../../../movies.typedef";
import {deleteById} from "../../../service";
import {
    Link
} from "react-router-dom";

interface Props {
    movie: Movie
}

export default function MovieCard(props: Props) {
    const {movie} = props;
    const viewMode = useSelector(selectMoviesViewMode);
    const dispatch = useDispatch();

    const _onDeleteClick = () => {
        deleteById(movie.id)
            .then(() => dispatch(loadMoviesAction()));
    };

    return (
        <div
            className={clsx({
                "item col-xs-4 col-lg-4": true,
                "list-group-item": viewMode === VIEW_MODES.LIST,
                "grid-group-item": viewMode === VIEW_MODES.GRID,
            })}
        >
            <Box component="span" mb={1} display="flex" justifyContent="flex-end">
                <Link
                    className="btn btn-primary mr-1"
                    to={ROUTE_PATHS.MOVIES_EDIT(movie.id.toString())}
                >
                    Edit
                </Link>

                <Button
                    size="small"
                    variant="contained"
                    color="secondary"
                    onClick={_onDeleteClick}
                >
                    Delete
                </Button>
            </Box>

            <div className="thumbnail card">
                <div className="img-event">
                    <img
                        className="group list-group-image img-fluid"
                        src={movie.posterUrl}
                        alt="movie poster"
                    />
                </div>
                <div className="caption card-body">
                    <h4 className="group card-title inner list-group-item-heading">
                        {movie.title} &nbsp;

                        <span className="badge badge-secondary">
                            {movie.releaseYear.value.toString()}
                       </span>
                        &nbsp;
                        <span className="badge badge-secondary">
                            {movie.genre.name}
                       </span>
                    </h4>
                    <p className="group inner list-group-item-text">{movie.overview}</p>
                </div>
            </div>
        </div>
    );
}
