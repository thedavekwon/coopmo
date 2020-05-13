import React from "react";
import MenuButton from "./MenuButton.js";
import {formatMoney} from "../functions/formatMoney";
import defaultImg from "../shyam/shyam_close_cropped.jpg";
import NotificationBell from "./NotificationBell.js";
import {changeRefreshState} from "../redux/actions";
import {connect} from "react-redux";
import {persistor} from "../redux/store";

class TitleBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            page: "main",
            profilePic: "",
            balance: null,
        };
        this.getProfilePic();
        this.getBalance();
    }

    componentDidUpdate() {
        if (this.props.refreshBalance) {
            this.getBalance();
            this.props.changeRefreshState("refreshBalance", false);
        }

        if (this.props.refreshProfilePic) {
            this.getProfilePic();
            this.props.changeRefreshState("refreshProfilePic", false);
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
            .then((res) => {
                if (res.status === 302) {
                    persistor.purge();
                }
                return res.json();
            })
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

    render() {
        let backgroundColor =
            this.props.page === "main"
                ? "rgba(102, 0, 153, 1)"
                : "rgba(255, 255, 255, 1)";
        let textColor =
            this.props.page === "main"
                ? "rgba(255, 255, 255, 1)"
                : "rgba(102, 0, 153, 1)";

        return (
            <div
                style={{
                    height: "100%",
                }}
            >
                <div
                    style={{
                        width: "100%",
                        display: "flex",
                        justifyContent: "space-between",
                        backgroundColor: backgroundColor,
                        alignItems: "center",
                    }}
                >
                    <div
                        style={{
                            flex: 2,
                        }}
                    >
                        <MenuButton
                            textColor={textColor}
                            page={this.props.page}
                            domainName={this.props.domainName}
                        />
                    </div>
                    <div style={{flex: 1}}>

                    </div>
                    <div
                        style={{
                            flex: 7,
                            textAlign: "center",
                        }}
                    >
            <span
                style={{
                    fontSize: 73,
                    fontStyle: "normal",
                    color: textColor,
                }}
            >
              Coop
            </span>
                        <span
                            style={{
                                fontSize: 73,
                                fontStyle: "normal",
                            }}
                        >
              mo
            </span>
                    </div>
                    <div
                        style={{
                            flex: 1,

                            textAlign: "center",
                        }}
                    >
                        <img
                            src={this.state.profilePic}
                            style={{
                                borderRadius: "50%",
                            }}
                            className="friendListPic"
                        />
                    </div>
                    <div
                        style={{
                            backgroundColor: backgroundColor,
                            color: textColor,
                            textAlign: "center",
                            lineHeight: 1,
                            flex: 1,
                        }}
                        className="textStyle"
                    >
          <span>
            Balance: {" "}
              {this.state.balance !== null
                  ? formatMoney(this.state.balance)
                  : "Loading..."}
          </span>
                    </div>
                    <div style={{flex: 1, textAlign: "center"}}>
                        <NotificationBell
                            color={this.props.page === "main" ? "white" : "purple"}
                        />
                    </div>
                </div>
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {
        refreshBalance: state.refreshBalance,
        refreshProfilePic: state.refreshProfilePic,
    };
}

export default connect(mapStateToProps, {changeRefreshState})(TitleBar);
