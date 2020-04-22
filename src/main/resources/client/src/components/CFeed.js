import React from "react";
import CFeedTab from "./CFeedTab.js";
import CFeedList from "./CFeedList.js";

export default class CFeed extends React.Component {
    constructor(props) {
        super(props);
        this.handleClick = this.handleClick.bind(this);
        this.state = {feedTab: "Me"};
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
            <div className="master" style={{backgroundColor: "rgba(0, 0, 0, 0)"}}>
                <div>
                    <div style={{}} className="outerDiv centerer">
            <div
              id="I38:1057;30:46"
              style={{
                marginLeft: 0,
                marginRight: 0,
                flexGrow: 1,
                height: "48px",
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
          <div
            style={{
              position: "relative",
              marginTop: 75,
            }}
          >
            <CFeedList
                feedTab={this.state.feedTab}
                domainName={this.props.domainName}
            />
          </div>
        </div>
      </div>
    );
  }
}
