import React from "react";
import {formatMoney} from "../functions/formatMoney";
import {fetchOthersProfilePic} from "../functions/fetchProfilePics";
import defaultImg from "../shyam/shyam_close_cropped.jpg";
import {getTimeAgoStr} from "../functions/timeDifference";
import {likeTransaction} from "../functions/likeTransaction";
import Button from "react-bootstrap/Button";

const purple = "rgba(102, 0, 153, 1)";
const black = "rgba(0, 0, 0, 1)";
const profilePicWidth = 40;
const profilePicHeight = 40;
const fontSize = 24;

export default class CFeedItemPayment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            profilePic: "",
            liked: false,
            newlyLiked: false
        };
    }

    handleLikeTransaction = (event) => {
        likeTransaction(this.props.domainName, this.props.transactionId, "PAY")
            .then((res) => {
                this.setState({
                    liked: true,
                    newlyLiked: true
                })
            });
    }

    getFromUserProfilePic(fromUserId) {
        fetchOthersProfilePic(this.props.domainName, fromUserId)
        .then((res) => {
            if (res.status === 200) {
              res.blob().then((blob) => {
                let url = window.URL.createObjectURL(blob);
                this.setState({
                  profilePic: url,
                });
              });
            } else {
              let url = defaultImg;
              this.setState({
                profilePic: url,
              });
            }
          });
    }

    componentDidMount() {
        this.getFromUserProfilePic(this.props.fromUserId);
        for (let ii = 0; ii < this.props.likes.length; ii++) {
            console.log(this.props.likes[ii].username);
            if (this.props.likes[ii].username === this.props.username) {
                this.setState({
                    liked: true,
                });
            }
        }
    }

    getButtonJSX = () => {
        if (this.state.liked) {
            const numLikes = this.state.newlyLiked ? parseInt(this.props.likes.length) + 1: parseInt(this.props.likes.length);
            const numLikesStr = numLikes === 1 ? numLikes.toString() + " like" : numLikes.toString() + " likes";
    
            return (
                <Button style={{
                    backgroundColor: purple,
                    borderColor: purple
                    }} disabled>
                    <div style={{
                        display: "flex",
                        justifyContent: "space-around",                                
                    }}>
                        <div>
                            {numLikesStr}
                        </div>
                    </div>
                </Button>
            );
        } else {
            const numLikes = parseInt(this.props.likes.length);

            const numLikesStr = numLikes === 1 ? numLikes.toString() + " like" : numLikes.toString() + " likes";
    
            return (
                <Button onClick={this.handleLikeTransaction} style={{
                    backgroundColor: purple,
                    borderColor: purple
                    }}>
                    <div style={{
                        display: "flex",
                        justifyContent: "space-around",                                
                    }}>
                        <div>
                            {numLikesStr}
                        </div>
                    </div>
                </Button>
            );
        }
    }

    render() {


        // sample timestamp: 2020-05-11 00:19:32.622


        const timestampStr = getTimeAgoStr(this.props.timestamp);


        // const itemDate = this.props.timestamp.substring(0, 10);

        
        // const amOrPm = hour > 12 ? "PM" : "AM";
        // const itemHourStr = hour % 12 === 0 ? "12" : (hour % 12).toString();
        // const timestampStr =
        //     itemDate + " " + itemHourStr + ":" + minute + " " + amOrPm;
        
        const type = this.props.type.substring(0,1).toUpperCase() + this.props.type.substring(1).toLowerCase();
        const comment = this.props.comment; 

        const fromUserHandle = this.props.fromUserHandle;
        const toUserHandle = this.props.toUserHandle;

        const amount =
            this.props.tab === "Me" ? formatMoney(this.props.amount) : "";
        return (
            <div
                style={{
                    color: black,
                    fontSize: fontSize,
                    fontWeight: 400,
                    fontFamily: "Muli",
                    fontStyle: "normal",
                    lineHeight: "125%",
                    letterSpacing: "0px",
                }}
            >
                <div
                    style={{
                        display: "flex",
                        justifyContent: "space-between",
                        height: "150px"
                    }}
                >
                    <div style={
                        {
                            display: "flex",
                            flexDirection: "column",
                            justifyContent: "space-between",
                            textAlign: "left"}
                        }>
                        <div style={{
                            display: "flex",
                            justifyContent: "space-between",
                        }}>
                            <img src={this.state.profilePic} style={{
                            width: profilePicWidth,
                            height: profilePicHeight,
                            borderRadius:"50%",
                            marginRight: "20px"
                            }} />
                            <span>{fromUserHandle} paid {toUserHandle}</span>
                        </div>
                        <span>{amount}</span>
                        <span>{comment}</span>
                    </div>
                    <div
                        style={{
                            display: "flex",
                            flexDirection: "column",
                            justifyContent: "space-between",
                            paddingRight: "10px",
                            textAlign: "right"
                        }}
                    >
                        <span>{timestampStr}</span>
                        <span>{type}</span>
                        {this.getButtonJSX()}
                    </div>
                </div>
                <hr
                    style={{
                        color: purple,
                        backgroundColor: purple,
                    }}
                />
            </div>
        );


    }
}
