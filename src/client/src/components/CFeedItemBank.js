import React from "react";
import {formatMoney} from "../functions/formatMoney";
import {getTimeAgoStr} from "../functions/timeDifference";

export default class CFeedItemBank extends React.Component {

    render() {
        const purple = "rgba(102, 0, 153, 1)";
        const black = "rgba(0, 0, 0, 1)";

        const timestampStr = getTimeAgoStr(this.props.timestamp);
        

        const fontSize = 24;
        const type = "Cash " + this.props.type.substring(0,1).toUpperCase() + this.props.type.substring(1).toLowerCase();

        const amount = formatMoney(this.props.amount);
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
                    <div style={{
                        display: "flex",
                        flexDirection: "column",
                        justifyContent: "space-between",
                        textAlign: "left"
                        }}>
                        <span>{type}</span>
                        <span>{amount}</span>
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
                        <span>Private</span>
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
