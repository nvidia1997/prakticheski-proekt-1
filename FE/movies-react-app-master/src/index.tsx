// @ts-ignore
import {SnackbarProvider} from "notistack";
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
import "./css/bootstrap/bootstrap.min.css";
import "./css/index.css";

ReactDOM.render(
    <React.StrictMode>
        <Router>
            <Provider store={store}>
                <SnackbarProvider maxSnack={3} autoHideDuration={3000}>
                    <Header/>

                    <Routes/>
                </SnackbarProvider>
            </Provider>
        </Router>
    </React.StrictMode>,
    document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
