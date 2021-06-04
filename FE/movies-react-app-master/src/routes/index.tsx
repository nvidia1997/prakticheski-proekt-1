import React from "react";
// @ts-ignore
import {Route, Switch} from "react-router-dom";
import Movies from "./movies";


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
            {/*  <Route path={ROUTE_PATHS.MOVIES_EDIT()}>
                <Movies/>
            </Route>
            <Route path={ROUTE_PATHS.MOVIES_CREATE}>
                <Movies/>
            </Route> */}
            <Route path={ROUTE_PATHS.MOVIES_LIST}>
                <Movies/>
            </Route>

            {/* <Route path={ROUTE_PATHS.GENRES_EDIT()}>
            </Route>
            <Route path={ROUTE_PATHS.GENRES_CREATE}>
            </Route>
            <Route path={ROUTE_PATHS.GENRES_LIST}>
            </Route>

            <Route path={ROUTE_PATHS.YEARS_EDIT()}>
            </Route>
            <Route path={ROUTE_PATHS.YEARS_CREATE}>
            </Route>
            <Route path={ROUTE_PATHS.YEARS_LIST}>
            </Route>*/}
        </Switch>
    );
}
