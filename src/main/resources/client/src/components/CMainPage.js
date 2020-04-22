import React from "react";
import CMenuButton from "./CMenuButton.js";
import CFriendsList from "./CFriendsList.js";

export default class CMainPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      feedTab: "Me",
      userId: "0cf8d8f2-c631-4568-99b5-0773d93a6e78",
      balance: 0,
    };
    this.getBalance();
  }

  getBalance = () => {
    const path =
        this.props.domainName + "/user/getUserBalance?userId=" + this.state.userId;
    fetch(path, {
      method: "GET",
    })
      .then((res) => res.json())
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
        }}
      >
        <div>
          <div style={{}} className="outerDiv centerer">
            <div
              id="38:1057"
              style={{
                width: "55.208333333333336%",
                marginLeft: "7.692311604817708%",
                marginTop: 197.284423828125,
                marginBottom: 11.715576171875,
                backgroundColor: "rgba(0, 0, 0, 0)",
                overflow: "hidden",
              }}
              className="innerDiv"
            >
              {/*
              <CFeed
                {...this.props}
                userId={this.state.userId}
                nodeId="38:1057"
                domainName = {this.props.domainName}
              />*/}
            </div>
          </div>
          <div
            style={{
              zIndex: 1,
            }}
            className="outerDiv centerer"
          >
            <div
              id="38:1056"
              style={{
                width: "20.416666666666668%",
                marginLeft: "72.91666666666667%",
                height: "71.2890625%",
                top: "20.99609375%",
                backgroundColor: "rgba(0, 0, 0, 0)",
                overflow: "hidden",
              }}
              className="innerDiv"
            >
                <CFriendsList
                    {...this.props}
                    userId={this.state.userId}
                    nodeId="38:1056"
                    domainName={this.props.domainName}
                />
            </div>
          </div>
          <div
            style={{
              zIndex: 2,
            }}
            className="outerDiv centerer"
          >
            <div
              id="76:29"
              style={{
                width: "100%",
                marginLeft: "0%",
                height: "10.3515625%",
                top: "0%",
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <div>
                <div style={{}} className="outerDiv centerer">
                  <div
                    id="I76:29;4:3"
                    style={{
                      marginRight: 0,
                      flexGrow: 1,
                      marginTop: 0,
                      marginBottom: 0,
                      backgroundColor: "rgba(102, 0, 153, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div> </div>
                  </div>
                </div>
                <div
                  style={{
                    flexGrow: 1,
                    width: "10%",
                    top: "25%",
                    height: "50%",
                    marginLeft: "2%",
                  }}
                  className="outerDiv centerer"
                >
                  <div
                    id="I76:29;30:174"
                    style={{
                      flexGrow: 1,
                      backgroundColor: "rgba(102, 0, 153, 1)",
                    }}
                    className="innerDiv"
                  >
                      <CMenuButton
                          {...this.props}
                          textColor="rgba(255, 255, 255, 1)"
                          page="main"
                          nodeId="I76:29;30:174"
                          domainName={this.props.domainName}
                      />
                  </div>
                </div>
                <div
                  style={{
                    zIndex: 2,
                  }}
                  className="outerDiv centerer"
                >
                  <div
                    id="I76:29;97:225"
                    style={{
                      marginLeft: "40%",
                      marginRight: "40%",
                      flexGrow: 1,
                      top: "2%",
                      color: "rgba(0, 0, 0, 1)",
                      fontSize: 73,
                      fontWeight: 700,
                      fontFamily: "Muli",
                      textAlign: "CENTER",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span
                        style={{
                          fontSize: 73,
                          fontStyle: "normal",
                          lineHeight: "NaN%",
                          letterSpacing: "undefinedpx",
                          color: "rgba(255, 255, 255, 1)",
                        }}
                        key="4"
                      >
                        Coop
                      </span>
                      <span
                        style={{
                          fontSize: 73,
                          fontStyle: "normal",
                          lineHeight: "NaN%",
                          letterSpacing: "undefinedpx",
                        }}
                        key="end"
                      >
                        mo
                      </span>
                    </div>
                  </div>
                </div>
                <div
                  style={{
                    zIndex: 3,
                    width: "10%",
                    top: "37%",
                    height: "50%",
                    marginLeft: "85%",
                  }}
                  className="outerDiv centerer"
                >
                  <div
                    id="I76:29;30:174"
                    style={{
                      flexGrow: 1,
                      backgroundColor: "rgba(102, 0, 153, 1)",
                    }}
                    className="innerDiv"
                  >
                    <span
                      style={{
                        fontSize: 24,
                        fontStyle: "normal",
                        lineHeight: "NaN%",
                        letterSpacing: "undefinedpx",
                        color: "rgba(255, 255, 255, 1)",
                      }}
                      key="4"
                    >
                      Balance : ${this.state.balance.toFixed(2) / 100.0}
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
