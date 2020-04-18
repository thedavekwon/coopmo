import React, { PureComponent } from "react";
import CFeedTab from "./CFeedTab.js";

export default class CFeed extends PureComponent {
  constructor(props) {
    super(props);
    this.handleClick = this.handleClick.bind(this);
    this.state = { feedTab: "Me" };
  }
  handleClick(tab) {
    console.log("in handle click");
    this.setState((state) => ({
      feedTab: tab,
    }));
  }

  render() {
    let purple = "rgba(102, 0, 153, 1)";
    let white = "rgba(255, 255, 255, 1)";
    return (
      <div className="master" style={{ backgroundColor: "rgba(0, 0, 0, 0)" }}>
        <div>
          <div style={{}} className="outerDiv centerer">
            <div
              id="I38:1057;30:46"
              style={{
                marginLeft: 0,
                marginRight: 0,
                flexGrow: 1,
                height: "7%",
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <div>
                <div style={{}} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:46;30:9"
                    style={{
                      marginLeft: "66.67%",
                      marginRight: 0,
                      flexGrow: 1,
                      marginTop: 0,
                      marginBottom: 0,
                      backgroundColor:
                        this.state.feedTab == "Public" ? purple : white,
                    }}
                    className="innerDiv"
                    onClick={() => this.handleClick("Public")}
                  >
                    <CFeedTab name="Public" feedTab={this.state.feedTab} />
                  </div>
                </div>
                <div style={{ zIndex: 1 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:46;30:6"
                    style={{
                      marginLeft: "33.33%",
                      marginRight: "33.33%",
                      flexGrow: 1,
                      marginTop: 0,
                      marginBottom: 0,
                      backgroundColor:
                        this.state.feedTab == "Friends" ? purple : white,
                    }}
                    className="innerDiv"
                    onClick={() => this.handleClick("Friends")}
                  >
                    <CFeedTab name="Friends" feedTab={this.state.feedTab} />
                  </div>
                </div>
                <div style={{ zIndex: 2 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:46;30:12"
                    style={{
                      marginLeft: 0,
                      marginRight: "66.67%",
                      flexGrow: 1,
                      marginTop: 0,
                      marginBottom: 0,
                      backgroundColor:
                        this.state.feedTab == "Me" ? purple : white,
                    }}
                    className="innerDiv"
                    onClick={() => this.handleClick("Me")}
                  >
                    <CFeedTab name="Me" feedTab={this.state.feedTab} />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div style={{ zIndex: 1 }} className="outerDiv centerer">
            <div
              id="I38:1057;30:55"
              style={{
                marginLeft: 0,
                marginRight: 0,
                flexGrow: 1,
                top: "7%",
                height: "20%",
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <div>
                <div style={{}} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:55;30:25"
                    style={{
                      marginLeft: 0,
                      marginRight: 0,
                      flexGrow: 1,
                      marginTop: 0,
                      marginBottom: 0,
                      backgroundColor: "rgba(255, 255, 255, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div></div>
                  </div>
                </div>
                <div style={{ zIndex: 1 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:55;30:27"
                    style={{
                      marginLeft: "2%",
                      marginRight: "73%",
                      top: "2%",
                      height: 24,
                      flexGrow: 1,
                      marginTop: 17,
                      marginBottom: 105.61333274841309,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Date Time
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 2 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:55;30:28"
                    style={{
                      marginLeft: "33%",
                      marginRight: "2%",
                      flexGrow: 1,
                      top: "8%",
                      height: "84%",
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "RIGHT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Message
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 3 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:55;30:29"
                    style={{
                      marginLeft: "2%",
                      marginRight: "73%",
                      flexGrow: 1,
                      top: "38%",
                      height: 24,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Name 1 paid{" "}
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 4 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:55;30:30"
                    style={{
                      marginLeft: "2%",
                      marginRight: "73%",
                      flexGrow: 1,
                      top: "65%",
                      height: 24,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Name 2
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 5 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:55;38:1352"
                    style={{
                      marginLeft: "10%",
                      marginRight: "10%",
                      flexGrow: 1,
                      top: "94%",
                      height: 1,
                      backgroundColor: "rgba(102, 0, 153, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div style={{ zIndex: 2 }} className="outerDiv centerer">
            <div
              id="I38:1057;30:61"
              style={{
                marginLeft: 0,
                marginRight: 0,
                flexGrow: 1,
                marginTop: 207,
                marginBottom: 456,
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <div>
                <div style={{}} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:61;30:25"
                    style={{
                      marginLeft: 0,
                      marginRight: 0,
                      flexGrow: 1,
                      marginTop: 0,
                      marginBottom: 0,
                      backgroundColor: "rgba(255, 255, 255, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div></div>
                  </div>
                </div>
                <div style={{ zIndex: 1 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:61;30:27"
                    style={{
                      marginLeft: 15,
                      marginRight: 588.0000305175781,
                      flexGrow: 1,
                      marginTop: 17,
                      marginBottom: 105.61333274841309,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Date Time
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 2 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:61;30:28"
                    style={{
                      marginLeft: 321,
                      marginRight: 15,
                      flexGrow: 1,
                      marginTop: 17,
                      marginBottom: 18,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "RIGHT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Message
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 3 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:61;30:29"
                    style={{
                      marginLeft: 15,
                      marginRight: 552,
                      flexGrow: 1,
                      marginTop: 61.8133544921875,
                      marginBottom: 64.85330963134766,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Name 1 paid{" "}
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 4 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:61;30:30"
                    style={{
                      marginLeft: 15,
                      marginRight: 605,
                      flexGrow: 1,
                      marginTop: 103,
                      marginBottom: 29.746673583984375,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Name 2
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 5 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:61;38:1352"
                    style={{
                      marginLeft: 73,
                      marginRight: 72,
                      flexGrow: 1,
                      marginTop: 151,
                      marginBottom: 0,
                      backgroundColor: "rgba(102, 0, 153, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div style={{ zIndex: 3 }} className="outerDiv centerer">
            <div
              id="I38:1057;30:67"
              style={{
                marginLeft: 0,
                marginRight: 0,
                flexGrow: 1,
                marginTop: 359,
                marginBottom: 304,
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <div>
                <div style={{}} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:67;30:25"
                    style={{
                      marginLeft: 0,
                      marginRight: 0,
                      flexGrow: 1,
                      marginTop: 0,
                      marginBottom: 0,
                      backgroundColor: "rgba(255, 255, 255, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div></div>
                  </div>
                </div>
                <div style={{ zIndex: 1 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:67;30:27"
                    style={{
                      marginLeft: 15,
                      marginRight: 588.0000305175781,
                      flexGrow: 1,
                      marginTop: 17,
                      marginBottom: 105.61333274841309,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Date Time
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 2 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:67;30:28"
                    style={{
                      marginLeft: 321,
                      marginRight: 15,
                      flexGrow: 1,
                      marginTop: 17,
                      marginBottom: 18,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "RIGHT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Message
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 3 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:67;30:29"
                    style={{
                      marginLeft: 15,
                      marginRight: 552,
                      flexGrow: 1,
                      marginTop: 61.8133544921875,
                      marginBottom: 64.85330963134766,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Name 1 paid{" "}
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 4 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:67;30:30"
                    style={{
                      marginLeft: 15,
                      marginRight: 605,
                      flexGrow: 1,
                      marginTop: 103,
                      marginBottom: 29.746673583984375,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Name 2
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 5 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:67;38:1352"
                    style={{
                      marginLeft: 73,
                      marginRight: 72,
                      flexGrow: 1,
                      marginTop: 151,
                      marginBottom: 0,
                      backgroundColor: "rgba(102, 0, 153, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div style={{ zIndex: 4 }} className="outerDiv centerer">
            <div
              id="I38:1057;30:73"
              style={{
                marginLeft: 0,
                marginRight: 0,
                flexGrow: 1,
                marginTop: 511,
                marginBottom: 152,
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <div>
                <div style={{}} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:73;30:25"
                    style={{
                      marginLeft: 0,
                      marginRight: 0,
                      flexGrow: 1,
                      marginTop: 0,
                      marginBottom: 0,
                      backgroundColor: "rgba(255, 255, 255, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div></div>
                  </div>
                </div>
                <div style={{ zIndex: 1 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:73;30:27"
                    style={{
                      marginLeft: 15,
                      marginRight: 588.0000305175781,
                      flexGrow: 1,
                      marginTop: 17,
                      marginBottom: 105.61333274841309,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Date Time
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 2 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:73;30:28"
                    style={{
                      marginLeft: 321,
                      marginRight: 15,
                      flexGrow: 1,
                      marginTop: 17,
                      marginBottom: 18,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "RIGHT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Message
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 3 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:73;30:29"
                    style={{
                      marginLeft: 15,
                      marginRight: 552,
                      flexGrow: 1,
                      marginTop: 61.8133544921875,
                      marginBottom: 64.85330963134766,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Name 1 paid{" "}
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 4 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:73;30:30"
                    style={{
                      marginLeft: 15,
                      marginRight: 605,
                      flexGrow: 1,
                      marginTop: 103,
                      marginBottom: 29.746673583984375,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Name 2
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 5 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:73;38:1352"
                    style={{
                      marginLeft: 73,
                      marginRight: 72,
                      flexGrow: 1,
                      marginTop: 151,
                      marginBottom: 0,
                      backgroundColor: "rgba(102, 0, 153, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div style={{ zIndex: 5 }} className="outerDiv centerer">
            <div
              id="I38:1057;30:79"
              style={{
                marginLeft: 0,
                marginRight: 0,
                flexGrow: 1,
                marginTop: 663,
                marginBottom: 0,
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <div>
                <div style={{}} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:79;30:25"
                    style={{
                      marginLeft: 0,
                      marginRight: 0,
                      flexGrow: 1,
                      marginTop: 0,
                      marginBottom: 0,
                      backgroundColor: "rgba(255, 255, 255, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div></div>
                  </div>
                </div>
                <div style={{ zIndex: 1 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:79;30:27"
                    style={{
                      marginLeft: 15,
                      marginRight: 588.0000305175781,
                      flexGrow: 1,
                      marginTop: 17,
                      marginBottom: 105.61333274841309,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Date Time
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 2 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:79;30:28"
                    style={{
                      marginLeft: 321,
                      marginRight: 15,
                      flexGrow: 1,
                      marginTop: 17,
                      marginBottom: 18,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "RIGHT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Message
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 3 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:79;30:29"
                    style={{
                      marginLeft: 15,
                      marginRight: 552,
                      flexGrow: 1,
                      marginTop: 61.8133544921875,
                      marginBottom: 64.85330963134766,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Name 1 paid{" "}
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 4 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:79;30:30"
                    style={{
                      marginLeft: 15,
                      marginRight: 605,
                      flexGrow: 1,
                      marginTop: 103,
                      marginBottom: 29.746673583984375,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Name 2
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 5 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:79;38:1352"
                    style={{
                      marginLeft: 73,
                      marginRight: 72,
                      flexGrow: 1,
                      marginTop: 151,
                      marginBottom: 0,
                      backgroundColor: "rgba(102, 0, 153, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div style={{ zIndex: 6 }} className="outerDiv centerer">
            <div
              id="I38:1057;30:85"
              style={{
                marginLeft: 0,
                marginRight: 0,
                flexGrow: 1,
                marginTop: 815,
                marginBottom: -152,
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <div>
                <div style={{}} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:85;30:25"
                    style={{
                      marginLeft: 0,
                      marginRight: 0,
                      flexGrow: 1,
                      marginTop: 0,
                      marginBottom: 0,
                      backgroundColor: "rgba(255, 255, 255, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div></div>
                  </div>
                </div>
                <div style={{ zIndex: 1 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:85;30:27"
                    style={{
                      marginLeft: 15,
                      marginRight: 588.0000305175781,
                      flexGrow: 1,
                      marginTop: 17,
                      marginBottom: 105.61333274841309,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Date Time
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 2 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:85;30:28"
                    style={{
                      marginLeft: 321,
                      marginRight: 15,
                      flexGrow: 1,
                      marginTop: 17,
                      marginBottom: 18,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "RIGHT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Message
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 3 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:85;30:29"
                    style={{
                      marginLeft: 15,
                      marginRight: 552,
                      flexGrow: 1,
                      marginTop: 61.8133544921875,
                      marginBottom: 64.85330963134766,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Name 1 paid{" "}
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 4 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:85;30:30"
                    style={{
                      marginLeft: 15,
                      marginRight: 605,
                      flexGrow: 1,
                      marginTop: 103,
                      marginBottom: 29.746673583984375,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Name 2
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 5 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:85;38:1352"
                    style={{
                      marginLeft: 73,
                      marginRight: 72,
                      flexGrow: 1,
                      marginTop: 151,
                      marginBottom: 0,
                      backgroundColor: "rgba(102, 0, 153, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div style={{ zIndex: 7 }} className="outerDiv centerer">
            <div
              id="I38:1057;30:91"
              style={{
                marginLeft: 0,
                marginRight: 0,
                flexGrow: 1,
                marginTop: 967,
                marginBottom: -304,
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <div>
                <div style={{}} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:91;30:25"
                    style={{
                      marginLeft: 0,
                      marginRight: 0,
                      flexGrow: 1,
                      marginTop: 0,
                      marginBottom: 0,
                      backgroundColor: "rgba(255, 255, 255, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div></div>
                  </div>
                </div>
                <div style={{ zIndex: 1 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:91;30:27"
                    style={{
                      marginLeft: 15,
                      marginRight: 588.0000305175781,
                      flexGrow: 1,
                      marginTop: 17,
                      marginBottom: 105.61333274841309,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Date Time
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 2 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:91;30:28"
                    style={{
                      marginLeft: 321,
                      marginRight: 15,
                      flexGrow: 1,
                      marginTop: 17,
                      marginBottom: 18,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "RIGHT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Message
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 3 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:91;30:29"
                    style={{
                      marginLeft: 15,
                      marginRight: 552,
                      flexGrow: 1,
                      marginTop: 61.8133544921875,
                      marginBottom: 64.85330963134766,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Name 1 paid{" "}
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 4 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:91;30:30"
                    style={{
                      marginLeft: 15,
                      marginRight: 605,
                      flexGrow: 1,
                      marginTop: 103,
                      marginBottom: 29.746673583984375,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Name 2
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 5 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:91;38:1352"
                    style={{
                      marginLeft: 73,
                      marginRight: 72,
                      flexGrow: 1,
                      marginTop: 151,
                      marginBottom: 0,
                      backgroundColor: "rgba(102, 0, 153, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div style={{ zIndex: 8 }} className="outerDiv centerer">
            <div
              id="I38:1057;30:97"
              style={{
                marginLeft: 0,
                marginRight: 0,
                flexGrow: 1,
                marginTop: 1119,
                marginBottom: -456,
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <div>
                <div style={{}} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:97;30:25"
                    style={{
                      marginLeft: 0,
                      marginRight: 0,
                      flexGrow: 1,
                      marginTop: 0,
                      marginBottom: 0,
                      backgroundColor: "rgba(255, 255, 255, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div></div>
                  </div>
                </div>
                <div style={{ zIndex: 1 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:97;30:27"
                    style={{
                      marginLeft: 15,
                      marginRight: 588.0000305175781,
                      flexGrow: 1,
                      marginTop: 17,
                      marginBottom: 105.61333274841309,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Date Time
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 2 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:97;30:28"
                    style={{
                      marginLeft: 321,
                      marginRight: 15,
                      flexGrow: 1,
                      marginTop: 17,
                      marginBottom: 18,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "RIGHT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Message
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 3 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:97;30:29"
                    style={{
                      marginLeft: 15,
                      marginRight: 552,
                      flexGrow: 1,
                      marginTop: 61.8133544921875,
                      marginBottom: 64.85330963134766,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Name 1 paid{" "}
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 4 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:97;30:30"
                    style={{
                      marginLeft: 15,
                      marginRight: 605,
                      flexGrow: 1,
                      marginTop: 103,
                      marginBottom: 29.746673583984375,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Name 2
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 5 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:97;38:1352"
                    style={{
                      marginLeft: 73,
                      marginRight: 72,
                      flexGrow: 1,
                      marginTop: 151,
                      marginBottom: 0,
                      backgroundColor: "rgba(102, 0, 153, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div style={{ zIndex: 9 }} className="outerDiv centerer">
            <div
              id="I38:1057;30:103"
              style={{
                marginLeft: 0,
                marginRight: 0,
                flexGrow: 1,
                marginTop: 1271,
                marginBottom: -608,
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <div>
                <div style={{}} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:103;30:25"
                    style={{
                      marginLeft: 0,
                      marginRight: 0,
                      flexGrow: 1,
                      marginTop: 0,
                      marginBottom: 0,
                      backgroundColor: "rgba(255, 255, 255, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div></div>
                  </div>
                </div>
                <div style={{ zIndex: 1 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:103;30:27"
                    style={{
                      marginLeft: 15,
                      marginRight: 588.0000305175781,
                      flexGrow: 1,
                      marginTop: 17,
                      marginBottom: 105.61333274841309,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Date Time
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 2 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:103;30:28"
                    style={{
                      marginLeft: 321,
                      marginRight: 15,
                      flexGrow: 1,
                      marginTop: 17,
                      marginBottom: 18,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "RIGHT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Message
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 3 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:103;30:29"
                    style={{
                      marginLeft: 15,
                      marginRight: 552,
                      flexGrow: 1,
                      marginTop: 61.8133544921875,
                      marginBottom: 64.85330963134766,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Name 1 paid{" "}
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 4 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:103;30:30"
                    style={{
                      marginLeft: 15,
                      marginRight: 605,
                      flexGrow: 1,
                      marginTop: 103,
                      marginBottom: 29.746673583984375,
                      color: "rgba(102, 0, 153, 1)",
                      fontSize: 24,
                      fontWeight: 400,
                      fontFamily: "Muli",
                      textAlign: "LEFT",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span style={{}} key="end">
                        Name 2
                      </span>
                    </div>
                  </div>
                </div>
                <div style={{ zIndex: 5 }} className="outerDiv centerer">
                  <div
                    id="I38:1057;30:103;38:1352"
                    style={{
                      marginLeft: 73,
                      marginRight: 72,
                      flexGrow: 1,
                      marginTop: 151,
                      marginBottom: 0,
                      backgroundColor: "rgba(102, 0, 153, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div></div>
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
