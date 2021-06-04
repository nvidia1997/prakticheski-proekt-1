import clsx from "clsx";
import React from "react";
import {useSelector} from "react-redux";
import {VIEW_MODES} from "../../movies.constants";
import {selectMoviesViewMode} from "../../movies.selectors";
import {Movie} from "../../movies.typedef";

interface Props {
    movie: Movie
}

export default function MovieCard(props: Props) {
    const {movie} = props;
    const viewMode = useSelector(selectMoviesViewMode);

    return (
        <div
            className={clsx({
                "item col-xs-4 col-lg-4": true,
                "list-group-item": viewMode === VIEW_MODES.LIST,
                "grid-group-item": viewMode === VIEW_MODES.GRID,
            })}
        >
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
                    </h4>
                    <p className="group inner list-group-item-text">{movie.overview}</p>
                </div>
            </div>
        </div>
    );
}
