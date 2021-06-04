import {ReduxAction} from "../../index.typedef";
import {
    GENRES_LOADED,
    MOVIES_LOADED,
    SET_MOVIES_VIEW_MODE, SET_MOVIES_YEAR,
    SET_SELECTED_GENRES_IDS, YEARS_LOADED
} from "./movies.actions";
import {MOVIES_SORT_BY, VIEW_MODES} from "./movies.constants";

export const initialState = {
    movies: [],
    isLoadingMovies: false,

    genres: [],
    years: [],
    isLoadingGenres: false,
    viewMode: VIEW_MODES.GRID,
    sort_by: MOVIES_SORT_BY.VOTE_DESC,
    selectedGenresIds: [],
    year: "all"
};

export default function moviesReducer(state = initialState, action: ReduxAction) {
    switch (action.type) {
        case MOVIES_LOADED:
            return {
                ...state,
                // @ts-ignore
                movies: action.payload.movies
            };
        case GENRES_LOADED:
            return {
                ...state,
                // @ts-ignore
                genres: action.payload.genres
            };

        case YEARS_LOADED:
            return {
                ...state,
                // @ts-ignore
                years: action.payload.years
            };

        case SET_MOVIES_VIEW_MODE:
            return {
                ...state,
                // @ts-ignore
                viewMode: action.payload.viewMode
            };

        case SET_SELECTED_GENRES_IDS:
            return {
                ...state,
                // @ts-ignore
                selectedGenresIds: action.payload.selectedGenresIds
            };
        case SET_MOVIES_YEAR:
            return {
                ...state,
                // @ts-ignore
                year: action.payload.year
            };
        default:
            return state;
    }
}
