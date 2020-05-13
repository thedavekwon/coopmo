import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import * as serviceWorker from "./serviceWorker";
import "bootstrap/dist/css/bootstrap.min.css";
import {Provider} from "react-redux";
import store from "./redux/store";
import App from "./components/App";

ReactDOM.render(
    <Provider store={store}>
        {/*<LoginPage domainName="http://localhost:8080"/>*/}
        <App/>
    </Provider>,
    document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
