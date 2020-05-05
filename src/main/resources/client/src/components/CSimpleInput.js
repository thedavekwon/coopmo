import React from "react";

export default class CSimpleInput extends React.Component {
    constructor(props) {
        super(props);

        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        this.props.onInput(this.props.valKey, event.target.value);
    }

    render() {
        return (
            <div className="master" style={{backgroundColor: "rgba(0, 0, 0, 0)"}}>
                <div>
                    <div style={{zIndex: 1}} className="outerDiv centerer">
                        <div
                            id="I35:280;35:276"
                            style={{
                                marginLeft: "3%",
                                width: "94%",
                                flexGrow: 1,
                                height: "40%",
                                top: "40%",
                                backgroundColor: "rgba(0, 0, 0, 0)",
                            }}
                            className="innerDiv"
                        >
                            <div>
                                <div style={{}} className="outerDiv centerer">
                                    <div
                                        id="I35:280;35:277"
                                        style={{
                                            width: "100%",
                                            marginLeft: "0%",
                                            height: "100%",
                                            top: "0%",
                                            border: "1px solid rgba(38, 38, 38, 1)",
                                            borderRadius: "8px 8px 8px 8px",
                                        }}
                                        className="innerDiv"
                                    ></div>
                </div>
              </div>
            </div>
          </div>
          <div style={{ zIndex: 2 }} className="outerDiv centerer">
            <div
              id="I35:280;35:278"
              style={{
                marginLeft: "3%",
                width: "100%",
                height: 22,
                top: "16%",
                color: "rgba(38, 38, 38, 1)",
                fontSize: 18,
                fontWeight: 700,
                fontFamily: "Muli",
                textAlign: "LEFT",
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
        </div>
        <div style={{ zIndex: 3 }} className="outerDiv centerer">
          <div
            id="I35:280;35:275"
            style={{
                marginLeft: "5%",
                marginRight: "3%",
                flexGrow: 1,
                height: "40%",
                top: "40%",
                width: "100%",
                color: "rgba(38, 38, 38, 1)",
                fontSize: 16,
                fontWeight: 400,
                fontFamily: "Muli",
                textAlign: "LEFT",
                fontStyle: "normal",
                lineHeight: "175%",
                letterSpacing: "0px",
            }}
            className="innerDiv"
          >
              <input
                  type="text"
                  id={this.props.valKey}
                  name={this.props.name}
                  onChange={this.handleChange}
                  style={{width: "100%", height: "100%"}}
              ></input>
          </div>
        </div>
      </div>
    );
  }
}
