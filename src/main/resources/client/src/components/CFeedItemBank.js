import React from "react";

export default class CFeedItemBank extends React.Component {
    constructor(props) {
        super(props);
    }

    formatAmount(amount) {
        if (amount !== "") {
            return "$" + amount;
        } else {
            return "";
        }
    }

    render() {
        let purple = "rgba(102, 0, 153, 1)";
        const itemDate = this.props.timestamp.substring(0, 10);
        const itemHour = parseInt(this.props.timestamp.substring(11, 13));
        const itemMinute = this.props.timestamp.substring(14, 16);
        const amOrPm = itemHour > 12 ? "PM" : "AM";
        const itemHourStr = itemHour % 12 === 0 ? "12" : (itemHour % 12).toString();
        const timestamp =
            itemDate + " " + itemHourStr + ":" + itemMinute + " " + amOrPm;
        const fontSize = 24;
        const message = "CASH " + this.props.type;

        const amount = this.props.amount.toString();
        return (
            <div
                style={{
                    color: purple,
                    fontSize: fontSize,
                    fontWeight: 400,
                    fontFamily: "Muli",
                    textAlign: "LEFT",
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
                    <div>
                        <span style={{}}>{timestamp}</span>
                        <br/>
                        <span>{this.formatAmount(amount)}</span>
                    </div>
                    <div
                        style={{
                            paddingRight: "10px",
                        }}
                    >
                        <br/>
                        <span style={{}}>{message}</span>
                        <br/>
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
