import React, { PureComponent } from "react";

export default class CDropdown extends PureComponent {
  constructor(props) {
    super(props);
  }
  state = {};

  render() {
    let bankAcct1 = this.props.bankAcct1;
    let bankAcct2 = this.props.bankAcct2;
    let bankAcct3 = this.props.bankAcct3;

    let dropdown = (
      <select
        id="Bank Account"
        name="Bank Account"
        style={{ width: "100%", border: "none", outline: "none" }}
      >
        <option value={bankAcct1}> {bankAcct1}</option>
        <option value={bankAcct2}> {bankAcct2}</option>
        <option value={bankAcct3}> {bankAcct3}</option>
      </select>
    );

    return (
      <div className="master" style={{ backgroundColor: "rgba(0, 0, 0, 0)" }}>
        <div>
          <div
            style={{ alignItems: "center", zIndex: 4 }}
            className="outerDiv centerer"
          >
            <div
              id="38:1017"
              style={{
                marginLeft: 48,
                marginRight: 72,
                flexGrow: 1,
                height: 28,
                marginTop: 24,
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
                  {dropdown}
                </span>
              </div>
            </div>
          </div>
          <div
            style={{
              zIndex: 1,
              justifyContent: "center",
              alignItems: "center",
            }}
            className="outerDiv centerer"
          ></div>
          <div
            style={{ zIndex: 2, alignItems: "center" }}
            className="outerDiv centerer"
          >
            <div
              id="38:1020"
              style={{
                marginLeft: 32,
                marginRight: 32,
                flexGrow: 1,
                height: 48,
                marginTop: 24,
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <div>
                <div style={{}} className="outerDiv centerer">
                  <div
                    id="38:1021"
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
          <div
            style={{ zIndex: 3, alignItems: "center" }}
            className="outerDiv centerer"
          >
            <div
              id="38:1022"
              style={{
                marginLeft: 32,
                width: "100%",
                height: 22,
                marginTop: -58,
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
      </div>
    );
  }
}
