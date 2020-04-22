import React from "react";
import CSimpleInput from "./CSimpleInput.js";
import CSingleButton from "./CSingleButton.js";


export default class CCreateUserPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            request: {
                name: "",
                username: "",
                password: "",
                email: "",
                handle: ""
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


    createUserRequest = () => {

        const path = this.props.domainName + "/user/createUser";
        fetch(path, {
            method: "POST",
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
                        this.setMessage("Successfully Created an Account!", "SUCCESS");
                    }
                },
                (error) => {
                    this.setMessage("ERROR sending request", "ERROR");
                }
            );
    };

    render() {
        const entries = [
            {
                name: "Name",
                valKey: "name"
            },
            {
                name: "Username",
                valKey: "username"
            },
            {
                name: "Password",
                valKey: "password"
            },
            {
                name: "Email",
                valKey: "email"
            },
            {
                name: "Handle",
                valKey: "handle"
            },
        ]

        let inputs = entries.map((e, key) => {
            return (
                <div style={{zIndex: key + 1}} className="outerDiv centerer">
                    <div
                        id="35:300"
                        style={{
                            marginLeft: "26.3889%",
                            width: "47.22222222222222%",
                            height: "11.71875%",
                            top: 50 - 11.71875 * (entries.length - 2 * key) / 2 + "%",
                            backgroundColor: "rgba(0, 0, 0, 0)",
                        }}
                        className="innerDiv"
                    >
                        <CSimpleInput
                            {...this.props}
                            name={e.name}
                            valKey={e.valKey}
                            onInput={this.handleChange}
                            nodeId="35:300"
                        />
                    </div>
                </div>
            );
        })


        return (
            <form>
                {inputs}
                <div style={{zIndex: 1}} className="outerDiv centerer">
                    <div
                        id="35:300"
                        style={{
                            marginLeft: "26.3889%",
                            width: "47.22222222222222%",
                            height: "11.71875%",
                            top: 50 + 11.71875 * (entries.length) / 2 + "%",
                            backgroundColor: "rgba(0, 0, 0, 0)",
                        }}
                        className="innerDiv"
                    >
                        <CSingleButton {...this.props}
                                       text="Create User"
                                       nodeId="35:320"
                                       onSub={this.createUserRequest}
                                       messageType={this.state.respMessage.messageType}
                                       message={this.state.respMessage.message}/>
                    </div>
                </div>
            </form>
        );
    }
}
