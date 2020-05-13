import React from "react";
import LoginPage from "./LoginPage";
import MainPage from "./MainPage";
import MenuPage from "./MenuPage";
import CreateUserPage from "./CreateUserPage";
import {connect} from "react-redux";
import {addDomainName, addNotification, changeNewNotifications, changeRefreshState,} from "../redux/actions";
import SockJsClient from "react-stomp";
import {persistor} from "../redux/store";

class App extends React.Component {

  componentDidMount() {
    this.props.addDomainName("http://localhost:8080");
  }

  getBalance = () => {
    const path = "http://localhost:8080/user/getUserBalance";
    fetch(path, {
      method: "GET",
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Cache-Control": "no-cache",
      },
      credentials: "include",
    }).then((res) => {
      if (res.status === 302) {
        persistor.purge();
      }
      return res.json();
    });
  };

  receivedNotification = (message) => {
    this.props.addNotification(message);
    switch (message.type) {
      case "FRIENDREQUEST":
        this.props.changeRefreshState("refreshFriendRequests", true);
        break;
      case "FRIENDACCEPT":
        this.props.changeRefreshState("refreshFriendsList", true);
        break;
      case "PAYMENT":
        this.props.changeRefreshState("refreshFeed", true);
        this.props.changeRefreshState("refreshBalance", true);
        break;
      default:
        break;
    }
    this.props.changeNewNotifications(true);
    console.log(message);
  };

  render() {
    let renderedPage;
    if (this.props.activePage === "Login") renderedPage = <LoginPage/>;
    else if (this.props.activePage === "MainPage") renderedPage = <MainPage/>;
    else if (this.props.activePage === "MenuPage") renderedPage = <MenuPage/>;
    else renderedPage = <CreateUserPage/>;

    let socket;
    if (this.props.loggedIn) {
      socket = (
          <SockJsClient
              url="http://localhost:8080/ws"
              topics={["/user/queue/notify"]}
              onMessage={this.receivedNotification}
              ref={(client) => {
                this.clientRef = client;
              }}
          />
      );
    }
    return (
        <div id="App">
          {socket}
          {renderedPage}
        </div>
    );
  }
}

function mapStateToProps(state) {
  return {
    activePage: state.activePage,
    loggedIn: state.loggedIn,
  };
}

export default connect(mapStateToProps, {
  addDomainName,
  addNotification,
  changeRefreshState,
  changeNewNotifications,
})(App);
