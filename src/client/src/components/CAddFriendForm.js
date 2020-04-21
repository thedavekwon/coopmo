import React, { PureComponent } from "react";
import CSimpleInput from "./CSimpleInput.js";
import CSingleButton from "./CSingleButton.js";

export default class CAddFriendForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      username: this.props.username,
      friendUsername: "",
    };
  }

  handleChange = (key, value) => {
    this.setState((state) => ({ [key]: value }));
  };

  sendRequest = () => {
    console.log(this.state);
    const path = "http://localhost:8080/user/sendOutRequestWithUsername";
    fetch(path, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(this.state),
    })
      .then((res) => res.json())
      .then(
        (result) => {
          if (result.error != null) {
            console.log(result.error);
          }
        },
        (error) => {
          console.log("error sending request");
        }
      );
  };

  render() {
    return (
      <form>
        <div>
          <div style={{ zIndex: 1 }} className="outerDiv centerer">
            <div
              id="35:300"
              style={{
                width: "47.22222222222222%",
                marginLeft: "37.986111111111114%",
                height: "11.71875%",
                top: "19.53125%",
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <CSimpleInput
                {...this.props}
                name="Friend's Username"
                valKey="friendUsername"
                onInput={this.handleChange}
                nodeId="35:300"
              />
            </div>
          </div>
          <div style={{ zIndex: 2 }} className="outerDiv centerer">
            <div
              id="35:280"
              style={{
                width: "47.22222222222222%",
                marginLeft: "37.986111111111114%",
                height: "11.71875%",
                top: "31.25%",
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <CSingleButton
                text="Send Friend Request"
                onSub={this.sendRequest}
              />
            </div>
          </div>
        </div>
      </form>
    );
  }
}
