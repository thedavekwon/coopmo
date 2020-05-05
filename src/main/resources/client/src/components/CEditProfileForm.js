import React from "react";
import CSimpleInput from "./CSimpleInput.js";
import CSingleButton from "./CSingleButton.js";
//need to test request updating again
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
        const formEntries = [{
            name: "Name",
            valKey: "newName",
        },
            {
                name: "Username",
                valKey: "newUsername"
            },
            {
                name: "Password",
                valKey: "newPassword"
            },
            {
                name: "Email",
                valKey: "newEmail"
            },
            {
                name: "Handle",
                valKey: "newHandle"
            }]

        let formBlocks = formEntries.map((value, index) => {
            return (
                <div
                    style={{
                        zIndex: index + 1,
                    }}
                    className="outerDiv centerer"
                >
                    <div
                        id="35:300"
                        style={{
                            top: 19.53125 + 11.71875 * index + "%",
                        }}
                        className="innerDiv blocksOnForm"
                    >
                        <CSimpleInput
                            {...this.props}
                            name={value.name}
                            valKey={value.valKey}
                            onInput={this.handleChange}
                            nodeId="35:300"
                        />
                    </div>
                </div>
            )
        })

        return (
            <form>
                <div>
                    {formBlocks}

                    <div
                        style={{
                            zIndex: 7,
                        }}
                        className="outerDiv centerer"
                    >
                        <div
                            id="35:320"
                            style={{
                                top: "78.1251%",
                                overflow: "hidden",
                            }}
                            className="innerDiv blocksOnForm"
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
