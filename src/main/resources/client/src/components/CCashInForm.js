import React from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Select from "react-select";
import InputGroup from "react-bootstrap/InputGroup";
import FormAlert from "./FormAlert.js";

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
      showMessage: false,
    };
    this.getBankAccounts();
  }

  setMessage(message, messageType) {
    var newRespMessage = this.state.respMessage;
    newRespMessage.message = message;
    newRespMessage.messageType = messageType;
    this.setState((state) => ({
      respMessage: newRespMessage,
      showMessage: true
    }));
  }

  handleBankChange = (entry) => {
    var newRequest = this.state.request;
    newRequest.bankAccountId = entry.value;
    this.setState((state) => ({
      request: newRequest,
    }));
  };

  handleAmtChange = (event) => {
    var newRequest = this.state.request;
    newRequest[event.target.id] = parseInt(event.target.value * 100);
    this.setState((state) => ({request: newRequest}));
  };

  handleCashChange = (cash) => {
    console.log(cash);
    var newRequest = this.state.request;
    newRequest.type = cash.value;
    this.setState((state) => ({
      request: newRequest,
    }));
  };
  sendRequest = (event) => {
    event.preventDefault();
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
            if (result.data != null && result.data.length != 0) {
              var bankList = [];
              console.log(result.data);
              for (var i = 0; i < result.data.length; i++) {
                bankList = [
                  ...bankList,
                  {
                    value: result.data[i].id,
                    label: result.data[i].routingNumber,
                  },
                ];
              }
              this.setState((state) => ({
                bankAcctList: bankList,
              }));

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
    const cashType = [
      {
        value: "IN",
        label: "Cash In"
      },
      {
        value: "OUT",
        label: "Cash Out"
      }

    ]
    return (
        <>
          <FormAlert
              onClose={() => {
                this.setState((state) => ({
                  showMessage: false
                }))
              }}
              showMessage={this.state.showMessage}
              messageType={this.state.respMessage.messageType}
              message={this.state.respMessage.message}
          />
          <Form onSubmit={this.sendRequest}>
            <Form.Group controlId="bankAccountId">
              <Form.Label style={{fontFamily: "Muli"}} column="lg">
                Bank Account
              </Form.Label>
              <Select
                  isSearchable={true}
                  onChange={this.handleBankChange}
                  options={this.state.bankAcctList}
                  placeholder="Search..."
              />
            </Form.Group>
            <Form.Group controlId="type">
              <Form.Label style={{fontFamily: "Muli"}} column="lg">
                Cash In or Out?
              </Form.Label>
              <Select
                  isSearchable={true}
                  onChange={this.handleCashChange}
                  options={cashType}
                  placeholder="Search..."
              />
            </Form.Group>
            <Form.Group controlId="amount">
              <Form.Label style={{fontFamily: "Muli"}} column="lg">
                Amount
              </Form.Label>
              <InputGroup>
                <InputGroup.Prepend>
                  <InputGroup.Text id="inputGroupPrepend">$</InputGroup.Text>
                </InputGroup.Prepend>
                <Form.Control
                    required
                    style={{fontFamily: "Muli"}}
                    size="lg"
                    type="number"
                    step=".01"
                    min="0"
                    placeholder={"Enter Amount"}
                    onChange={this.handleAmtChange}
                />
              </InputGroup>
            </Form.Group>
            <Button className="submitButton" type="submit">
              Submit
            </Button>
          </Form>
        </>
    );
  }
}
