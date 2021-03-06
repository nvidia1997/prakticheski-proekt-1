import {MOVIES_STATE_KEY} from "./movies.actions";
import {Movie, MovieGenre, MovieYear} from "./movies.typedef";

export const selectFilteredMovies = (state: any): Movie[] => {
    const _selectedGenresIds = selectSelectedGenresIds(state);
    const _moviesYear = selectMoviesYear(state);

    return state[MOVIES_STATE_KEY]
        .movies
        .filter((m: Movie) => _selectedGenresIds.length === 0 || _selectedGenresIds.some(gId => m.genre.id.toString() === gId.toString()))
        .filter((m: Movie) => !_moviesYear || m.releaseYear.value.toString() === _moviesYear.toString());
};
export const selectYears = (state: any): MovieYear[] => state[MOVIES_STATE_KEY].years;
export const selectGenres = (state: any): MovieGenre[] => state[MOVIES_STATE_KEY].genres;
export const selectIsLoadingMovies = (state: any): boolean => state[MOVIES_STATE_KEY].isLoadingMovies;
export const selectIsLoadingGenres = (state: any): boolean => state[MOVIES_STATE_KEY].isLoadingGenres;
export const selectMoviesViewMode = (state: any): string => state[MOVIES_STATE_KEY].viewMode;
export const selectSelectedGenresIds = (state: any): number[] => state[MOVIES_STATE_KEY].selectedGenresIds;
export const selectMoviesYear = (state: any): MovieYear => state[MOVIES_STATE_KEY].year;
