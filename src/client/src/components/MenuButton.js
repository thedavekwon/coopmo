import React from "react";
import {connect} from "react-redux";
import {changePage} from "../redux/actions";

class MenuButton extends React.Component {
  constructor(props, toggle) {
    super(props);
    this.handleClick = this.handleClick.bind(this);
    this.state = {isToggleOn: toggle};
  }

  handleClick() {
    if (this.props.page === "main") {
      this.props.changePage("MenuPage");
    } else {
      this.props.changePage("MainPage");
    }
  }

  render() {
    const bColorStyle = {
      backgroundColor: this.props.backgroundColor,
    };
    return (
        <div style={bColorStyle}>
          <div
              className={
                this.props.page === "main"
                    ? " menuButton"
                    : " menuButtonAlt"
              }

              style={{
                lineHeight: "100%"
              }}
          >
            <div onClick={this.handleClick}>
              {/* <span style={textStyle} key="end"> */}
              <span>
                Menu
              </span>
            </div>
          </div>
        </div>
    );
  }
}

function mapStateToProps(state) {
  return {
    domainName: state.domainName,
  };
}

export default connect(mapStateToProps, {changePage})(MenuButton);
