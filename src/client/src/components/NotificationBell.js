import React from "react";
import {connect} from "react-redux";
import OverlayTrigger from "react-bootstrap/OverlayTrigger";
import Popover from "react-bootstrap/Popover";
import bellImg from "../Essentials Icon Pack/inv_bell.png";
import Image from "react-bootstrap/Image";
import Notification from "./Notification";

class NotificationBell extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
        <OverlayTrigger
            trigger="click"
            key={"bottom"}
            placement={"bottom"}
            overlay={
              <Popover>
                <Popover.Title as="h3">{"Payments"}</Popover.Title>
                <Popover.Content>
                  <div
                      className="outerDiv"
                      style={{
                        height: "100%",
                        width: "100%",
                      }}
                  >
                    <div className="innerDiv">
                      {this.props.paymentNotifications.map(
                          (notification, index) => {
                            return (
                                <Notification
                                    notification={notification}
                                    index={index}
                                />
                            );
                          }
                      )}
                    </div>
                  </div>
                </Popover.Content>
                <Popover.Title as="h1">{"Friends"}</Popover.Title>
                <Popover.Content>
                  <div
                      className="outerDiv"
                      style={{
                        height: "100%",
                        width: "100%",
                      }}
                  >
                    <div className="innerDiv">
                      {this.props.friendNotifications.map(
                          (notification, index) => {
                            return (
                                <Notification
                                    notification={notification}
                                    index={index}
                                />
                            );
                          }
                      )}
                    </div>
                  </div>
                </Popover.Content>
              </Popover>
            }
        >
          <Image src={bellImg} fluid onClick={this.showNotifications}/>
        </OverlayTrigger>
    );
  }
}

function mapStateToProps(state) {
  return {
    friendNotifications: state.friendNotifications,
    paymentNotifications: state.paymentNotifications,
  };
}

export default connect(mapStateToProps)(NotificationBell);
