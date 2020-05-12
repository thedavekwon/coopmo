import React from "react";
import MenuButton from "./MenuButton.js";
import {formatMoney} from "../functions/formatMoney";
import Image from "react-bootstrap/Image";
import defaultImg from "../shyam/shyam_close_cropped.jpg";
import NotificationBell from "./NotificationBell.js";
import {changeRefreshState} from "../redux/actions";
import {connect} from "react-redux";

class TitleBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            page: "main",
            profilePic: "",
            balance: 0,
        };
    }

    componentDidMount() {
        this.getProfilePic();
        this.getBalance();
    }

    componentDidUpdate() {
        if (this.props.refreshBalance) {
            this.getBalance();
            this.props.changeRefreshState("refreshBalance", false);
        }
    }

    getBalance = () => {
        const path = this.props.domainName + "/user/getUserBalance";
        fetch(path, {
            method: "GET",
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Cache-Control": "no-cache",
            },
            credentials: "include",
        })
            .then((res) => res.json())
            .then(
                (result) => {
                    console.log(result);
                    if (result.error != null) {
                        console.log(result.error);
                    } else {
                        this.setState((state) => ({
                            balance: result.data,
                        }));
                    }
                },
                (error) => {
                    console.log(error);
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
                let url = defaultImg;
                this.setState((state) => ({
                    profilePic: url,
                }));
            }
        });
    };

    render() {
        let backgroundColor =
            this.props.page === "main"
                ? "rgba(102, 0, 153, 1)"
                : "rgba(255, 255, 255, 1)";
        let textColor =
            this.props.page === "main"
                ? "rgba(255, 255, 255, 1)"
                : "rgba(102, 0, 153, 1)";
        let includeBalance;
        if (this.props.page === "main") {
            includeBalance = (
                <div
                    style={{
                        zIndex: 3,
                        width: "17.5%",
                        top: "25%",
                        height: "50%",
                        marginLeft: "80%",
                    }}
                    className="outerDiv centerer"
                >
                    <div className="innerDiv friendListPic vertCenterAndCut">
                        <Image src={this.state.profilePic} roundedCircle fluid/>
                    </div>

                    <div
                        style={{
                            flexGrow: 1,
                            backgroundColor: "rgba(102, 0, 153, 1)",
                            color: "rgba(255, 255, 255, 1)",
                            textAlign: "CENTER",
                        }}
                        className="innerDiv vertCenterAndCut textStyle"
                    >
                        <span>Balance : {formatMoney(this.state.balance)}</span>
                    </div>

                    <div className="innerDiv bellPng vertCenterAndCut">
                        <NotificationBell/>
                    </div>
                </div>
            );
        }
        return (
            <div
                style={{
                    zIndex: 0,
                }}
                className="outerDiv centerer"
            >
                <div
                    id="76:47"
                    style={{
                        width: "100%",
                        marginLeft: "0%",
                        height: "10.3515625%",
                        top: "0%",
                        backgroundColor: "rgba(0, 0, 0, 0)",
                    }}
                    className="innerDiv"
                >
                    <div style={{}} className="outerDiv centerer">
                        <div
                            id="I76:47;76:20"
                            style={{
                                flexGrow: 1,
                                backgroundColor: backgroundColor,
                            }}
                            className="innerDiv"
                        >
                            <div style={{}} className="outerDiv centerer">
                                <div
                                    style={{
                                        flexGrow: 1,
                                        backgroundColor: backgroundColor,
                                    }}
                                    className="innerDiv"
                                ></div>
                            </div>
                            <div
                                style={{
                                    flexGrow: 1,
                                    width: "10%",
                                    top: "25%",
                                    height: "50%",
                                    marginLeft: "2%",
                                }}
                                className="outerDiv centerer "
                            >
                                <div
                                    style={{
                                        flexGrow: 1,
                                        backgroundColor: "rgba(0, 0, 0, 1)",
                                    }}
                                    className="innerDiv vertCutAndCenter"
                                >
                                    <MenuButton
                                        {...this.props}
                                        backgroundColor={backgroundColor}
                                        textColor={textColor}
                                        page={this.props.page}
                                        domainName={this.props.domainName}
                                    />
                                </div>
                            </div>
                            <div
                                style={{
                                    zIndex: 2,
                                    top: "25%",
                                    height: "50%",
                                }}
                                className="outerDiv vertCenterAndCut"
                            >
                                <div
                                    style={{
                                        marginLeft: "40%",
                                        marginRight: "40%",
                                        flexGrow: 1,
                                        fontSize: 73,
                                        fontWeight: 700,
                                        textAlign: "CENTER",
                                    }}
                                    className="innerDiv vertCenterAndCut textStyle"
                                >
                                    <div>
                    <span
                        style={{
                            fontSize: 73,
                            fontStyle: "normal",
                            color: textColor,
                        }}
                        key="4"
                    >
                      Coop
                    </span>
                                        <span
                                            style={{
                                                fontSize: 73,
                                                fontStyle: "normal",
                                            }}
                                            key="end"
                                        >
                      mo
                    </span>
                                    </div>
                                </div>
                            </div>
                            {includeBalance}
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {
        refreshBalance: state.refreshBalance,
    };
}

export default connect(mapStateToProps, {changeRefreshState})(TitleBar);
