import React from "react";

export default class CMenuButtonDefault extends React.Component {

    render() {
        let highlight;
        if (this.props.active === this.props.name) {
            highlight = (
                <React.Fragment>
                    <div style={{zIndex: 1}} className="outerDiv centerer">
                        <div
                            id="30:386"
                            style={{
                                width: "33.333333333333336%",
                                marginLeft: "66.66666666666667%",
                                height: "100%",
                                top: "0%",
                                background:
                                    "linear-gradient(-1.5707962626652379rad, rgba(102, 0, 153, 1) 0%, rgba(102, 0, 153, 0.7289661169052124) 8%, rgba(102, 0, 153, 0) 100%)",
                            }}
                            className="innerDiv"
                        >
                            <div></div>
                        </div>
                    </div>
                </React.Fragment>
            );
        }
        return (
            <div className="master" style={{backgroundColor: "rgba(0, 0, 0, 0)"}}>
                <div>
                    <div style={{}} className="outerDiv centerer">
                        <div
                            id="I254:861;30:391;30:378"
                            style={{
                                flexGrow: 1,
                                color: "rgba(0, 0, 0, 1)",
                                fontSize: 24,
                                fontWeight: 400,
                                fontFamily: "Muli",
                                textAlign: "LEFT",
                                fontStyle: "normal",
                                lineHeight: "125%",
                                letterSpacing: "0px",
                            }}
                            className="innerDiv vertCenterAndCut"
            >
              <div>
                <span style={{}} key="end">
                  {this.props.name}
                </span>
              </div>
            </div>
          </div>
          {highlight}
        </div>
      </div>
    );
  }
}
