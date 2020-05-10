import React from "react";
import {formatMoney} from "../functions/formatMoney";
import {MINH_PIC_LINK} from "../minhPic"

export default class CFeedItemPayment extends React.Component {

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
                                <img src={MINH_PIC_LINK} style={{
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
