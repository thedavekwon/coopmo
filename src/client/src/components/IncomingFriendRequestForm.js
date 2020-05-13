import React from "react";
import {FriendRequest} from "./FriendRequest";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import {connect} from "react-redux";
import {changeRefreshState} from "../redux/actions";
import {persistor} from "../redux/store";

class IncomingFriendRequests extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            request: {},
            incomingRequests: [],
        };
        this.getIncomingRequests();
    }

    componentDidUpdate() {
        if (this.props.refreshFriendRequests) {
            this.getIncomingRequests();
            this.props.changeRefreshState("refreshFriendRequests", false);
        }
    }

    getIncomingRequests = () => {
        const path = this.props.domainName + "/user/getUserIncomingFriendRequest";
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
                            incomingRequests: result.data,
                        }));
                    }
                },
                (error) => {
                    console.log("error sending request");
                }
            )
            .then(() => {
                console.log(this.state.incomingRequests);
            });
    };

    render() {
        let friendRequests;
        if (this.state.incomingRequests != null) {
            friendRequests = (
                <Container>
                    {this.state.incomingRequests.map((e, key) => {
                        return (
                            <Row>
                                <div
                                    id="35:300"
                                    style={{
                                        width: "100%",
                                        height: 135,
                                        backgroundColor: "rgba(0, 0, 0, 0)",
                                    }}
                                    className="innerDiv"
                                >
                                    <FriendRequest
                                        name={e.name}
                                        friendId={e.id}
                                        key={key}
                                        domainName={this.props.domainName}
                                    ></FriendRequest>
                                </div>
                            </Row>
                        );
                    })}
                </Container>
            );
        }
        return (
            <div
                className="innerDiv centerer"
                style={{
                    height: "100%",
                    width: "100%",
                    overflowY: "scroll",
                    scrollbarColor: "rgba(102, 0, 153, 1)",
                }}
            >
                {friendRequests}
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {
        refreshFriendRequests: state.refreshFriendRequests,
    };
}

export default connect(mapStateToProps, {changeRefreshState})(
    IncomingFriendRequests
);
