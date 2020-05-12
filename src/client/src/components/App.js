import React from "react";
import LoginPage from "./LoginPage";
import MainPage from "./MainPage";
import MenuPage from "./MenuPage";
import CreateUserPage from "./CreateUserPage";
import {connect} from "react-redux";
import {addDomainName} from "../redux/actions";
import SockJsClient from "react-stomp";

class App extends React.Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    this.props.addDomainName("http://localhost:8080");
  }

  receivedNotification = (message) => {
    console.log(message);
  }

  render() {
    let renderedPage;
    if (this.props.activePage === "Login") renderedPage = <LoginPage/>;
    else if (this.props.activePage === "MainPage") renderedPage = <MainPage/>;
    else if (this.props.activePage === "MenuPage") renderedPage = <MenuPage/>;
    else renderedPage = <CreateUserPage/>;
    return (
        <div id="App">
          <SockJsClient
              url="http://localhost:8080/notification_ws"
              topics={["/user/queue/notify"]}
              onMessage={(msg) => {
                this.receivedNotification(msg);
              }}
              ref={(client) => {
                this.clientRef = client;
                console.log(client);
              }}
          />
          {renderedPage}
        </div>
    );
  }
}

function mapStateToProps(state) {
  return {
    activePage: state.activePage,
  };
}

export default connect(mapStateToProps, {addDomainName})(App);
