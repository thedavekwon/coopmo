import React from "react";
import CSingleButton from "./CSingleButton.js";
import CTypableDropdown from "./CTypableDropdown.js"

export default class CAddFriendForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            request: {
                userId: this.props.userId,
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
        };
    }

    handleInputChange = (value) => {
        var newRequest = this.state.findUserRequest;
        newRequest.match = value;
        this.setState((state) => ({
            findUserRequest: newRequest,
        }));
        const findUserPath = this.props.domainName + "/user/findUsers";
        fetch(findUserPath, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
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

    sendRequest = () => {
        if (this.state.users != null && this.state.users.length != 0) {
            var newRequest = this.state.request;
            newRequest.friendId = this.state.users[0].id;
            this.setState((state) => ({
                request: newRequest
            }))
        }
        const path = this.props.domainName + "/user/sendOutRequest";
        fetch(path, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
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
            <form>
                <div>
                    <div style={{zIndex: 1}} className="outerDiv centerer">
                        <div
                            id="35:300"
                            style={{
                                width: "47.22222222222222%",
                                marginLeft: "37.986111111111114%",
                                height: "11.71875%",
                                top: "19.53125%",
                                backgroundColor: "rgba(0, 0, 0, 0)",
                                overflowY: "visible"
                            }}
                            className="innerDiv"
                        >
                            <CTypableDropdown
                                name="Friend's Username"
                                valKey="friendUsername"
                                //list = {this.state.users}
                                list={[
                                    {value: 'chocolate', label: 'Chocolate'},
                                    {value: 'strawberry', label: 'Strawberry'},
                                    {value: 'vanilla', label: 'Vanilla'},
                                ]}
                                handleChange={this.handleInputChange}
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
                <CSingleButton
                    text="Send Friend Request"
                    onSub={this.sendRequest}
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
