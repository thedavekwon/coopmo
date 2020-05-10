import React from "react";
//need to test request updating again
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import InputGroup from "react-bootstrap/InputGroup";
import FormAlert from "./FormAlert.js";
import bsCustomFileInput from "bs-custom-file-input";
import Image from "react-bootstrap/Image";
import defaultImg from "../shyam/shyam_close_cropped.jpg";

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
      profilePic: "",
    };
  }

  componentDidMount() {
    bsCustomFileInput.init();
    this.getProfilePic();
  }

  handleChange = (event) => {
    var newRequest = this.state.request;
    newRequest[event.target.id] = event.target.value;
    this.setState((state) => ({request: newRequest}));
  };

  //https://flaviocopes.com/how-to-upload-files-fetch/
  handleProfilePic = (event) => {
    event.preventDefault();
    console.log(event.target.files);
    const formData = new FormData();
    formData.append("file", event.target.files[0]);
    const path = this.props.domainName + "/user/uploadProfilePic";

    fetch(path, {
      method: "POST",
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Cache-Control": "no-cache",
      },
      credentials: "include",
      body: formData,
    })
        .then((res) => res.json())
        .then(
            (result) => {
              if (result.error != null) {
                console.log(result.error);
                this.setMessage(result.error.message, "ERROR");
              } else {
                this.setMessage("Successfully Changed Profile Picture!", "SUCCESS");
                this.getProfilePic();
              }
            },
            (error) => {
              this.setMessage("ERROR sending request", "ERROR");
            }
        );
  };

  getProfilePic = () => {
    const path = this.props.domainName + "/user/getProfilePic";
    fetch(path, {
      method: "GET",
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Cache-Control": "no-cache",
      },
      credentials: "include",
    }).then((res) => {
      console.log("status" + res.status);
      if (res.status === 200) {
        res.blob().then((blob) => {
          let url = window.URL.createObjectURL(blob);
          this.setState((state) => ({
            profilePic: url,
          }));
        });
      } else {
        console.log("here");
        let url = defaultImg;
        this.setState((state) => ({
          profilePic: url,
        }));
      }
    });
  };

  setMessage(message, messageType) {
    var newRespMessage = this.state.respMessage;
    newRespMessage.message = message;
    newRespMessage.messageType = messageType;
    this.setState((state) => ({
      respMessage: newRespMessage,
      showMessage: true,
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
      if (value.name === "Handle") {
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
          <Form onSubmit={this.sendRequest} fluid>
            {formBlocks}

            <Form.Group>
              <Form.Label style={{fontFamily: "Muli"}} column="lg">
                Upload New Profile Picture
              </Form.Label>
              <div className="pic">
                <Image
                    src={this.state.profilePic}
                    roundedCircle
                    fluid
                    style={{height: "100%", width: "100%"}}
                />
              </div>

              <Form.File
                  id="profilePic"
                  label="Browse..."
                  style={{fontFamily: "Muli"}}
                  size="lg"
                  data-browse="Upload Profile Picture"
                  class="custom-file-input"
                  custom
                  onChange={this.handleProfilePic}
              />
            </Form.Group>

            <Button className="submitButton" type="submit">
              Submit
            </Button>
          </Form>
          <FormAlert
              onClose={() => {
                this.setState((state) => ({
                  showMessage: false,
                }));
              }}
              showMessage={this.state.showMessage}
              messageType={this.state.respMessage.messageType}
              message={this.state.respMessage.message}
          />
        </>
    );
  }
}
