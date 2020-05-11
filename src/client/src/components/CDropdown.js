import React from "react";

export default class CDropdown extends React.Component {
    constructor(props) {
        super(props);
    }

    handleChange = (event) => {
        this.props.handleChange(event.target.value);
    };

    render() {
        let dropdown;
        let content;

        if (this.props.dropType === "bank") {
            if (this.props.bankAcctList != null) {
                content = this.props.bankAcctList.map((e, key) => {
                    return (
                        <option value={e.id} key={key}>
                            {e.routingNumber}
                        </option>
                    );
                });
            }
            dropdown = (
                <select
                    id="Bank Account"
                    name="Bank Account"
                    onChange={this.handleChange}
                    style={{width: "100%", border: "none", outline: "none"}}
                >
                    {content}
                </select>
            );
        } else if (this.props.dropType === "payment") {
            if (this.props.paymentTypes != null) {
                content = this.props.paymentTypes.map((e, key) => {
                    return (
                        <option value={e.val} key={key}>
                            {e.name}
                        </option>
                    );
                });
            }

            dropdown = (
                <select
                    id="Payment type"
                    name="Payment Type"
                    onChange={this.handleChange}
                    style={{width: "100%", border: "none", outline: "none"}}
                >
                    {content}
                </select>
      );
    }

    return (
      <div className="master" style={{ backgroundColor: "rgba(0, 0, 0, 0)" }}>
        <div>
          <div style={{ zIndex: 4 }} className="outerDiv centerer">
            <div
              id="38:1017"
              style={{
                marginLeft: "5%",
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
                  {dropdown}
                </span>
              </div>
            </div>
          </div>
          <div style={{ zIndex: 2 }} className="outerDiv centerer">
            <div
              id="38:1020"
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
          <div style={{ zIndex: 3 }} className="outerDiv centerer">
            <div
              id="38:1022"
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
      </div>
    );
  }
}
