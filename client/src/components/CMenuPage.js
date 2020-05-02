import React, {PureComponent} from "react";
import ReactDOM from "react-dom";
import CLoginPage from "./CLoginPage";
import CMenuButton from "./CMenuButton.js";
import CMenuButtonDefault from "./CMenuButtonDefault.js";
import CEditProfileForm from "./CEditProfileForm.js";
import CAddFriendForm from "./CAddFriendForm.js";
import CIncomingFriendRequestForm from "./CIncomingFriendRequestForm.js";
import CChangeBankAccounts from "./CChangeBankAccounts.js";
import CCashInForm from "./CCashInForm.js";
import CSendPaymentForm from "./CSendPaymentForm.js";

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
                    ReactDOM.render(<CLoginPage domainName={this.props.domainName}></CLoginPage>, document.body);
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
    else formPage = <CEditProfileForm />;
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
              <div>
                <div style={{}} className="outerDiv centerer">
                  <div
                    id="I254:861;30:387"
                    style={{
                      marginRight: 0,
                      flexGrow: 1,
                      marginTop: 0,
                      top: "0%",
                      height: "14.2857%",
                      backgroundColor: "rgba(0, 0, 0, 0)",
                    }}
                    className="innerDiv"
                    onClick={() => this.changePage("Edit Profile")}
                  >
                    <CMenuButtonDefault
                      name="Edit Profile"
                      active={this.state.activePage}
                    />
                  </div>
                </div>
                <div
                  style={{
                    zIndex: 1,
                  }}
                  className="outerDiv centerer"
                >
                  <div
                    id="I254:861;30:389"
                    style={{
                      marginRight: 0,
                      flexGrow: 1,
                      top: "14.2857%",
                      height: "14.2857%",
                      backgroundColor: "rgba(0, 0, 0, 0)",
                    }}
                    className="innerDiv"
                    onClick={() => this.changePage("Add Friend")}
                  >
                    <CMenuButtonDefault
                      {...this.props}
                      name="Add Friend"
                      nodeId="I254:861;30:389"
                      active={this.state.activePage}
                    />
                  </div>
                </div>
                <div
                  style={{
                    zIndex: 2,
                  }}
                  className="outerDiv centerer"
                >
                  <div
                    id="I254:861;30:391"
                    style={{
                      marginLeft: 0,
                      marginRight: 0,
                      flexGrow: 1,
                      top: "28.57142%",
                      height: "14.2857%",
                      backgroundColor: "rgba(0, 0, 0, 0)",
                    }}
                    className="innerDiv"
                    onClick={() => this.changePage("Incoming Friend Requests")}
                  >
                    <CMenuButtonDefault
                      {...this.props}
                      name="Incoming Friend Requests"
                      nodeId="I254:861;30:391"
                      active={this.state.activePage}
                    />
                  </div>
                </div>
                <div
                  style={{
                    zIndex: 3,
                  }}
                  className="outerDiv centerer"
                >
                  <div
                    id="I254:861;30:393"
                    style={{
                      marginLeft: 0,
                      marginRight: 0,
                      flexGrow: 1,
                      top: "42.8571%",
                      height: "14.2857%",
                      backgroundColor: "rgba(0, 0, 0, 0)",
                    }}
                    className="innerDiv"
                    onClick={() => this.changePage("Add a Bank Account")}
                  >
                    <CMenuButtonDefault
                      {...this.props}
                      name="Add a Bank Account"
                      nodeId="I254:861;30:393"
                      active={this.state.activePage}
                    />
                  </div>
                </div>
                <div
                  style={{
                    zIndex: 4,
                  }}
                  className="outerDiv centerer"
                >
                  <div
                    id="I254:861;30:395"
                    style={{
                      marginLeft: 0,
                      marginRight: 0,
                      flexGrow: 1,
                      top: "57.14286%",
                      height: "14.2857%",
                      backgroundColor: "rgba(0, 0, 0, 0)",
                    }}
                    className="innerDiv"
                    onClick={() => this.changePage("Cash In")}
                  >
                    <CMenuButtonDefault
                      {...this.props}
                      name="Cash In"
                      nodeId="I254:861;30:395"
                      active={this.state.activePage}
                    />
                  </div>
                </div>
                <div
                  style={{
                    zIndex: 5,
                  }}
                  className="outerDiv centerer"
                >
                  <div
                    id="I254:861;30:397"
                    style={{
                      marginLeft: 0,
                      marginRight: 0,
                      flexGrow: 1,
                      top: "71.4286%",
                      height: "14.2857%",
                      backgroundColor: "rgba(0, 0, 0, 0)",
                    }}
                    className="innerDiv"
                    onClick={() => this.changePage("Send Payment")}
                  >
                    <CMenuButtonDefault
                      {...this.props}
                      name="Send Payment"
                      nodeId="I254:861;30:397"
                      active={this.state.activePage}
                    />
                  </div>
                </div>
                <div
                  style={{
                    zIndex: 6,
                  }}
                  className="outerDiv centerer"
                >
                  <div
                      id="I254:861;30:399"
                      style={{
                      marginLeft: 0,
                      marginRight: 0,
                      flexGrow: 1,
                      top: "85.7143%",
                      height: "14.2857%",
                      backgroundColor: "rgba(0, 0, 0, 0)",
                    }}
                      className="innerDiv"
                      onClick={this.signOut}
                  >
                    <CMenuButtonDefault
                      {...this.props}
                      name="Sign Out"
                      active={this.state.activePage}
                      nodeId="I254:861;30:399"
                    />
                  </div>
                </div>
              </div>
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
              <div> </div>
            </div>
          </div>
          {formPage}
          <div
            style={{
              zIndex: 8,
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
                      marginLeft: 0,
                      marginRight: 0,
                      flexGrow: 1,
                      marginTop: 0,
                      marginBottom: 0,
                      backgroundColor: "rgba(255, 255, 255, 1)",
                    }}
                    className="innerDiv"
                  >
                    <div> </div>
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
                          backgroundColor="rgba(255, 255, 255, 1)"
                          textColor="rgba(102, 0, 153, 1)"
                          nodeId="I76:47;76:21"
                          domainName={this.props.domainName}
                      />
                  </div>
                </div>
                <div
                  style={{
                    zIndex: 2,
                  }}
                  className="outerDiv centerer"
                >
                  <div
                    id="I76:29;97:225"
                    style={{
                      marginLeft: "40%",
                      marginRight: "40%",
                      flexGrow: 1,
                      top: "2%",
                      color: "rgba(0, 0, 0, 1)",
                      fontSize: 73,
                      fontWeight: 700,
                      fontFamily: "Muli",
                      textAlign: "CENTER",
                      fontStyle: "normal",
                      lineHeight: "125%",
                      letterSpacing: "0px",
                    }}
                    className="innerDiv"
                  >
                    <div>
                      <span
                        style={{
                          fontSize: 73,
                          fontStyle: "normal",
                          lineHeight: "NaN%",
                          letterSpacing: "undefinedpx",
                          color: "rgba(102, 0, 153, 1)",
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
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
