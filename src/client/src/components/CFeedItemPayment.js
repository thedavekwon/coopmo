import React from "react";

export default class CFeedItemPayment extends React.Component {
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
        const type = this.props.type;
        const comment = this.props.comment;

        const fromUserHandle = this.props.fromUserHandle;
        const toUserHandle = this.props.toUserHandle;

        const amount =
            this.props.tab === "Me" ? (this.props.amount.toFixed(2) / 100.0).toString() : "";  
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
                        <span style={{}}>
                {fromUserHandle} paid {toUserHandle}
            </span>
                        <br/>
                        <span>{this.formatAmount(amount)}</span>
                    </div>
                    <div
                        style={{
                            display: "flex",
                            flexDirection: "column",
                            justifyContent: "space-between",
                            paddingRight: "10px",
                        }}
                    >
                        <div>
                                <span>{type}</span>
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
