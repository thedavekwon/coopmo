import React from "react";
import FriendsList from "./FriendsList.js";
import CFeed from "./CFeed.js";
import TitleBar from "./TitleBar.js";
import {connect} from "react-redux";
import SockJsClient from "react-stomp";

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
                <SockJsClient url='http://localhost:8080/ws' topics={["/user/queue/notify"]}
                              onMessage={(msg) => { console.log(msg); }}
                              ref={ (client) => { this.clientRef = client; console.log(client)}} />
                <div>
                    <div style={{}} className="outerDiv centerer">
                        <div
                            id="38:1057"
                            style={{
                                width: "55.208333333333336%",
                                marginLeft: "7.692311604817708%",
                                height: "71.2890625%",
                                top: "20.99609375%",
                                backgroundColor: "rgba(0, 0, 0, 0)",
                                overflow: "hidden",
                            }}
                            className="innerDiv"
                        >
                            {
                                <CFeed
                                    {...this.props}
                                    nodeId="38:1057"
                                    domainName={this.props.domainName}
                                />
                            }
                        </div>
                    </div>
                    <div
                        style={{
                            zIndex: 1,
                        }}
                        className="outerDiv centerer"
                    >
                        <div
                            id="38:1056"
                            style={{
                                width: "20.416666666666668%",
                                marginLeft: "72.91666666666667%",
                                height: "71.2890625%",
                                top: "20.99609375%",
                                backgroundColor: "rgba(0, 0, 0, 0)",
                                overflow: "hidden",
                            }}
                            className="innerDiv"
                        >
                            <CFriendsList
                                {...this.props}
                                nodeId="38:1056"
                                domainName={this.props.domainName}
                            />
                        </div>
                    </div>
                    <TitleBar
                        page="main"
                        balance={this.state.balance}
                        domainName={this.props.domainName}
                    />
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
