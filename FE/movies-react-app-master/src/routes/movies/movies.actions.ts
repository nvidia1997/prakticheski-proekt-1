import {ReduxAction} from "../../index.typedef";
import {selectSelectedGenresIds} from "./movies.selectors";
import * as  MoviesService from "./service";
import * as  GenresService from "../genres/service";
import * as  YearsService from "../years/service";
import {Movie, MovieGenre, MovieYear} from "./movies.typedef";

export const MOVIES_STATE_KEY = "MOVIES";
export const MOVIES_LOADED = `${MOVIES_STATE_KEY}/MOVIES_LOADED`;
export const GENRES_LOADED = `${MOVIES_STATE_KEY}/GENRES_LOADED`;
export const YEARS_LOADED = `${MOVIES_STATE_KEY}/YEARS_LOADED`;

export const SET_MOVIES_VIEW_MODE = `${MOVIES_STATE_KEY}/SET_MOVIES_VIEW_MODE`;
export const SET_SELECTED_GENRES_IDS = `${MOVIES_STATE_KEY}/SET_SELECTED_GENRES_IDS`;
export const SET_MOVIES_YEAR = `${MOVIES_STATE_KEY}/SET_MOVIES_YEAR`;

export const loadMoviesAction = () => (dispatch: any) => {
    return MoviesService.list()
        .then(movies => {
            return dispatch(moviesLoadedAction(movies));
        })
        .catch(error => {
            return Promise.reject(error);
        });
};

export const loadGenresAction = () => (dispatch: any) => {
    return GenresService.list()
        .then(genres => {
            return dispatch(genresLoadedAction(genres));
        })
        .catch(error => {
            return Promise.reject(error);
        });
};

export const loadYearsAction = () => (dispatch: any) => {
    return YearsService.list()
        .then(years => {
            return dispatch(yearsLoadedAction(years));
        })
        .catch(error => {
            return Promise.reject(error);
        });
};

export const moviesLoadedAction = (movies: Movie[]): ReduxAction => ({
    type: MOVIES_LOADED,
    payload: {movies}
});

export const genresLoadedAction = (genres: MovieGenre[]): ReduxAction => ({
    type: GENRES_LOADED,
    payload: {genres}
});

export const yearsLoadedAction = (years: MovieYear[]): ReduxAction => ({
    type: YEARS_LOADED,
    payload: {years}
});

export const setMoviesViewModeAction = (viewMode: string): ReduxAction => ({
    type: SET_MOVIES_VIEW_MODE,
    payload: {viewMode}
});

const setSelectedGenresIdsAction = (selectedGenresIds: number[]): ReduxAction => ({
    type: SET_SELECTED_GENRES_IDS,
    payload: {selectedGenresIds}
});

export const toggleSelectedGenreIdAction = (genreId: number) => (dispatch: any, getState: () => object) => {
    const state = getState();
    let _selectedGenresIds = selectSelectedGenresIds(state);

    if (_selectedGenresIds.includes(genreId)) {
        _selectedGenresIds = _selectedGenresIds.filter(gId => gId !== genreId);
    }
    else {
        _selectedGenresIds = [
            ..._selectedGenresIds,
            genreId
        ];
    }

    return dispatch(setSelectedGenresIdsAction(_selectedGenresIds));
};

export const setMoviesYearAction = (year?: MovieYear): ReduxAction => ({
    type: SET_MOVIES_YEAR,
    payload: {year}
});
