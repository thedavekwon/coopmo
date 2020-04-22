import React, {PureComponent} from "react";
import CSimpleInput from "./CSimpleInput.js";
import CSingleButton from "./CSingleButton.js";
//need to test request updating again
export default class CEditProfileForm extends PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            request: {
                userId: this.props.userId,
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
        };
    }

    handleChange = (key, value) => {
        var newRequest = this.state.request;
        newRequest[key] = value;
        this.setState((state) => ({request: newRequest}));
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
        const path = this.props.domainName + "/user/editProfile";
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
                        this.setMessage("Successfully Edited Profile!", "SUCCESS");
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
                    <div
                        style={{
                            zIndex: 1,
                        }}
                        className="outerDiv centerer"
                    >
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
                            <CSimpleInput
                                {...this.props}
                                name="Name"
                                valKey="newName"
                                onInput={this.handleChange}
                                nodeId="35:300"
                            />
                        </div>
                    </div>
                    <div
                        style={{
                            zIndex: 2,
                        }}
                        className="outerDiv centerer"
                    >
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
                            <CSimpleInput
                                {...this.props}
                                name="Username"
                                valKey="newUsername"
                                onInput={this.handleChange}
                                nodeId="35:280"
                            />
                        </div>
                    </div>
                    <div
                        style={{
                            zIndex: 3,
                        }}
                        className="outerDiv centerer"
                    >
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
                            <CSimpleInput
                                {...this.props}
                                name="Password"
                                valKey="newPassword"
                                onInput={this.handleChange}
                                nodeId="35:285"
                            />
                        </div>
                    </div>
                    <div
                        style={{
                            zIndex: 4,
                        }}
                        className="outerDiv centerer"
                    >
                        <div
                            id="35:290"
                            style={{
                                width: "47.22222222222222%",
                                marginLeft: "37.986111111111114%",
                                height: "11.71875%",
                                top: "54.6875%",
                                backgroundColor: "rgba(0, 0, 0, 0)",
                            }}
                            className="innerDiv"
                        >
                            <CSimpleInput
                                {...this.props}
                                name="Email"
                                valKey="newEmail"
                                onInput={this.handleChange}
                                nodeId="35:290"
                            />
                        </div>
                    </div>
                    <div
                        style={{
                            zIndex: 5,
                        }}
                        className="outerDiv centerer"
                    >
                        <div
                            id="35:295"
                            style={{
                                width: "47.22222222222222%",
                                marginLeft: "37.986111111111114%",
                                height: "11.71875%",
                                top: "66.40625%",
                                backgroundColor: "rgba(0, 0, 0, 0)",
                            }}
                            className="innerDiv"
                        >
                            <CSimpleInput
                                {...this.props}
                                name="Handle"
                                valKey="newHandle"
                                onInput={this.handleChange}
                                nodeId="35:295"
                            />
                        </div>
                    </div>

                    <div
                        style={{
                            zIndex: 7,
                        }}
                        className="outerDiv centerer"
                    >
                        <div
                            id="35:320"
                            style={{
                                width: "47.22222222222222%",
                                marginLeft: "37.986111111111114%",
                                height: "11.71875%",
                                top: "78.1251%",
                                backgroundColor: "rgba(0, 0, 0, 0)",
                                overflow: "hidden",
                            }}
                            className="innerDiv"
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
