import React from "react";
import CSimpleInput from "./CSimpleInput";
import CSingleButton from "./CSingleButton.js";
//Need to test again
export default class CChangeBankAccounts extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      request: {
        routingNumber: 0,
        balance: 1000,
      },
      respMessage: {
        messageType: "NONE",
        message: "",
      },
    };
  }

  handleChange = (key, value) => {
    var newRequest = this.state.request;
    newRequest[key] = parseInt(value);
    this.setState((state) => ({request: newRequest}));
  };

  setMessage(message, messageType) {
    var newRespMessage = this.state.respMessage;
    newRespMessage.message = message;
    newRespMessage.messageType = messageType;
    this.setState((state) => ({
      respMessage: newRespMessage,
    }));
  }

  sendRequest = () => {
    const path = this.props.domainName + "/bank/createBankAccount";
    fetch(path, {
      method: "POST",
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Cache-Control": "no-cache",
        "Content-Type": "application/json",
      },
      credentials: "include",
      body: JSON.stringify(this.state.request),
    })
        .then((res) => res.json())
        .then(
            (result) => {
              if (result.error != null) {
                console.log(result.error);
                this.setMessage(result.error.message, "ERROR");
              } else {
                this.setMessage("Successfully Created a Bank Account!", "SUCCESS");
              }
            },
            (error) => {
              this.setMessage("ERROR sending request", "ERROR");
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
                    top: "19.53125%",
                  }}
                  className="innerDiv blocksOnForm"
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
                    top: "31.25%",
                  }}
                  className="innerDiv blocksOnForm"
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
