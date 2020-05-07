import React from "react";
//need to test request updating again
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import InputGroup from "react-bootstrap/InputGroup";
import FormAlert from "./FormAlert.js";

export default class CEditProfileForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      request: {
        newName: "",
        newUsername: "",
        newPassword: "",
        newEmail: "",
        newHandle: "",
      },
      respMessage: {
        messageType: "NONE",
        message: "",
      },
      showMessage: false,
    };
  }

  handleChange = (event) => {
    var newRequest = this.state.request;
    newRequest[event.target.id] = event.target.value;
    this.setState((state) => ({request: newRequest}));
  };

  setMessage(message, messageType) {
    var newRespMessage = this.state.respMessage;
    newRespMessage.message = message;
    newRespMessage.messageType = messageType;
    this.setState((state) => ({
      respMessage: newRespMessage,
      showMessage: true
    }));
  }

  sendRequest = (event) => {
    event.preventDefault();
    const path = this.props.domainName + "/user/editProfile";

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
                this.setMessage("Successfully Edited Profile!", "SUCCESS");
              }
            },
            (error) => {
              this.setMessage("ERROR sending request", "ERROR");
            }
        );
  };

  render() {
    const formEntries = [
      {
        name: "Name",
        valKey: "newName",
      },
      {
        name: "Username",
        valKey: "newUsername",
      },
      {
        name: "Password",
        valKey: "newPassword",
      },
      {
        name: "Email",
        valKey: "newEmail",
      },
      {
        name: "Handle",
        valKey: "newHandle",
      },
    ];

    let formBlocks = formEntries.map((value, index) => {
      let inputBlock;
      if (value.name == "Handle") {
        inputBlock = (
            <InputGroup>
              <InputGroup.Prepend>
                <InputGroup.Text id="inputGroupPrepend">@</InputGroup.Text>
              </InputGroup.Prepend>
              <Form.Control
                  required
                  style={{fontFamily: "Muli"}}
                  size="lg"
                  type={value.name != "Password" ? "text" : "password"}
                  placeholder={"Enter " + value.name}
                  onChange={this.handleChange}
              />
            </InputGroup>
        );
      } else {
        inputBlock = (
            <Form.Control
                required
                style={{fontFamily: "Muli"}}
                size="lg"
                type={value.name != "Password" ? "text" : "password"}
                placeholder={"Enter " + value.name}
                onChange={this.handleChange}
            />
        );
      }
      return (
          <Form.Group controlId={value.valKey}>
            <Form.Label style={{fontFamily: "Muli"}} column="lg">
              {value.name}
            </Form.Label>
            {inputBlock}
          </Form.Group>
      );
    });


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
            {formBlocks}
            <Button className="submitButton" type="submit">
              Submit
            </Button>
          </Form>
        </>
    );
  }
}
