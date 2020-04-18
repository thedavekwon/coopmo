import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import CMainPage from "./components/CMainPage.js";
import CMenuPage from "./components/CMenuPage";
import * as serviceWorker from "./serviceWorker";
import "bootstrap/dist/css/bootstrap.min.css";
import AutoScale from "react-auto-scale/lib/components/AutoScale";

ReactDOM.render(
  <React.StrictMode>
    <div className="body">
      <CMainPage />
    </div>
  </React.StrictMode>,
  document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
