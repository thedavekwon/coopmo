import React from "react";
import FriendEntry from "./FriendEntry.js";
import Container from "react-bootstrap/Container";

export default class FriendsList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            friendsList: [],
        };
        this.getFriendsList();
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
            .then((res) => res.json())
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
        if (this.state.friendsList !== null) {
            friends = (
                <>
                    {this.state.friendsList.map((friend, key) => {
                        return (
                            <FriendEntry friend={friend} domainName={this.props.domainName} key={key}/>
                        );
                    })}
                </>
            );
        }

        return (
            <div style={{backgroundColor: "rgba(0, 0, 0, 0)"}}>
                    <div style={{
                        height: "45px",
                        backgroundColor: "rgba(102, 0, 153, 1)",
                        borderRadius: "8px 8px 0px 0px",
                    }}>
                        <div
                            style={{
                                fontSize: 24,
                                fontWeight: 400,
                                fontFamily: "Muli",
                                textAlign: "CENTER",
                                fontStyle: "normal",
                                lineHeight: "100%",
                                letterSpacing: "0px",
                                color: "rgba(255, 255, 255, 1)",
                                paddingTop: "10px"
                            }}
                        >
                                Friends List
                        </div>
                    </div>

                <div>
                    <div
                        style={{
                            overflowY: "scroll",
                            height: "100%"
                        }}
                    >
                        <Container fluid>{friends}</Container>
                    </div>
                </div>
            </div>
        );
    }
}
