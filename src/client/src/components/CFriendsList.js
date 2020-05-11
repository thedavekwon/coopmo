import React from "react";
import FriendEntry from "./FriendEntry.js";
import Container from "react-bootstrap/Container";

export default class CFriendsList extends React.Component {
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
        let friendsListSize = 0;
        if (this.state.friendsList != null) {
            friendsListSize = this.state.friendsList.length;
            friends = (
                <>
                    {this.state.friendsList.map((friend, key) => {
                        return <FriendEntry friend={friend} domainName={this.props.domainName}/>;
                    })}
                </>
            );
    }

    return (
        <div className="master" style={{backgroundColor: "rgba(0, 0, 0, 0)"}}>
            <div style={{}} className="outerDiv centerer">
                <div
                    id="I38:1056;4:4"
                    style={{
                        flexGrow: 1,
                        height: "10%",
                        borderRadius: "10px 10px 0px 0px",
                        backgroundColor: "rgba(102, 0, 153, 1)",
                    }}
                    className="innerDiv"
                >
                    <div></div>
                </div>
            </div>
            <div className="outerDiv centerer">
                <div style={{height: friendsListSize * 10 + "%", top: "10%", width: "100%"}} className="innerDiv">
                    <Container fluid>
                        {friends}
                    </Container>
                </div>

            </div>

            <div style={{zIndex: 15}} className="outerDiv centerer">
                <div
                    id="I38:1056;14:73"
                    style={{
                        top: "3.5%",
                        height: "3%",
                        width: "100%",
                        color: "rgba(255, 255, 255, 1)",
                        fontSize: 24,
                        fontWeight: 400,
                        fontFamily: "Muli",
                        textAlign: "CENTER",
                        fontStyle: "normal",
                        lineHeight: "125%",
                        letterSpacing: "0px",
                    }}
                    className="innerDiv"
                >
                    <div>
              <span style={{}} key="end">
                Friends List
              </span>
                    </div>
                </div>
            </div>
        </div>
    );
  }
}
