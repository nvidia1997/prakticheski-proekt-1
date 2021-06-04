import React from "react";
// @ts-ignore
import {Link} from "react-router-dom";
import {ROUTE_PATHS} from "../../routes";

function Header() {
    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
            <a className="navbar-brand" href={ROUTE_PATHS.MOVIES_LIST}>Movinfo</a>
            <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"/>
            </button>
            <div className="collapse navbar-collapse" id="navbarNav">
                <ul className="navbar-nav">
                    <li className="nav-item">
                        <a className="nav-link" href={ROUTE_PATHS.MOVIES_LIST}>Movies <span className="sr-only">(current)</span></a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href={ROUTE_PATHS.YEARS_LIST}>Years</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" href={ROUTE_PATHS.GENRES_LIST}>Genres</a>
                    </li>
                </ul>
            </div>
        </nav>
    );
}

export default Header;
