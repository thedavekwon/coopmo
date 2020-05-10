import React from "react";
import ReactDOM from "react-dom";
import CMenuEditProfile from "./CMenuPage.js";
import CMainPage from "./CMainPage.js";

export default class CMenuButton extends React.Component {
  constructor(props, toggle) {
    super(props);
    this.handleClick = this.handleClick.bind(this);
    this.state = { isToggleOn: toggle };
  }
  handleClick() {
    if (this.props.page === "main") {
      ReactDOM.render(<CMenuEditProfile
          domainName={this.props.domainName}></CMenuEditProfile>, document.getElementById("root"));
    } else {
      ReactDOM.render(<CMainPage domainName={this.props.domainName}></CMainPage>, document.getElementById("root"));
    }
  }

  render() {
    const bColorStyle = {
      backgroundColor: this.props.backgroundColor,
    };
    const textStyle = {
      flexGrow: 1,
      color: this.props.textColor,
      fontSize: 24,
      fontWeight: 400,
      fontFamily: "Muli",
      textAlign: "CENTER",
      fontStyle: "normal",
      lineHeight: "125%",
      letterSpacing: "0px",
    };
    return (
      <div className="master" style={bColorStyle}>
        <div>
          <div className="outerDiv centerer">
            <div className="innerDiv vertCenterAndCut menuButton">
              <div onClick={this.handleClick}>
                <span style={textStyle} key="end">
                  Menu
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
