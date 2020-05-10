import React from "react";
import {MINH_PIC_LINK} from "../minhPic.js";

export default class CFriendsList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            friendsList: [],
        };
        this.getFriendsList();
    }

    getFriendsList = () => {
        const path = this.props.domainName + "/user/getUserFriendList";
        fetch(path, {
            method: "GET",
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Cache-Control": "no-cache",
            },
            credentials: "include",
        })
            .then((res) => res.json())
            .then(
                (result) => {
                    console.log(result);
                    if (result.error != null) {
                        console.log(result.error);
                    } else {
                        this.setState((state) => ({
                            friendsList: result.data,
                        }));
                    }
                },
                (error) => {
                    console.log("error sending request");
                }
            )
      .then(() => {
        console.log(this.state.friendsList);
      });
  };

  render() {
    let friends;
    if (this.state.friendsList != null) {
      friends = (
        <div>
          {this.state.friendsList.map((friend, key) => {
            return (
              <div style={{ zIndex: key + 1 }} className="outerDiv centerer" key={friend}>
                <div
                  style={{
                    marginLeft: 0,
                    marginRight: 0,
                    flexGrow: 1,
                    height: "10%",
                    top: (key + 1) * 10 + "%",
                    backgroundColor: "rgba(0, 0, 0, 0)",
                    overflow: "hidden",
                  }}
                  className="innerDiv"
                >
                  <div>
                    <div style={{}} className="outerDiv centerer">
                      <div
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
                        style={{
                          marginLeft: 16.92724609375,
                          marginRight: "80%",
                          flexGrow: 1,
                          marginTop: 2,
                          marginBottom: 3,
                        }}
                        className="innerDiv"
                      >
                        <div>
                          <img src={MINH_PIC_LINK} style={{
                            width: 50,
                            height: 50,
                            borderRadius:"50%"
                            }} />
                        </div>
                      </div>
                    </div>
                    <div style={{ zIndex: 2 }} className="outerDiv centerer">
                      <div
                        style={{
                          marginLeft: 73.054443359375,
                          marginRight: 9.80010986328125,
                          flexGrow: 1,
                          marginTop: 13,
                          marginBottom: 13,
                          color: "rgba(0, 0, 0, 1)",
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
                            {friend.name}
                          </span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      );
    }

    return (
      <div className="master" style={{ backgroundColor: "rgba(0, 0, 0, 0)" }}>
        <div>
          <div style={{}} className="outerDiv centerer">
            <div
              style={{
                  flexGrow: 1,
                  height: "10%",
                  borderRadius: "10px 10px 0px 0px",
                  backgroundColor: "rgba(102, 0, 153, 1)",
              }}
              className="innerDiv"
            >
              <div></div>
            </div>
          </div>
          {friends}

          <div style={{ zIndex: 15 }} className="outerDiv centerer">
            <div
              style={{
                top: "3.5%",
                height: "3%",
                width: "100%",
                color: "rgba(255, 255, 255, 1)",
                fontSize: 24,
                fontWeight: 400,
                fontFamily: "Muli",
                textAlign: "CENTER",
                fontStyle: "normal",
                lineHeight: "125%",
                letterSpacing: "0px",
              }}
              className="innerDiv"
            >
              <div>
                <span style={{}} key="end">
                  Friends List
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
