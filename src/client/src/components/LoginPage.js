import React from "react";
import CSimpleInput from "./CSimpleInput.js";
import CSingleButton from "./CSingleButton.js";
import {connect} from "react-redux";
import {changeLogin, changePage} from "../redux/actions";

class LoginPage extends React.Component {
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
    }
    renderCreate = () => {
        this.props.changePage("CreatePage")
    };

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
        const formData = new FormData();
        formData.append("username", this.state.request["username"]);
        formData.append("password", this.state.request["password"]);

        fetch(path, {
            method: "POST",
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Cache-Control": "no-cache",
            },
            body: formData,
            credentials: "include",
        })
            .then((res) => {
                return res.json();
            })
            .then(
                (result) => {
                    this.props.changeLogin(true);
                    this.props.changePage("MainPage")
                },
                (error) => {
                    // alert("failed to login");
                    this.setMessage("failed to login", "ERROR");
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
                    <div style={{zIndex: 1}} className="outerDiv centerer">
                        <div
                            id="35:300"
                            style={{
                                marginLeft: "26.3889%",
                                width: "47.22222222222222%",
                                height: "5%",
                                top: 44.1406 + 2 * 11.71875 + "%",
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
                    }}
                    onClick={this.renderCreate}
                >
                  Click Here to Make a New Account
                </span>
                            </div>
                        </div>
                    </div>
                    <div
                        style={{
                            zIndex: 2,
                        }}
                        className="outerDiv centerer"
                    >
                        <div
                            id="I76:29;97:225"
                            style={{
                                marginLeft: "40%",
                                marginRight: "40%",
                                flexGrow: 1,
                                top: "25%",
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
                </div>
            </form>
        );
    }
}

function mapStateToProps(state) {
    return {
        domainName: state.domainName
    }
}

export default connect(mapStateToProps, {changePage, changeLogin})(LoginPage);
