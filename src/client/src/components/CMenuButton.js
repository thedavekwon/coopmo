import React, { PureComponent } from "react";
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
    /*
    this.setState(state => ({
      isToggleOn: !state.isToggleOn
    }));
    */
    if (this.props.page == "main") {
      ReactDOM.render(<CMenuEditProfile></CMenuEditProfile>, document.body);
    } else {
      ReactDOM.render(<CMainPage></CMainPage>, document.body);
    }
  }

  render() {
    const bColorStyle = {
      backgroundColor: this.props.backgroundColor,
    };
    const textStyle = {
      marginLeft: 0,
      marginRight: 0,
      flexGrow: 1,
      marginTop: 11.899993896484375,
      marginBottom: 11.900007247924805,
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
          <div style={{}} className="outerDiv centerer">
            <div id="I100:0;30:174;7:1" style={textStyle} className="innerDiv">
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
