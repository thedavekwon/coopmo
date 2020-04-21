import React from "react";
import CSimpleInput from "./CSimpleInput";
import CSingleButton from "./CSingleButton.js";
import CInputwithanIcon from "./CInputwithanIcon.js";
import CDropdown from "./CDropdown.js";

export default class CSendPaymentForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      request: {
        fromUserId: this.props.userId,
        toUserId: "",
        amount: 0,
        type: "PRIVATE",
      },
      findUserRequest: {
        match: "",
        type: "USERNAME",
      },
      users: [],

      respMessage: {
        messageType: "NONE",
        message: "",
      },
    };
  }

  setMessage(message, messageType) {
    var newRespMessage = this.state.respMessage;
    newRespMessage.message = message;
    newRespMessage.messageType = messageType;
    this.setState((state) => ({
      respMessage: newRespMessage,
    }));
  }

  handleInputChange = (key, value) => {
    var newRequest = this.state.findUserRequest;
    newRequest.match = value;
    this.setState((state) => ({
      findUserRequest: newRequest,
    }));
    const findUserPath = "http://localhost:8080/user/findUsers";
    fetch(findUserPath, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(this.state.findUserRequest),
    })
      .then((res) => res.json())
      .then(
        (result) => {
          console.log(result);
          if (result.error != null) {
            this.setMessage(result.error.message, "ERROR");
          } else {
            this.setState((state) => ({
              users: result.data,
            }));
          }
        },
        (error) => {
          this.setMessage("ERROR sending request", "ERROR");
        }
      );
  };

  handleChange = (paymentType) => {
    var newRequest = this.state.request;
    newRequest.type = paymentType;
    this.setState((state) => ({
      request: newRequest,
    }));
  };

  handleAmtChange = (valKey, amount) => {
    var newRequest = this.state.request;
    newRequest.amount = parseInt(amount);
    this.setState((state) => ({
      request: newRequest,
    }));
  };

  sendRequest = () => {
    var newRequest = this.state.request;
    if (this.state.users.length != 0)
      newRequest.toUserId = this.state.users[0].id;
    else {
      this.setMessage("No User starting with that username found", "ERROR");
      return;
    }
    this.setState((state) => ({
      request: newRequest,
    }));

    console.log(this.state.request);
    const path = "http://localhost:8080/pay/createPayment";
    fetch(path, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(this.state.request),
    })
      .then((res) => res.json())
      .then(
        (result) => {
          if (result.error != null) {
            console.log(result.error);
            this.setMessage(result.error.message, "ERROR");
          } else {
            this.setMessage("Successfully sent payment!", "SUCCESS");
          }
        },
        (error) => {
          this.setMessage("ERROR sending request", "ERROR");
        }
      );
  };

  render() {
    let paymentTypes = [
      {
        val: "PRIVATE",
        name: "Private",
      },
      {
        val: "FRIEND",
        name: "Friend",
      },
      {
        val: "PUBLIC",
        name: "Public",
      },
    ];

    return (
      <form>
        <div style={{ overflow: "auto" }}>
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
                name="To Who"
                valKey="textInput"
                onInput={this.handleInputChange}
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
              <CInputwithanIcon
                onInput={this.handleAmtChange}
                name="Payment Amount"
              />
            </div>
          </div>
          <div style={{ zIndex: 3 }} className="outerDiv centerer">
            <div
              id="35:285"
              style={{
                width: "47.22222222222222%",
                marginLeft: "37.986111111111114%",
                height: "11.71875%",
                top: "42.96875%",
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <CDropdown
                {...this.props}
                name="Type of Payment"
                dropType="payment"
                handleChange={this.handleChange}
                paymentTypes={paymentTypes}
                nodeId="35:320"
              />
            </div>
          </div>
          <div style={{ zIndex: 3 }} className="outerDiv centerer">
            <div
              id="35:285"
              style={{
                width: "47.22222222222222%",
                marginLeft: "37.986111111111114%",
                height: "11.71875%",
                top: "54.6875%",
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <CSingleButton
                {...this.props}
                text="Submit"
                onSub={this.sendRequest}
                nodeId="35:320"
                messageType={this.state.respMessage.messageType}
                message={this.state.respMessage.message}
              />
            </div>
          </div>
        </div>
      </form>
    );
  }
}
