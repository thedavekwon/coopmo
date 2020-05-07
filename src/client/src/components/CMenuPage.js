import React, {PureComponent} from "react";
import CMenuButtonDefault from "./CMenuButtonDefault.js";
import CEditProfileForm from "./CEditProfileForm.js";
import CAddFriendForm from "./CAddFriendForm.js";
import CIncomingFriendRequestForm from "./CIncomingFriendRequestForm.js";
import CChangeBankAccounts from "./CChangeBankAccounts.js";
import CCashInForm from "./CCashInForm.js";
import CSendPaymentForm from "./CSendPaymentForm.js";
import TitleBar from "./TitleBar.js";
import ReactDOM from "react-dom";
import CLoginPage from "./CLoginPage.js";

export default class CMenuPage extends PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            activePage: "Edit Profile",
        };
        this.changePage = this.changePage.bind(this);
    }

    changePage(newPage) {
        this.setState((state) => ({
            activePage: newPage,
        }));
    }

    signOut = () => {
        var path = this.props.domainName + "/logout"
        fetch(path, {
            method: "GET",
            headers: {"Access-Control-Allow-Origin": "*", "Cache-Control": "no-cache"},
            credentials: 'include'
        })
            .then((res) => {
                if (res.status == 200) {
                    ReactDOM.render(<CLoginPage
                        domainName={this.props.domainName}></CLoginPage>, document.getElementById("root"));
                }
            });
    }

    render() {
        let formPage;
        if (this.state.activePage == "Edit Profile")
            formPage = <CEditProfileForm domainName={this.props.domainName}/>;
        else if (this.state.activePage == "Add Friend")
            formPage = <CAddFriendForm domainName={this.props.domainName}/>;
        else if (this.state.activePage == "Incoming Friend Requests")
            formPage = (
                <CIncomingFriendRequestForm domainName={this.props.domainName}/>
            );
        else if (this.state.activePage == "Add a Bank Account")
            formPage = <CChangeBankAccounts domainName={this.props.domainName}/>;
        else if (this.state.activePage == "Cash In")
            formPage = <CCashInForm domainName={this.props.domainName}/>;
        else if (this.state.activePage == "Send Payment")
            formPage = <CSendPaymentForm domainName={this.props.domainName}/>;
        else
            this.signOut();

        let pages = [
            "Edit Profile",
            "Add Friend",
            "Incoming Friend Requests",
            "Add a Bank Account",
            "Cash In",
            "Send Payment",
            "Sign Out",
        ];

        let menuButtons = pages.map((value, index) => {
            return (
                <div
                    style={{
                        zIndex: index + 1,
                    }}
                    className="outerDiv centerer"
                >
                    <div
                        id="I254:861;30:393"
                        style={{
                            marginLeft: 0,
                            marginRight: 0,
                            flexGrow: 1,
                            top: (100 / 7) * index + "%",
                            height: "14.2857%",
                            backgroundColor: "rgba(0, 0, 0, 0)",
                        }}
                        className="innerDiv"
                        onClick={value != "Sign Out" ? () => this.changePage(value) : this.signOut}
                    >
                        <CMenuButtonDefault
                            {...this.props}
                            name={value}
                            nodeId="I254:861;30:393"
                            active={this.state.activePage}
                        />
                    </div>
                </div>
            );
        });

        return (
            <div
                className="master"
                style={{
                    backgroundColor: "rgba(255, 255, 255, 1)",
                }}
            >
                <div>
                    <div style={{}} className="outerDiv centerer">
                        <div
                            id="254:861"
                            style={{
                                width: "20.833333333333332%",
                                marginLeft: "10.277777777777779%",
                                height: "39.6484375%",
                                top: "19.53125%",
                                backgroundColor: "rgba(0, 0, 0, 0)",
                            }}
                            className="innerDiv"
                        >
                            {menuButtons}
                        </div>
                    </div>
                    <div
                        style={{
                            zIndex: 1,
                        }}
                        className="outerDiv centerer"
                    >
                        <div
                            id="30:446"
                            style={{
                                width: "0.06944444444444445%",
                                marginLeft: "31.041666666666668%",
                                height: "73.53515625%",
                                top: "19.53125%",
                                backgroundColor: "rgba(102, 0, 153, 1)",
                            }}
                            className="innerDiv"
                        >
                            <div></div>
                        </div>
                    </div>
                    <div className="outerDiv centerer">
                        <div className="innerDiv menuForm">{formPage}</div>
                    </div>

                    <TitleBar page="editProfile" domainName={this.props.domainName}/>
                </div>
            </div>
        );
    }
}
