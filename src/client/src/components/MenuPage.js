import React, {PureComponent} from "react";
import MenuTab from "./MenuTab.js";
import EditProfileForm from "./EditProfileForm.js";
import AddFriendForm from "./AddFriendForm.js";
import IncomingFriendRequestForm from "./IncomingFriendRequestForm.js";
import AddBankAccounts from "./AddBankAccounts.js";
import CashInForm from "./CashInForm.js";
import SendPaymentForm from "./SendPaymentForm.js";
import TitleBar from "./TitleBar.js";
import {connect} from "react-redux";
import {changeLogin, changeMenuPage, changePage} from "../redux/actions";
import {persistor} from "../redux/store";

class MenuPage extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      activePage: "Edit Profile",
    };
    this.changePage = this.changePage.bind(this);
  }

  changePage(newPage) {
    this.props.changeMenuPage(newPage);
  }

  signOut = () => {
    var path = this.props.domainName + "/logout";
    fetch(path, {
      method: "GET",
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Cache-Control": "no-cache",
      },
      credentials: "include",
    }).then((res) => {
      if (res.status === 200) {
        persistor.purge();
      } else if (res.status === 302) {
        persistor.purge();
      }
    });
  };

  render() {
    let formPage;
    switch (this.props.activeMenuPage) {
      case "Edit Profile":
        formPage = <EditProfileForm domainName={this.props.domainName}/>;
        break;
      case "Add Friend":
        formPage = <AddFriendForm domainName={this.props.domainName}/>;
        break;
      case "Incoming Friend Requests":
        formPage = (
            <IncomingFriendRequestForm domainName={this.props.domainName}/>
        );
        break;
      case "Add a Bank Account":
        formPage = <AddBankAccounts domainName={this.props.domainName}/>;
        break;
      case "Cash In":
        formPage = <CashInForm domainName={this.props.domainName}/>;
        break;
      case "Send Payment":
        formPage = <SendPaymentForm domainName={this.props.domainName}/>;
        break;
      default:
        this.signOut();
        break;
    }
    /*
    if (this.props.activeMenuPage === "Edit Profile")
        formPage = <EditProfileForm domainName={this.props.domainName}/>;
    else if (this.props.activeMenuPage === "Add Friend")
        formPage = <AddFriendForm domainName={this.props.domainName}/>;
    else if (this.props.activeMenuPage === "Incoming Friend Requests")
      formPage = (
          <IncomingFriendRequestForm domainName={this.props.domainName}/>
      );
    else if (this.props.activeMenuPage === "Add a Bank Account")
        formPage = <AddBankAccounts domainName={this.props.domainName}/>;
    else if (this.state.activePage === "Cash In")
        formPage = <CashInForm domainName={this.props.domainName}/>;
    else if (this.state.activePage === "Send Payment")
        formPage = <SendPaymentForm domainName={this.props.domainName}/>;
    else this.signOut();
    */

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
              key={value}
          >
            <div
                style={{
                  flexGrow: 1,
                  top: (100 / 7) * index + "%",
                  height: "14.2857%",
                  backgroundColor: "rgba(0, 0, 0, 0)",
                }}
                className="innerDiv"
                onClick={
                  value !== "Sign Out" ? () => this.changePage(value) : this.signOut
                }
            >
              <MenuTab
                  {...this.props}
                  name={value}
                  active={this.props.activeMenuPage}
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
              ></div>
            </div>
            <div className="outerDiv centerer">
              <div
                  className={
                    this.state.activePage === "Edit Profile"
                        ? "innerDiv menuForm editForm"
                        : "innerDiv menuForm"
                  }
              >
                {formPage}
              </div>
            </div>

            <TitleBar page="editProfile" domainName={this.props.domainName}/>
          </div>
        </div>
    );
  }
}

function mapStateToProps(state) {
  return {
    domainName: state.domainName,
    activeMenuPage: state.activeMenuPage,
  };
}

export default connect(mapStateToProps, {changePage, changeLogin, changeMenuPage})(MenuPage);
