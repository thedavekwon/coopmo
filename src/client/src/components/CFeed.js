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
                <div key={feedTab} style={{
                    flex: 1,
                    backgroundColor:
                        this.state.feedTab === feedTab ? purple : white,
                    borderRadius: "8px 8px 0px 0px",
                }}
                onClick={() => this.handleClick(feedTab)}>
                        <CFeedTab name={feedTab} feedTab={this.state.feedTab}/>
                </div>
            );
        })


        return (
            <div style={{backgroundColor: "rgba(0, 0, 0, 0)",
                height: "100%"}}>
                <div
                    style={{
                        height: "45px",
                        display: "flex"
                    }}
                    >
                    {feedTabs}
                </div>
                <div
                    style={{
                        marginTop: 20
                    }}
                >
                    <CFeedList
                        feedTab={this.state.feedTab}
                        domainName={this.props.domainName}
                        username={this.props.username}
                    />
                </div>
            </div>
        );
    }
}
