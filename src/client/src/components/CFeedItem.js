import React, { PureComponent } from "react";

export default class CFeedItem extends PureComponent {

    constructor(props) {
        super(props);
    }

    formatAmount(paymentAmount) {
        if (paymentAmount !== "") {
            return "$" + paymentAmount;
        } else {
            return "";
        }
    }

    render() {
        let purple = "rgba(102, 0, 153, 1)";
        const itemDate = this.props.itemDateTime.substring(0, 10);
        const itemHour = parseInt(this.props.itemDateTime.substring(11, 13));
        const itemMinute = this.props.itemDateTime.substring(14, 16);
        const amOrPm = itemHour > 12 ? "PM":"AM";
        const itemHourStr = itemHour % 12 === 0 ? "12" : (itemHour % 12).toString();
        const itemDateTime = itemDate + " at " + itemHourStr + ":" + itemMinute + " " + amOrPm;
        const name1 = this.props.name1;
        const name2 = this.props.name2;
        const message = this.props.message;
        const paymentAmount = this.props.tab === "Me" ? this.props.paymentAmount: "";
        const fontSize = 24;
        return (
            <div style={{
                color: purple,
                fontSize: fontSize,
                fontWeight: 400,
                fontFamily: "Muli",
                textAlign: "LEFT",
                fontStyle: "normal",
                lineHeight: "125%",
                letterSpacing: "0px",
            }}>
                <div style={{
                    display:"flex",
                    justifyContent:"space-between"
                }}>
                    <div>
                    <span style={{}} >
                            {itemDateTime}
                    </span>
                    <br/>
                    <span style={{}} >
                        {name1} paid {name2}
                    </span>
                    <br/>
                    <span >
                        {this.formatAmount(paymentAmount)}
                    </span>
                    </div>
                    <div style={{
                        paddingRight: "10px",
                    }}>
                    <br/>
                    <span style={{}} >
                                {message}
                                </span>
                    <br/>
                    </div>
                </div>
                <hr style = {{
                    color: purple,
                    backgroundColor: purple
                }} />
            </div>
        );
    }
}

