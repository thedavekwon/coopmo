import React from "react";
import CFeedTab from "./CFeedTab.js";
import CFeedList from "./CFeedList.js";

export default class CFeed extends React.Component {
    constructor(props) {
        super(props);
        this.handleClick = this.handleClick.bind(this);
        this.state = {feedTab: "Me"};
    }

    handleClick(tab) {
        console.log("in handle click");
        this.setState((state) => ({
            feedTab: tab,
        }));
    }


    render() {
        let purple = "rgba(102, 0, 153, 1)";
        let white = "rgba(255, 255, 255, 1)";

        let tabNames = ["Me", "Friend", "Public"];

        let feedTabs = tabNames.map((feedTab, index) => {
            return (
                <div className="outerDiv centerer" key={feedTab}>
                    <div
                        style={{
                            marginLeft: 100 / 3 * index + "%",
                            marginRight: 100 / 3 * (2 - index) + "%",
                            flexGrow: 1,
                            backgroundColor:
                                this.state.feedTab === feedTab ? purple : white,
                            borderRadius: "8px 8px 0px 0px",
                        }}
                        className="innerDiv"
                        onClick={() => this.handleClick(feedTab)}
                    >
                        <CFeedTab name={feedTab} feedTab={this.state.feedTab}/>
                    </div>
                </div>
            );
        })


        return (
            <div className="master" style={{backgroundColor: "rgba(0, 0, 0, 0)"}}>
                <div>
                    <div className="outerDiv centerer">
                        <div
                            style={{
                                marginLeft: 0,
                                marginRight: 0,
                                flexGrow: 1,
                                height: "48px",
                                backgroundColor: "rgba(0, 0, 0, 0)",
                            }}
                            className="innerDiv"
                        >
                            {feedTabs}
                        </div>
                    </div>
                    <div
                        style={{
                            position: "relative",
                            marginTop: 75,
                        }}
                    >
                        <CFeedList
                            feedTab={this.state.feedTab}
                            domainName={this.props.domainName}
                        />
                    </div>
                </div>
            </div>
        );
    }
}
