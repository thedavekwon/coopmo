import React from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Select from "react-select";

export default class CAddFriendForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      request: {
        friendId: "",
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
                  console.log(this.state.userListDrop);
                }
              }
            },
            (error) => {
              this.setMessage("ERROR sending request", "ERROR");
            }
        );
  };

  setMessage(message, messageType) {
    var newRespMessage = this.state.respMessage;
    newRespMessage.message = message;
    newRespMessage.messageType = messageType;
    this.setState((state) => ({
      respMessage: newRespMessage,
    }));
  }

  sendRequest = (event) => {
    event.preventDefault();
    if (this.state.users != null && this.state.users.length != 0) {
      var newRequest = this.state.request;
      newRequest.friendId = this.state.users[0].id;
      this.setState((state) => ({
        request: newRequest,
      }));
    }
    const path = this.props.domainName + "/user/sendOutRequest";
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
                this.setMessage("Successfully sent friend Request!", "SUCCESS");
              }
            },
            (error) => {
              this.setMessage("ERROR sending request", "ERROR");
            }
        );
  };

  render() {
    return (
        <Form onSubmit={this.sendRequest}>
          <Form.Group controlId="friendId">
            <Form.Label style={{fontFamily: "Muli"}} column="lg">
              Friend's Username
            </Form.Label>
            <Select
                isSearchable={true}
                onInputChange={this.handleInputChange}
                onChange={this.handleInputChange}
                options={this.state.userListDrop}
                placeholder="Search..."
            />
          </Form.Group>
          <Button className="submitButton" type="submit">
            Submit
          </Button>
        </Form>
    );
  }
}
