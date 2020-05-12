import React from "react";
import LoginPage from "./LoginPage";
import MainPage from "./MainPage";
import MenuPage from "./MenuPage";
import CreateUserPage from "./CreateUserPage";
import {connect} from "react-redux";
import {addDomainName, addNotification} from "../redux/actions";
import SockJsClient from "react-stomp";

class App extends React.Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    this.props.addDomainName("http://localhost:8080");
  }

  receivedNotification = (message) => {
    this.props.addNotification(message);
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

export default connect(mapStateToProps, {addDomainName, addNotification})(App);
