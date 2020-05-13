import React from "react";
import FriendEntry from "./FriendEntry.js";
import Container from "react-bootstrap/Container";
import {connect} from "react-redux";
import {changeRefreshState} from "../redux/actions";
import {persistor} from "../redux/store";

class FriendsList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            friendsList: [],
        };
        this.getFriendsList();
    }

    componentDidUpdate() {
        if (this.props.refreshFriendsList) {
            this.getFriendsList();
            this.props.changeRefreshState("refreshFriendsList", false);
        }
    }

    getFriendsList = () => {
        const path = this.props.domainName + "/user/getUserFriendList";
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
                            friendsList: result.data,
                        }));
                    }
                },
                (error) => {
                    console.log("error sending request");
                }
            )
            .then(() => {
                console.log(this.state.friendsList);
            });
    };

    render() {
        let friends;
        if (this.state.friendsList != null) {
            friends = (
                <>
                    {this.state.friendsList.map((friend, key) => {
                        return (
                            <FriendEntry friend={friend} domainName={this.props.domainName}/>
                        );
                    })}
                </>
            );
        }

        return (
            <div className="master" style={{backgroundColor: "rgba(0, 0, 0, 0)"}}>
                <div style={{}} className="outerDiv centerer">
                    <div
                        style={{
                            flexGrow: 1,
                            height: "10%",
                            borderRadius: "10px 10px 0px 0px",
                            backgroundColor: "rgba(102, 0, 153, 1)",
                        }}
                        className="innerDiv"
                    ></div>
                </div>
                <div className="outerDiv centerer">
                    <div
                        style={{
                            height: "90%",
                            top: "10%",
                            width: "100%",
                            overflowY: "scroll",
                        }}
                        className="innerDiv"
                    >
                        <Container fluid>{friends}</Container>
                    </div>
                </div>

                <div style={{zIndex: 15}} className="outerDiv centerer">
                    <div style={{height: "10%", width: "100%"}}>
                        <div
                            style={{
                                top: "60%",
                                textAlign: "CENTER",
                                color: "rgba(255, 255, 255, 1)",
                            }}
                            className="innerDiv textStyle vertCenterAndCut"
                        >
                            <div>
                <span style={{}} key="end">
                  Friends List
                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {
        refreshFriendsList: state.refreshFriendsList,
    };
}

export default connect(mapStateToProps, {changeRefreshState})(FriendsList);
