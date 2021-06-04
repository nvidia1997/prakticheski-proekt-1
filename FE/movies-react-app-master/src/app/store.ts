import {configureStore, ThunkAction, Action} from '@reduxjs/toolkit';
import moviesReducer from "../routes/movies/movies.reducers";
import {MOVIES_STATE_KEY} from "../routes/movies/movies.actions";

export const store = configureStore({
    reducer: {
        // @ts-ignore
        [MOVIES_STATE_KEY]: moviesReducer
    },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppThunk<ReturnType = void> = ThunkAction<ReturnType,
    RootState,
    unknown,
    Action<string>>;
