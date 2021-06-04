import React from "react";
// @ts-ignore
import {Route, Switch} from "react-router-dom";

import Movies from "./movies/list";
import MoviesUpsert from "./movies/upsert";

import GenresList from "./genres/list";
import GenresUpsert from "./genres/upsert";

import YearsList from "./years/list";
import YearsUpsert from "./years/upsert";


export const ROUTE_PATHS = {
    MOVIES_LIST: "/",
    MOVIES_EDIT: (id = ':id') => `/movies/${id}`,
    MOVIES_CREATE: `/movies/create`,

    YEARS_LIST: "/years",
    YEARS_EDIT: (id = ':id') => `/years/${id}`,
    YEARS_CREATE: `/years/create`,

    GENRES_LIST: "/genres",
    GENRES_EDIT: (id = ':id') => `/genres/${id}`,
    GENRES_CREATE: `/genres/create`,
};

export default function Index() {
    return (
        <Switch>
            <Route path={ROUTE_PATHS.GENRES_EDIT()}>
                <GenresUpsert/>
            </Route>
            <Route path={ROUTE_PATHS.GENRES_CREATE}>
                <GenresUpsert/>
            </Route>
            <Route path={ROUTE_PATHS.GENRES_LIST}>
                <GenresList/>
            </Route>


            <Route path={ROUTE_PATHS.YEARS_EDIT()}>
                <YearsUpsert/>
            </Route>
            <Route path={ROUTE_PATHS.YEARS_CREATE}>
                <YearsUpsert/>
            </Route>
            <Route path={ROUTE_PATHS.YEARS_LIST}>
                <YearsList/>
            </Route>


            <Route path={ROUTE_PATHS.MOVIES_EDIT()}>
                <MoviesUpsert/>
            </Route>
            <Route path={ROUTE_PATHS.MOVIES_CREATE}>
                <MoviesUpsert/>
            </Route>
            <Route path={ROUTE_PATHS.MOVIES_LIST}>
                <Movies/>
            </Route>
        </Switch>
    );
}
