import React from "react";
import CSingleButton from "./CSingleButton.js";
import CInputwithanIcon from "./CInputwithanIcon.js";
import CDropdown from "./CDropdown.js";

export default class CCashInForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      bankAcctList: [],
      request: {
        bankAccountId: "",
        amount: "",
        type: "IN",
      },
      respMessage: {
        messageType: "NONE",
        message: "",
      },
    };
    this.getBankAccounts();
  }

  setMessage(message, messageType) {
    var newRespMessage = this.state.respMessage;
    newRespMessage.message = message;
    newRespMessage.messageType = messageType;
    this.setState((state) => ({
      respMessage: newRespMessage,
    }));
  }

  handleBankChange = (bankId) => {
    var newRequest = this.state.request;
    newRequest.bankAccountId = bankId;
    this.setState((state) => ({
      request: newRequest,
    }));
  };

  handleAmtChange = (key, amount) => {
    var newRequest = this.state.request;
    newRequest.amount = amount;
    this.setState((state) => ({
      request: newRequest,
    }));
  };
  sendRequest = () => {
    const path = this.props.domainName + "/cash/createCash";
    console.log(this.state.request);
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
              this.setMessage("Successfully Cashed In!", "SUCCESS");
            }
          },
          (error) => {
            this.setMessage("ERROR sending request", "ERROR");
          }
      );
  };

  getBankAccounts = () => {
    const path = this.props.domainName + "/user/getUserBankAccountList";
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
          if (result.error != null) {
            console.log(result.error);
            this.setMessage(result.error.message, "ERROR");
          } else {
            this.setState((state) => ({
              bankAcctList: result.data,
            }));
            if (result.data != null && result.data.length != 0) {
              var newRequest = this.state.request;
              newRequest.bankAccountId = result.data[0].id;
              this.setState((state) => ({
                request: newRequest,
              }));
            }
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
        <div style={{ overflow: "auto" }}>
          <div style={{ zIndex: 1 }} className="outerDiv centerer">
            <div
                id="35:300"
                style={{
                  top: "19.53125%",
                }}
                className="innerDiv blocksOnForm"
            >
              <CDropdown
                  name="Bank Account to Withdraw From"
                  bankAcctList={this.state.bankAcctList}
                  handleChange={this.handleBankChange}
                  dropType="bank"
                  request={this.state.request}
              />
            </div>
          </div>
          <div style={{ zIndex: 2 }} className="outerDiv centerer">
            <div
                id="35:280"
                style={{
                  top: "31.25%",
                }}
                className="innerDiv blocksOnForm"
            >
              <CInputwithanIcon
                  onInput={this.handleAmtChange}
                  valKey="amount"
                  name="Amount to Withdraw"
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
