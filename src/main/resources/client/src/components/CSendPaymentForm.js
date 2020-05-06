import React from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import InputGroup from "react-bootstrap/InputGroup";
import Select from "react-select";
import FormAlert from "./FormAlert.js";

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
      showMessage: false,
    };
  }

  setMessage(message, messageType) {
    var newRespMessage = this.state.respMessage;
    newRespMessage.message = message;
    newRespMessage.messageType = messageType;
    this.setState((state) => ({
      respMessage: newRespMessage,
      showMessage: true,
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
                        value: result.data[i].id,
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

  handlePayChange = (paymentType) => {
    var newRequest = this.state.request;
    newRequest.type = paymentType.value;
    this.setState((state) => ({
      request: newRequest,
    }));
  };

  handleAmtChange = (event) => {
    var newRequest = this.state.request;
    newRequest[event.target.id] = parseInt(event.target.value * 100);
    this.setState((state) => ({request: newRequest}));
  };

  handleCommentChange = (event) => {
    var newRequest = this.state.request;
    newRequest[event.target.id] = event.target.value;
    this.setState((state) => ({request: newRequest}));
  };

  handleSelect = (event) => {
    var newRequest = this.state.request;
    newRequest.toUserId = event.value;
    this.setState((state) => ({
      request: newRequest,
    }));
  }

  sendRequest = (event) => {
    event.preventDefault();
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
        value: "PRIVATE",
        label: "Private",
      },
      {
        value: "FRIEND",
        label: "Friend",
      },
      {
        value: "PUBLIC",
        label: "Public",
      },
    ];

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
            <Form.Group controlId="friendId">
              <Form.Label style={{fontFamily: "Muli"}} column="lg">
                Friend's Username
              </Form.Label>
              <Select
                  isSearchable={true}
                  onInputChange={this.handleInputChange}
                  onChange={this.handleSelect}
                  options={this.state.userListDrop}
                  placeholder="Search..."
              />
            </Form.Group>
            <Form.Group controlId="type">
              <Form.Label style={{fontFamily: "Muli"}} column="lg">
                Privacy Level
              </Form.Label>
              <Select
                  isSearchable={true}
                  onChange={this.handlePayChange}
                  options={paymentTypes}
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
            <Form.Group controlId="comment">
              <Form.Label>Comment</Form.Label>
              <Form.Control
                  as="textarea"
                  rows="3"
                  required
                  onChange={this.handleCommentChange}
              />
            </Form.Group>
            <Button className="submitButton" type="submit">
              Submit
            </Button>
          </Form>
        </>
    );
  }
}
