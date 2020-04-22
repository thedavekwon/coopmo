import React, {PureComponent} from "react";
import CSimpleInput from "./CSimpleInput.js";
import CSingleButton from "./CSingleButton.js";

export default class CLoginPage extends PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            request: {
                username: "",
                password: "",
            },
            respMessage: {
                messageType: "NONE",
                message: "",
            },
        };
        this.createUserRequest();
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
        const path = this.props.domainName + "/login";
        fetch(path, {
            method: "POST",
            mode: "cors",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(this.state.request),
        })
            .then((res) => res.json())
            .then(
                (result) => {
                    console.log(result);
                    if (result.error != null) {
                        console.log(result.error);
                        this.setMessage(result.error.message, "ERROR");
                    } else {
                        this.setMessage("Successfully Logged In!", "SUCCESS");
                    }
                },
                (error) => {
                    console.log(error)
                    this.setMessage("ERROR sending request", "ERROR");
                }
            );
    };

    createUserRequest = () => {
        const testRequest = {
            name: "test",
            username: "test",
            password: "test",
            email: "test@gmail.com",
            handle: "test"
        }
        const path = this.props.domainName + "/user/createUser";
        fetch(path, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(testRequest),
        })
            .then((res) => res.json())
            .then(
                (result) => {
                    console.log(result);
                    if (result.error != null) {
                        console.log(result.error);
                        this.setMessage(result.error.message, "ERROR");
                    } else {
                        this.setMessage("Successfully Logged In!", "SUCCESS");
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
                                marginLeft: "26.3889%",
                                width: "47.22222222222222%",
                                height: "11.71875%",
                                top: 44.1406 - 11.71875 + "%",
                                backgroundColor: "rgba(0, 0, 0, 0)",
                            }}
                            className="innerDiv"
                        >
                            <CSimpleInput
                                {...this.props}
                                name="Username"
                                valKey="username"
                                onInput={this.handleChange}
                                nodeId="35:300"
                            />
                        </div>
                    </div>
                    <div style={{zIndex: 2}} className="outerDiv centerer">
                        <div
                            id="35:280"
                            style={{
                                marginLeft: "26.3889%",
                                width: "47.22222222222222%",
                                height: "11.71875%",
                                top: "44.1406%",
                                backgroundColor: "rgba(0, 0, 0, 0)",
                            }}
                            className="innerDiv"
                        >
                            <CSimpleInput
                                {...this.props}
                                name="Password"
                                valKey="password"
                                onInput={this.handleChange}
                                nodeId="35:300"
                            />

                        </div>
                    </div>
                </div>
                <div style={{zIndex: 1}} className="outerDiv centerer">
                    <div
                        id="35:300"
                        style={{
                            marginLeft: "26.3889%",
                            width: "47.22222222222222%",
                            height: "11.71875%",
                            top: 44.1406 + 11.71875 + "%",
                            backgroundColor: "rgba(0, 0, 0, 0)",
                        }}
                        className="innerDiv"
                    >
                        <CSingleButton
                            text="Login"
                            onSub={this.sendRequest}
                            messageType={this.state.respMessage.messageType}
                            message={this.state.respMessage.message}
                        />

                    </div>
                </div>
            </form>
        );
    }
}
