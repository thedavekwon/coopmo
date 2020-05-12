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
        this.state = {
            friendNotifications: [...this.props.friendNotifications],
            paymentNotifications: [...this.props.paymentNotifications],
        }
    }

    updateNotificationList = () => {
        this.setState((state) => ({
            friendNotifications: [...this.props.friendNotifications],
            paymentNotifications: [...this.props.paymentNotifications],
        }))
    }

    render() {
        let friendNotifications;
        let paymentNotifications;
        if (this.props.friendNotifications.length === 0) {
            friendNotifications = (
                <div className="textStyle">
                    No New Friend Notifications
                </div>
            )
        } else {
            friendNotifications = this.state.friendNotifications.map(
                (notification, index) => {
                    return (
                        <Notification
                            notification={notification}
                            index={index}
                        />
                    );
                }
            )
        }
        if (this.props.paymentNotifications.length === 0) {
            paymentNotifications = (
                <div className="textStyle">
                    No New Payment Notifications
                </div>
            )
        } else {
            paymentNotifications = this.state.paymentNotifications.map(
                (notification, index) => {
                    return (
                        <Notification
                            notification={notification}
                            index={index}
                        />
                    );
                }
            )
        }
        return (
            <OverlayTrigger
                trigger="click"
                key={"bottom"}
                placement={"bottom"}
                onHide={this.updateNotificationList}
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
                        {paymentNotifications}
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
                        {friendNotifications}
                    </div>
                  </div>
                </Popover.Content>
              </Popover>
            }
        >
                <Image src={bellImg} fluid onClick={this.updateNotificationList}/>
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
