import React from "react";
import {useDispatch, useSelector} from "react-redux";
import {toggleSelectedGenreIdAction} from "../../movies.actions";
import {selectGenres} from "../../movies.selectors";

export default function GenresSelector() {
    const dispatch = useDispatch();
    const genres = useSelector(selectGenres) || [];

    const _onGenreToggle = (e: React.ChangeEvent, genreId: number) => {
        dispatch(toggleSelectedGenreIdAction(genreId));
    };

    return (
        <div className="float-right w-25">
            <p className="font-weight-bold">Genres:</p>
            <ul className="d-flex flex-row flex-wrap" id="genres">
                {
                    genres.map((genre) => (
                        <div
                            key={genre.id}
                            className="custom-control custom-checkbox d-inline-flex m-1"
                        >
                            <input
                                type="checkbox"
                                className="custom-control-input genre"
                                id={`genre-${genre.id}`}
                                onChange={(e) => _onGenreToggle(e, +genre.id)}
                            />

                            <label
                                className="custom-control-label"
                                htmlFor={`genre-${genre.id}`}
                            >
                                {genre.name}
                            </label>
                        </div>
                    ))
                }
            </ul>
        </div>
    );
}