import React from "react";
import {CFriendRequest} from "./CFriendRequest";

export default class CMenuIncomingFriendRequests extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            request: {},
            incomingRequests: [],
        };
        this.getIncomingRequests();
    }

    getIncomingRequests = () => {
        const path = this.props.domainName + "/user/getUserIncomingFriendRequest";
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
                            incomingRequests: result.data,
                        }));
                    }
                },
                (error) => {
                    console.log("error sending request");
                }
            )
      .then(() => {
        console.log(this.state.incomingRequests);
      });
  };

  render() {
    let friendRequests;
    if (this.state.incomingRequests != null) {
      friendRequests = (
        <form>
          {this.state.incomingRequests.map((incomingRequest, key) => {
            return (
              <div
                style={{
                  zIndex: key + 1,
                }}
                className="outerDiv centerer"
                key={incomingRequest.id}
              >
                <div
                    style={{
                        width: "100%",

                        height: (1 / 6) * 100 + "%",
                        top: (key / 6) * 100 + "%",
                        backgroundColor: "rgba(0, 0, 0, 0)",
                    }}
                    className="innerDiv"
                >
                    <CFriendRequest
                        name={incomingRequest.name}
                        friendId={incomingRequest.id}
                        key={key}
                        userId={this.props.userId}
                        domainName={this.props.domainName}
                    ></CFriendRequest>
                </div>
              </div>
            );
          })}
        </form>
      );
    }
    return (

        <div
          className="innerDiv centerer"
          style={{
            height: "100%",
            width: "100%",
            overflowY: "scroll",
            scrollbarColor: "rgba(102, 0, 153, 1)",
          }}
        >
          {friendRequests}
        </div>
    );
  }
}
