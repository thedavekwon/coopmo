import React, { PureComponent } from "react";
import CSimpleInput from "./CSimpleInput.js";
import CSingleButton from "./CSingleButton.js";

export default class CEditProfileForm extends PureComponent {
  state = {};
  render() {
    return (
      <form>
        <div>
          <div style={{ zIndex: 1 }} className="outerDiv centerer">
            <div
              id="35:300"
              style={{
                width: "47.22222222222222%",
                marginLeft: "37.986111111111114%",
                height: "11.71875%",
                top: "19.53125%",
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <CSimpleInput {...this.props} name="Name" nodeId="35:300" />
            </div>
          </div>
          <div style={{ zIndex: 2 }} className="outerDiv centerer">
            <div
              id="35:280"
              style={{
                width: "47.22222222222222%",
                marginLeft: "37.986111111111114%",
                height: "11.71875%",
                top: "31.25%",
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <CSimpleInput {...this.props} name="Username" nodeId="35:280" />
            </div>
          </div>
          <div style={{ zIndex: 3 }} className="outerDiv centerer">
            <div
              id="35:285"
              style={{
                width: "47.22222222222222%",
                marginLeft: "37.986111111111114%",
                height: "11.71875%",
                top: "42.96875%",
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <CSimpleInput {...this.props} name="Password" nodeId="35:285" />
            </div>
          </div>
          <div style={{ zIndex: 4 }} className="outerDiv centerer">
            <div
              id="35:290"
              style={{
                width: "47.22222222222222%",
                marginLeft: "37.986111111111114%",
                height: "11.71875%",
                top: "54.6875%",
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <CSimpleInput {...this.props} name="Email" nodeId="35:290" />
            </div>
          </div>
          <div style={{ zIndex: 5 }} className="outerDiv centerer">
            <div
              id="35:295"
              style={{
                width: "47.22222222222222%",
                marginLeft: "37.986111111111114%",
                height: "11.71875%",
                top: "66.40625%",
                backgroundColor: "rgba(0, 0, 0, 0)",
              }}
              className="innerDiv"
            >
              <CSimpleInput {...this.props} name="Handle" nodeId="35:295" />
            </div>
          </div>

          <div style={{ zIndex: 7 }} className="outerDiv centerer">
            <div
              id="35:320"
              style={{
                width: "47.22222222222222%",
                marginLeft: "37.986111111111114%",
                height: "11.71875%",
                top: "78.1251%",
                backgroundColor: "rgba(0, 0, 0, 0)",
                overflow: "hidden",
              }}
              className="innerDiv"
            >
              <CSingleButton {...this.props} text="Submit" nodeId="35:320" />
            </div>
          </div>
        </div>
      </form>
    );
  }
}
