import React from "react";
import {formatMoney} from "../functions/formatMoney";
import {fetchOthersProfilePic} from "../functions/fetchProfilePics";
import defaultImg from "../shyam/shyam_close_cropped.jpg";
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
        let purple = "rgba(102, 0, 153, 1)";
        const profilePicWidth = 40;
        const profilePicHeight = 40;
        const itemDate = this.props.timestamp.substring(0, 10);
        const itemHour = parseInt(this.props.timestamp.substring(11, 13));
        const itemMinute = this.props.timestamp.substring(14, 16);
        const amOrPm = itemHour > 12 ? "PM" : "AM";
        const itemHourStr = itemHour % 12 === 0 ? "12" : (itemHour % 12).toString();
        const timestamp =
            itemDate + " " + itemHourStr + ":" + itemMinute + " " + amOrPm;
        const fontSize = 24;
        const type = this.props.type;
        const comment = this.props.comment; 

        const fromUserHandle = this.props.fromUserHandle;
        const toUserHandle = this.props.toUserHandle;

        const amount =
            this.props.tab === "Me" ? formatMoney(this.props.amount) : "";  
        return (
            <div
                style={{
                    color: purple,
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
                        <div><span>{timestamp}</span></div>
                        <div>
                            <span>
                                <img src={this.state.profilePic} style={{
                                width: profilePicWidth,
                                height: profilePicHeight,
                                borderRadius:"50%"
                                }} />
                                {fromUserHandle} paid {toUserHandle}
                            </span>
                        </div>
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
                        <div>
                                <span>{type}</span>
                        </div>
                        <div>
                            <span>{amount}</span>
                        </div>
                        <div>
                                <span>{comment}</span>
                        </div>
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
