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
        if (this.props.feedTab === this.props.name) active = true;
        let purple = "rgba(102, 0, 153, 1)";
        let white = "rgba(255, 255, 255, 1)";
        let barBottom;

        if (!active) {
            barBottom = (
                <div>
                    <div
                        style={{
                            height: "1px",
                            marginTop: "10px",
                            backgroundColor: "rgba(102, 0, 153, 1)",
                        }}
                    >
                    </div>
        </div>
      );
    }

    return (
      <div>
          <div
            style={{
              color: active ? white : purple,
              paddingTop: "10px",
              fontFamily: "Muli",
              textAlign: "CENTER",
              lineHeight: "100%",
            }} className="textStyle"
          >
            {this.props.name}
          </div>
        {barBottom}
      </div>
    );
  }
}
