import React from "react";
import ReactDOM from "react-dom";
import CMenuEditProfile from "./MenuPage.js";
import MainPage from "./MainPage.js";

export default class MenuButton extends React.Component {
  constructor(props, toggle) {
    super(props);
    this.handleClick = this.handleClick.bind(this);
    this.state = {isToggleOn: toggle};
  }

  handleClick() {
    if (this.props.page === "main") {
      ReactDOM.render(<CMenuEditProfile
          domainName={this.props.domainName}></CMenuEditProfile>, document.getElementById("root"));
    } else {
      ReactDOM.render(<MainPage domainName={this.props.domainName}></MainPage>, document.getElementById("root"));
    }
  }

  render() {
    const bColorStyle = {
      backgroundColor: this.props.backgroundColor,
    };
    return (
      <div className="master" style={bColorStyle}>
        <div>
          <div className="outerDiv centerer">
            <div
                className={this.props.page === "main" ? "innerDiv vertCenterAndCut menuButton" : "innerDiv vertCenterAndCut menuButtonAlt"}>
              <div onClick={this.handleClick}>
                <span style={{}} key="end">
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
