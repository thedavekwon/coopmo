import React, { PureComponent } from "react";
import CMenuButton from "./CMenuButton.js";
import CFriendsList from "./CFriendsList.js";
import AutoScale from "react-auto-scale";
import CFeed from "./CFeed.js";

export default class CMainPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = { feedTab: "Me" };
  }
  state = {};

  render() {
    return (
      <div
        className="master"
        style={{ backgroundColor: "rgba(255, 255, 255, 1)" }}
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
              <CFeed {...this.props} nodeId="38:1057" />
            </div>
          </div>
          <div style={{ zIndex: 1 }} className="outerDiv centerer">
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
              <CFriendsList {...this.props} nodeId="38:1056" />
            </div>
          </div>
          <div style={{ zIndex: 2 }} className="outerDiv centerer">
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
                      marginLeft: 0,
                      marginRight: 0,
                      flexGrow: 1,
                      marginTop: 0,
                      marginBottom: 0,
                      backgroundColor: "rgba(102, 0, 153, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div></div>
                  </div>
                </div>
                <div style={{ zIndex: 1 }} className="outerDiv centerer">
                  <div
                    id="I76:29;30:174"
                    style={{
                      marginLeft: 28,
                      marginRight: "90%",
                      flexGrow: 1,
                      marginTop: 28,
                      marginBottom: 28,
                      backgroundColor: "rgba(102, 0, 153, 1)",
                    }}
                    className="innerDiv"
                  >
                    <CMenuButton
                      {...this.props}
                      textColor="rgba(255, 255, 255, 1)"
                      page="main"
                      nodeId="I76:29;30:174"
                    />
                  </div>
                </div>
                <div style={{ zIndex: 2 }} className="outerDiv centerer">
                  <div
                    id="I76:29;97:225"
                    style={{
                      marginLeft: "40%",
                      marginRight: "40%",
                      flexGrow: 1,
                      marginTop: 14,
                      marginBottom: 28,
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
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
