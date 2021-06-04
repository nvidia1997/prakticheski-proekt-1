// @ts-ignore
import React from 'react';
// @ts-ignore
import ReactDOM from 'react-dom';
import './index.css';
import {store} from './app/store';
import {Provider} from 'react-redux';
import Header from "./components/header";
import Routes from "./routes";
import * as serviceWorker from './serviceWorker';
// @ts-ignore
import {BrowserRouter as Router} from "react-router-dom";

ReactDOM.render(
    <React.StrictMode>
        <Router>
            <Provider store={store}>
                <Header/>

                <Routes/>
            </Provider>
        </Router>
    </React.StrictMode>,
    document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
