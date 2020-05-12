import React from "react";
import {formatMoney} from "../functions/formatMoney";
import {fetchOthersProfilePic} from "../functions/fetchProfilePics";
import defaultImg from "../shyam/shyam_close_cropped.jpg";
import {getTimeAgoStr} from "../functions/timeDifference";


const purple = "rgba(102, 0, 153, 1)";
const black = "rgba(0, 0, 0, 1)";
const profilePicWidth = 40;
const profilePicHeight = 40;
const fontSize = 24;

export default class CFeedItemPayment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            profilePic: ""
        };
    }

    getFromUserProfilePic(fromUserId) {
        fetchOthersProfilePic(this.props.domainName, fromUserId)
        .then((res) => {
            console.log("status" + res.status);
            if (res.status === 200) {
              res.blob().then((blob) => {
                let url = window.URL.createObjectURL(blob);
                this.setState({
                  profilePic: url,
                });
              });
            } else {
              console.log("here");
              let url = defaultImg;
              this.setState({
                profilePic: url,
              });
            }
          });
    }

    componentDidMount() {
        this.getFromUserProfilePic(this.props.fromUserId);
    }

    render() {
        // sample timestamp: 2020-05-11 00:19:32.622
        const year = parseInt(this.props.timestamp.substring(0, 4));
        const month = parseInt(this.props.timestamp.substring(5,7));
        const day = parseInt(this.props.timestamp.substring(8,10));
        const hour = parseInt(this.props.timestamp.substring(11, 13));
        const minute = this.props.timestamp.substring(14, 16);
        const second = this.props.timestamp.substring(17, 19);
        const millisecond = this.props.timestamp.substring(19, 23);

        const timestamp = new Date(year, month-1, day, hour, minute, second, millisecond);

        const currentTimestamp = new Date();

        const timestampStr = getTimeAgoStr(timestamp);


        // const itemDate = this.props.timestamp.substring(0, 10);

        
        // const amOrPm = hour > 12 ? "PM" : "AM";
        // const itemHourStr = hour % 12 === 0 ? "12" : (hour % 12).toString();
        // const timestampStr =
        //     itemDate + " " + itemHourStr + ":" + minute + " " + amOrPm;
        
        const type = this.props.type;
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
                    }}
                >
                    <div style={
                        {
                            display: "flex",
                            flexDirection: "column",
                            justifyContent: "space-between",
                            textAlign: "left"}
                        }>
                        <span>
                            <img src={this.state.profilePic} style={{
                            width: profilePicWidth,
                            height: profilePicHeight,
                            borderRadius:"50%"
                            }} />
                            {fromUserHandle} paid {toUserHandle}
                        </span>
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
