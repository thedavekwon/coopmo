import React, { PureComponent } from "react";
import CSimpleInput from "./CSimpleInput";
import CSingleButton from "./CSingleButton.js";

export default class CChangeBankAccounts extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      userId: this.props.userId,
      routingNumber: 0,
      balance: 1000,
    };
  }

  handleChange = (key, value) => {
    this.setState((state) => ({ [key]: parseInt(value) }));
  };

  sendRequest = () => {
    console.log(this.state);
    const path = "http://localhost:8080/bank/createBankAccount";
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
        <div
          style={{
            overflow: "auto",
          }}
        >
          <div
            style={{
              zIndex: 1,
            }}
            className="outerDiv centerer"
          >
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
                name="Routing Number"
                valKey="routingNumber"
                onInput={this.handleChange}
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
                {...this.props}
                text="Submit"
                onSub={this.sendRequest}
                nodeId="35:320"
              />
            </div>
          </div>
        </div>
      </form>
    );
  }
}
