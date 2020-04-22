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
        const path =
            this.props.domainName + "/user/getUserIncomingFriendRequest?userId=" +
            this.props.userId;
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
          {this.state.incomingRequests.map((e, key) => {
            return (
              <div
                style={{
                  zIndex: key + 1,
                }}
                className="outerDiv centerer"
              >
                <div
                  id="35:300"
                  style={{
                    width: "100%",

                    height: (1 / 6) * 100 + "%",
                    top: (key / 6) * 100 + "%",
                    backgroundColor: "rgba(0, 0, 0, 0)",
                  }}
                  className="innerDiv"
                >
                  <CFriendRequest
                    name={e.name}
                    friendId={e.id}
                    key={key}
                    userId={this.props.userId}
                  >
                  </CFriendRequest>
                </div>
              </div>
            );
          })}
        </form>
      );
    }
    return (
      <div
        class="outerDiv centerer"
        style={{
          marginLeft: "37.986111111111114%",
          width: "47.22222222222222%",
          height: 11.71875 * 6 + "%",
          top: "19.53125%",
          zIndex: 9,
        }}
      >
        <div
          class="innerDiv centerer"
          style={{
            height: "100%",
            width: "100%",
            overflowY: "scroll",
            scrollbarColor: "rgba(102, 0, 153, 1)",
          }}
        >
          {friendRequests}
        </div>
      </div>
    );
  }
}
