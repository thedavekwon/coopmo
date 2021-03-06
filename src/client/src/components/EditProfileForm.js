import React from "react";
//need to test request updating again
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import InputGroup from "react-bootstrap/InputGroup";
import FormAlert from "./FormAlert.js";
import bsCustomFileInput from "bs-custom-file-input";
import Image from "react-bootstrap/Image";
import defaultImg from "../shyam/shyam_close_cropped.jpg";
import {persistor} from "../redux/store";
import {changeRefreshState,} from "../redux/actions";
import {connect} from "react-redux";

class EditProfileForm extends React.Component {
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
        this.getUserDetails();
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
        .then((res) => {
          if (res.status === 302) {
            persistor.purge();
          }
          return res.json();
        })
        .then(
            (result) => {
              if (result.error !== null) {
                console.log(result.error);
                this.setMessage(result.error.message, "ERROR");
              } else {
                  this.setMessage("Successfully Changed Profile Picture!", "SUCCESS");
                  this.props.changeRefreshState("refreshProfilePic", true);
                  this.getProfilePic();

              }
            },
            (error) => {
                this.setMessage("ERROR sending request", "ERROR");
            }
        );
  };

    getUserDetails = () => {
        const path = this.props.domainName + "/user/getUserWithId";
        fetch(path, {
            method: "GET",
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Cache-Control": "no-cache",
            },
            credentials: "include",
        })
            .then((res) => {
                if (res.status === 302) {
                    persistor.purge();
                }
                return res.json();
            })
            .then(
                (result) => {
                    let newRequest = ({
                        newName: result.data.name,
                        newUsername: result.data.username,
                        newEmail: result.data.email,
                        newHandle: result.data.handle,
                    });
                    this.setState((state) => ({
                        request: newRequest,
                    }))
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
      } else if (res.status === 302) {
        persistor.purge();
      } else {
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
        .then((res) => {
          if (res.status === 302) {
            persistor.purge();
          }
          return res.json();
        })
        .then(
            (result) => {
              if (result.error !== null) {
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
                  type={value.name !== "Password" ? "text" : "password"}
                  defaultValue={this.state.request[value.valKey]}
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
                type={value.name !== "Password" ? "text" : "password"}
                placeholder={"Enter " + value.name}
                defaultValue={this.state.request[value.valKey]}
                onChange={this.handleChange}
            />
        );
      }
      return (
          <Form.Group controlId={value.valKey} key = {value.valKey}>
            <Form.Label style={{fontFamily: "Muli"}} column="lg">
              {value.name}
            </Form.Label>
            {inputBlock}
          </Form.Group>
      );
    });


    return (
        <div>
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
          <Form onSubmit={this.sendRequest}>
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
                  style={{fontFamily: "Muli", opacity: 1}}
                  size="lg"
                  data-browse="Upload Profile Picture"
                  className="custom-file-input"
                  custom
                  onChange={this.handleProfilePic}
              />
            </Form.Group>

            <Button className="submitButton" type="submit">
              Submit
            </Button>
          </Form>

        </div>
    );
  }
}


export default connect(null, {
    changeRefreshState,
})(EditProfileForm);