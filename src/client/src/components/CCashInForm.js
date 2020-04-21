import React, { PureComponent } from "react";
import CSimpleInput from "./CSimpleInput";
import CSingleButton from "./CSingleButton.js";
import CInputwithanIcon from "./CInputwithanIcon.js";
import CDropdown from "./CDropdown.js";

export default class CCashInForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      bankAcctList: [],
      request: {
        userId: this.props.userId,
        bankAccountId: "",
        amount: 0,
        type: "IN",
      },
    };
    this.getBankAccounts();
  }

  handleBankChange = (bankId) => {
    var newRequest = this.state.request;
    newRequest.bankAccountId = bankId;
    this.setState((state) => ({
      request: newRequest,
    }));
  };

  handleAmtChange = (amount) => {
    var newRequest = this.state.request;
    newRequest.amount = parseInt(amount);
    this.setState((state) => ({
      request: newRequest,
    }));
  };
  sendRequest = () => {
    const path = "http://localhost:8080/cash/createCash";
    console.log(this.state.request);
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
          }
        },
        (error) => {
          console.log("error sending request");
        }
      );
  };

  getBankAccounts = () => {
    const path =
      "http://localhost:8080/user/getUserBankAccountList?userId=" +
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
          console.log("error sending request");
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
                width: "47.22222222222222%",
                marginLeft: "37.986111111111114%",
                height: "11.71875%",
                top: "19.53125%",
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
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
                width: "47.22222222222222%",
                marginLeft: "37.986111111111114%",
                height: "11.71875%",
                top: "31.25%",
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <CInputwithanIcon onInput name="Amount to Withdraw" />
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
