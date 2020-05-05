import React from "react";
import CSingleButton from "./CSingleButton.js";
import CInputwithanIcon from "./CInputwithanIcon.js";
import CDropdown from "./CDropdown.js";
import CTypableDropdown from "./CTypableDropdown.js";

export default class CSendPaymentForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
        request: {
            toUserId: "",
            amount: 0,
            type: "PRIVATE",
            comment: "YEEE",
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
        userListDrop: [],
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

    handleInputChange = (value) => {
        if (value == "") return;
        var newRequest = this.state.findUserRequest;
        newRequest.match = value;
        this.setState((state) => ({
            findUserRequest: newRequest,
        }));

        const findUserPath = this.props.domainName + "/user/findUsers";
        fetch(findUserPath, {
            method: "POST",
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Cache-Control": "no-cache",
                "Content-Type": "application/json",
            },
            credentials: "include",
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

                        if (result.data != null) {
                            var userList = [];
                            for (var i = 0; i < result.data.length; i++) {
                                userList = [
                                    ...userList,
                                    {
                                        value: result.data[i].name,
                                        label: result.data[i].name,
                                    },
                                ];
                            }

                            this.setState((state) => ({
                                userListDrop: userList,
                            }));
                        }
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
      if (this.state.users != null) {
          if (this.state.users.length != 0)
              newRequest.toUserId = this.state.users[0].id;
          else {
              this.setMessage("No User starting with that username found", "ERROR");
              return;
          }
      }
      this.setState((state) => ({
          request: newRequest,
      }));

      console.log(this.state.request);
      const path = this.props.domainName + "/pay/createPayment";
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
            <div style={{zIndex: 1}} className="outerDiv centerer">
                <div
                    id="35:300"
                    style={{
                        top: "19.53125%",
                    }}
                    className="innerDiv blocksOnForm"
                >
                    <CTypableDropdown
                        name="To Who"
                        valKey="textInput"
                        list={this.state.userListDrop}
                        handleChange={this.handleInputChange}
                        value={this.state.request.match}
                    />
                </div>
            </div>
            <div style={{zIndex: 2}} className="outerDiv centerer">
                <div
                    id="35:280"
                    style={{
                        top: "31.25%",
                    }}
                    className="innerDiv blocksOnForm"
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
                      top: "42.96875%",
                  }}
                  className="innerDiv blocksOnForm"
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
                      top: "54.6875%",
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
