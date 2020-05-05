import React from "react";

export default class CInputwithanIcon extends React.Component {
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
                                    >
                                        <div></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div style={{ zIndex: 1 }} className="outerDiv centerer">
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
              marginLeft: "7%",
              marginRight: "3%",
              flexGrow: 1,
              height: "33%",
              top: "50%",
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
            <div>
              <span style={{}} key="end">
                <input
                  type="text"
                  id={this.props.name}
                  name={this.props.name}
                  style={{ width: "100%" }}
                  onChange={this.handleChange}
                ></input>
              </span>
            </div>
          </div>
          <div
            style={{
              zIndex: 2,
            }}
            className="outerDiv centerer"
          >
            <div
              id="38:1035"
              style={{
                width: 16,
                marginLeft: "5%",
                height: 16,
                top: "54%",
              }}
              className="innerDiv"
            >
              <div
                className="vector"
                dangerouslySetInnerHTML={{
                  __html: `<svg preserveAspectRatio="none" width="16" height="16" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg">
<path fill-rule="evenodd" clip-rule="evenodd" d="M9 7H7C6.44772 7 6 6.55228 6 6C6 5.44772 6.44772 5 7 5H11C11.5523 5 12 4.55228 12 4C12 3.44772 11.5523 3 11 3H9V2C9 1.44772 8.55228 1 8 1C7.44772 1 7 1.44772 7 2V3C5.34315 3 4 4.34315 4 6C4 7.65685 5.34315 9 7 9H9C9.55228 9 10 9.44771 10 10C10 10.5523 9.55228 11 9 11H5C4.44772 11 4 11.4477 4 12C4 12.5523 4.44772 13 5 13H7V14C7 14.5523 7.44772 15 8 15C8.55228 15 9 14.5523 9 14V13C10.6569 13 12 11.6569 12 10C12 8.34315 10.6569 7 9 7Z" fill="#FEC703"/>
</svg>
`,
                }}
              />
            </div>
          </div>
        </div>
      </div>
    );
  }
}
