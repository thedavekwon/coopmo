import React from "react";
import FriendsList from "./FriendsList.js";
import CFeed from "./CFeed.js";
import TitleBar from "./TitleBar.js";
import {connect} from "react-redux";

class MainPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            feedTab: "Me",
            balance: 0,
        };
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

    componentDidMount() {
        this.getBalance();
    }

    render() {
        return (
            <div
                className="master"
                style={{
                    backgroundColor: "rgba(255, 255, 255, 1)",
                }}
            >
                <TitleBar
                        page="main"
                        balance={this.state.balance}
                        domainName={this.props.domainName}
                    />
                <div style = {{
                    position: "absolute",
                    display: "flex",
                    width: "100%",
                    minHeight: "100%",
                    justifyContent: "space-around"
                }}>
                    <div style={{flex: 1}}></div>
                    <div style={{
                        flex: 5,
                        paddingTop: "140px",
                        minHeight: "100%",
                        backgroundColor: "rgba(0, 0, 0, 0)",
                        }} >
                            <CFeed
                                {...this.props}
                                domainName={this.props.domainName}
                            />
                    </div>
                    <div style={{flex: 1}}></div>
                    <div
                        style={{
                            flex: 2,
                            marginTop: "140px",
                            backgroundColor: "rgba(0, 0, 0, 0)",
                        }}
                    >
                        <FriendsList
                            {...this.props}
                            domainName={this.props.domainName}
                        />
                    </div>
                    <div style={{flex: 1}}></div>
                </div>
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {
        domainName: state.domainName,
    };
}

export default connect(mapStateToProps)(MainPage);
