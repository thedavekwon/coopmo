import React from "react";

export default class CFeedTab extends React.Component {
    constructor(props) {
        super(props);
        this.handleClick = this.handleClick.bind(this);
    }

    handleClick() {
        console.log("in Feed Tab");
    }

    render() {
        let active = false;
        if (this.props.feedTab == this.props.name) active = true;
        let purple = "rgba(102, 0, 153, 1)";
        let white = "rgba(255, 255, 255, 1)";
        let barBottom;

        if (!active) {
            barBottom = (
                <div style={{zIndex: 2}} className="outerDiv centerer">
                    <div
                        id="I38:1057;30:46;30:9;30:5"
                        style={{
                            marginLeft: 0,
                            marginRight: 0,
                            flexGrow: 1,
                            top: "98%",
                            height: "2%",
                            backgroundColor: "rgba(102, 0, 153, 1)",
                        }}
                        className="innerDiv"
                    >
                        <div></div>
                    </div>
        </div>
      );
    }

    return (
      <div>
        <div style={{ zIndex: 1 }} className="outerDiv centerer">
          <div
            id="I38:1057;30:46;30:9;24:1"
            style={{
              marginLeft: 0,
              marginRight: 0,
              flexGrow: 1,
              marginTop: 14,
              marginBottom: 14,
              color: active ? white : purple,
              fontSize: 24,
              fontWeight: 400,
              fontFamily: "Muli",
              textAlign: "CENTER",
              fontStyle: "normal",
              lineHeight: "125%",
              letterSpacing: "0px",
            }}
            className="innerDiv"
          >
            <div>
              <span style={{}} key="end">
                {this.props.name}
              </span>
            </div>
          </div>
        </div>
        {barBottom}
      </div>
    );
  }
}
