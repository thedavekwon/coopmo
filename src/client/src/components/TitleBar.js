import React from "react";
import CMenuButton from "./CMenuButton.js";
import {formatMoney} from "../functions/formatMoney";

export default class TitleBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            page: "main",
        };
    }

    render() {
        let backgroundColor =
            this.props.page === "main"
                ? "rgba(102, 0, 153, 1)"
                : "rgba(255, 255, 255, 1)";
        let textColor =
            this.props.page === "main"
                ? "rgba(255, 255, 255, 1)"
                : "rgba(102, 0, 153, 1)";
        let includeBalance;
        if (this.props.page === "main") {
            includeBalance = (
                <div
                    style={{
                        zIndex: 3,
                        width: "10%",
                        top: "25%",
                        height: "50%",
                        marginLeft: "85%",
                    }}
                    className="outerDiv centerer"
                >
                    <div
                        id="I76:29;30:174"
                        style={{
                            flexGrow: 1,
                            backgroundColor: "rgba(102, 0, 153, 1)",
                            color: "rgba(255, 255, 255, 1)",
                            fontSize: 24,
                            fontWeight: 400,
                            fontFamily: "Muli",
                            textAlign: "CENTER",
                            fontStyle: "normal",
                            lineHeight: "125%",
                            letterSpacing: "0px",
                        }}
                        className="innerDiv vertCenterAndCut"
                    >
                        <span>Balance : {formatMoney(this.props.balance)}</span>
                    </div>
                </div>
            );
        }
        return (
            <div
                style={{
                    zIndex: 0,
                }}
                className="outerDiv centerer"
            >
                <div
                    id="76:47"
                    style={{
                        width: "100%",
                        marginLeft: "0%",
                        height: "10.3515625%",
                        top: "0%",
                        backgroundColor: "rgba(0, 0, 0, 0)",
                    }}
                    className="innerDiv"
                >
                    <div>
                        <div style={{}} className="outerDiv centerer">
                            <div
                                id="I76:47;76:20"
                                style={{
                                    flexGrow: 1,
                                    backgroundColor: backgroundColor,
                                }}
                                className="innerDiv"
                            >
                                <div style={{}} className="outerDiv centerer">
                                    <div
                                        id="I76:47;76:20"
                                        style={{
                                            flexGrow: 1,
                                            backgroundColor: backgroundColor,
                                        }}
                                        className="innerDiv"
                                    >
                                        <div></div>
                                    </div>
                                </div>
                                <div
                                    style={{
                                        flexGrow: 1,
                                        width: "10%",
                                        top: "25%",
                                        height: "50%",
                                        marginLeft: "2%",
                                    }}
                                    className="outerDiv centerer"
                                >
                                    <div
                                        id="I76:47;76:21"
                                        style={{
                                            flexGrow: 1,
                                            backgroundColor: "rgba(0, 0, 0, 1)",
                                        }}
                                        className="innerDiv"
                                    >
                                        <CMenuButton
                                            {...this.props}
                                            backgroundColor={backgroundColor}
                                            textColor={textColor}
                                            nodeId="I76:47;76:21"
                                            page={this.props.page}
                                            domainName={this.props.domainName}
                                        />
                                    </div>
                                </div>
                                <div
                                    style={{
                                        zIndex: 2,
                                        top: "25%",
                                        height: "50%",
                                    }}
                                    className="outerDiv vertCenterAndCut"
                                >
                                    <div
                                        id="I76:29;97:225"
                                        style={{
                                            marginLeft: "40%",
                                            marginRight: "40%",
                                            flexGrow: 1,
                                            color: "rgba(0, 0, 0, 1)",
                                            fontSize: 73,
                                            fontWeight: 700,
                                            fontFamily: "Muli",
                                            textAlign: "CENTER",
                                            fontStyle: "normal",
                                            lineHeight: "125%",
                                            letterSpacing: "0px",
                                        }}
                                        className="innerDiv vertCenterAndCut  "
                                    >
                                        <div>
                      <span
                          style={{
                              fontSize: 73,
                              fontStyle: "normal",
                              lineHeight: "NaN%",
                              letterSpacing: "undefinedpx",
                              color: textColor,
                          }}
                          key="4"
                      >
                        Coop
                      </span>
                                            <span
                                                style={{
                                                    fontSize: 73,
                                                    fontStyle: "normal",
                                                    lineHeight: "NaN%",
                                                    letterSpacing: "undefinedpx",
                                                }}
                                                key="end"
                                            >
                        mo
                      </span>
                                        </div>
                                    </div>
                                </div>
                                {includeBalance}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
