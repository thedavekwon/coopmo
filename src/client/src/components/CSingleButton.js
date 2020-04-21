import React, { PureComponent } from "react";

export default class CSingleButton extends PureComponent {
  constructor(props) {
    super(props);
    this.handleClick = this.handleClick.bind(this);
  }

  handleClick(event) {
    this.props.onSub();
  }

  render() {
    return (
      <div className="master" style={{ backgroundColor: "rgba(0, 0, 0, 0)" }}>
        <div>
          <div style={{ alignItems: "center" }} className="outerDiv centerer">
            <div
              id="I35:320;35:315"
              style={{
                width: "29.41176470588235%",
                marginLeft: "65.88235294117646%",
                height: 56,
                marginTop: 0,
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <div>
                <div style={{}} className="outerDiv centerer">
                  <div
                    id="I35:320;35:316"
                    style={{
                      width: "100%",
                      marginLeft: "0%",
                      height: "100%",
                      top: "0%",
                      backgroundColor: "rgba(12, 200, 99, 1)",
                      borderRadius: "8px 8px 8px 8px",
                    }}
                    className="innerDiv"
                    onClick={this.handleClick}
                  >
                    <div></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div
            style={{ zIndex: 1, alignItems: "center" }}
            className="outerDiv centerer"
          >
            <div
              id="I35:320;35:317"
              style={{
                width: "22.352941176470587%",
                marginLeft: "72.94117647058823%",
                height: 19,
                color: "rgba(255, 255, 255, 1)",
                fontSize: 16,
                fontWeight: 600,
                fontFamily: "Muli",
                textAlign: "CENTER",
                fontStyle: "normal",
                lineHeight: "125%",
                letterSpacing: "0px",
              }}
              className="innerDiv"
              onClick={this.handleClick}
            >
              <div>
                <span style={{}} key="end">
                  {this.props.text}
                </span>
              </div>
            </div>
          </div>
          <div
            style={{
              zIndex: 2,
              justifyContent: "center",
              alignItems: "center",
            }}
            className="outerDiv centerer"
          >
            <div
              id="I35:320;35:318"
              style={{ width: 16, marginLeft: "45%", height: 16, marginTop: 0 }}
              className="innerDiv"
              onClick={this.handleClick}
            >
              <div
                className="vector"
                dangerouslySetInnerHTML={{
                  __html: `<svg preserveAspectRatio="none" width="16" height="16" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg">
<path fill-rule="evenodd" clip-rule="evenodd" d="M9.73243 5.3594L7.16603 8.4375L6.26763 7.36C6.03898 7.08576 5.68139 6.95434 5.32957 7.01524C4.97775 7.07613 4.68514 7.3201 4.56197 7.65524C4.4388 7.99037 4.50378 8.36576 4.73243 8.64L6.39843 10.64C6.58799 10.8682 6.86917 11.0002 7.16579 11.0004C7.46242 11.0006 7.74378 10.8689 7.93363 10.641L11.2676 6.641C11.5021 6.36738 11.5712 5.98878 11.4486 5.64996C11.3259 5.31114 11.0305 5.0645 10.6752 5.00435C10.3199 4.9442 9.95977 5.07983 9.73243 5.3594Z" fill="white"/>
<path fill-rule="evenodd" clip-rule="evenodd" d="M0 8C0 3.58172 3.58172 0 8 0C12.4163 0.0047399 15.9953 3.58369 16 8C16 12.4183 12.4183 16 8 16C3.58172 16 0 12.4183 0 8ZM2 8C2 11.3137 4.68629 14 8 14C11.3122 13.9964 13.9964 11.3122 14 8C14 4.68629 11.3137 2 8 2C4.68629 2 2 4.68629 2 8Z" fill="white"/>
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
