import React from "react";
import ReactDOM from "react-dom";
import CSimpleInput from "./CSimpleInput.js";
import CSingleButton from "./CSingleButton.js";
import CLoginPage from "./CLoginPage.js";


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

    renderCreate = () => {
        ReactDOM.render(<CLoginPage domainName={this.props.domainName}></CLoginPage>, document.getElementById("root"));
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
            headers: {
                "Content-Type": "application/json"
            },
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
                <div style={{zIndex: key + 1}} key={key} className="outerDiv centerer">
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
                <div style={{zIndex: 6}} className="outerDiv centerer">
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

                <div style={{zIndex: 7}} className="outerDiv centerer">
                    <div
                        id="35:300"
                        style={{
                            marginLeft: "26.3889%",
                            width: "47.22222222222222%",
                            height: "5%",
                            top: 87 + "%",
                            backgroundColor: "rgba(0, 0, 0, 0)",
                            color: "rgba(0, 0, 0, 1)",
                            fontSize: 73,
                            fontWeight: 700,
                            fontFamily: "Muli",
                            textAlign: "CENTER",
                            fontStyle: "normal",
                            lineHeight: "125%",
                            letterSpacing: "0px",
                        }}
                        className="innerDiv"

                    >
                        <div>
                            <span
                                style={{
                                    fontSize: 16,
                                    fontStyle: "normal",
                                    lineHeight: "NaN%",
                                    letterSpacing: "undefinedpx",
                                    color: "rgba(102, 0, 153, 1)",
                                }
                                }
                                onClick={this.renderCreate}>
                                Click Here to Return to the Login Screen
                            </span>
                        </div>
                    </div>
                </div>
                <div
                    style={{
                        zIndex: 8,
                    }}
                    className="outerDiv centerer"
                >
                    <div
                        id="I76:29;97:225"
                        style={{
                            marginLeft: "40%",
                            marginRight: "40%",
                            flexGrow: 1,
                            top: "12%",
                            height: 73,
                            color: "rgba(0, 0, 0, 1)",
                            fontSize: 73,
                            fontWeight: 700,
                            fontFamily: "Muli",
                            textAlign: "CENTER",
                            fontStyle: "normal",
                            lineHeight: "125%",
                            letterSpacing: "0px",
                        }}
                        className="innerDiv"
                    >
                        <div>
                      <span
                          style={{
                              fontSize: 73,
                              fontStyle: "normal",
                              lineHeight: "NaN%",
                              letterSpacing: "undefinedpx",
                              color: "rgba(102, 0, 153, 1)",
                          }}
                          key="4"
                      >
                        Coop
                      </span>
                            <span
                                style={{
                                    fontSize: 73,
                                    fontStyle: "normal",
                                    lineHeight: "NaN%",
                                    letterSpacing: "undefinedpx",
                                }}
                                key="end"
                            >
                        mo
                      </span>
                        </div>
                    </div>
                </div>
            </form>
        );
    }
}
