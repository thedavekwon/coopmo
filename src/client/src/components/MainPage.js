import React from "react";
import FriendsList from "./FriendsList.js";
import CFeed from "./CFeed.js";
import TitleBar from "./TitleBar.js";
import { connect } from "react-redux";
import { persistor } from "../redux/store";

class MainPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      feedTab: "Me",
      balance: 0,
    };
  }

  getBalance = () => {
    const path = this.props.domainName + "/user/getUserBalance";
    fetch(path, {
      method: "GET",
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Cache-Control": "no-cache",
      },
      credentials: "include",
    })
      .then((res) => {
        if (res.status === 302) {
          persistor.purge();
        }
        return res.json();
      })
      .then(
        (result) => {
          console.log(result);
          if (result.error != null) {
            console.log(result.error);
          } else {
            this.setState((state) => ({
              balance: result.data,
            }));
          }
        },
        (error) => {
          console.log(error);
        }
      );
  };

  componentDidMount() {
    this.getBalance();
  }

  render() {
    return (
      <div
        className="master"
        style={{
          backgroundColor: "rgba(255, 255, 255, 1)",
          display: "flex",
          flexDirection: "column",
          height: "100%",
        }}
      >
        <div
          style={{
            flex: 2,
            width: "100%",
            height: "120px",
          }}
        >
          <TitleBar
            page="main"
            balance={this.state.balance}
            domainName={this.props.domainName}
          />
        </div>
        <div
          style={{
            flex: 1,
          }}
        ></div>
        <div
          style={{
            flex: 14,
            display: "flex",
            width: "100%",
            height: "60%",
            justifyContent: "space-around",
          }}
        >
          <div style={{ flex: 1 }}></div>
          <div
            style={{
              flex: 5,
              height: "90%",
              backgroundColor: "rgba(0, 0, 0, 0)",
            }}
          >
            <CFeed {...this.props} domainName={this.props.domainName} />
          </div>
          <div style={{ flex: 1 }}></div>
          <div
            style={{
              flex: 2,
              backgroundColor: "rgba(0, 0, 0, 0)",
              height: "90%",
            }}
          >
            <FriendsList {...this.props} domainName={this.props.domainName} />
          </div>
          <div style={{ flex: 1 }}></div>
        </div>
      </div>
    );
  }
}

function mapStateToProps(state) {
  return {
    domainName: state.domainName,
  };
}

export default connect(mapStateToProps)(MainPage);
