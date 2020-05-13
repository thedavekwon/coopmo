import React from "react";
import {connect} from "react-redux";
import OverlayTrigger from "react-bootstrap/OverlayTrigger";
import Popover from "react-bootstrap/Popover";
import Notification from "./Notification";
import bellPurple from "../Essentials Icon Pack/bell_purple.png";
import bellNotifPurple from "../Essentials Icon Pack/bell_notif_purple.png";
import bellWhite from "../Essentials Icon Pack/bell_white.png";
import bellNotifWhite from "../Essentials Icon Pack/bell_notif_white.png";
import {changeNewNotifications} from "../redux/actions";

class NotificationBell extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      friendNotifications: [...this.props.friendNotifications],
      paymentNotifications: [...this.props.paymentNotifications],
      open: false,
    };
  }

  updateNotificationList = () => {
    this.setState((state) => ({
      friendNotifications: [...this.props.friendNotifications],
      paymentNotifications: [...this.props.paymentNotifications],
      open: !this.state.open
    }));
    if (this.state.open)
      this.props.changeNewNotifications(false);
  };

  onClose = () => {
    this.props.changeNewNotifications(false);
  }

  render() {
    let bellImg;
    if (this.props.newNotifications) {
      if (this.props.color === "purple") {
          bellImg = bellNotifPurple;
      } else bellImg = bellNotifWhite;
    } else {
        if (this.props.color === "purple") {
            bellImg = bellPurple;
        } else bellImg = bellWhite;
    }

      let friendNotifications;
      let paymentNotifications;
      let friendContainerHeight = 300;
      let paymentContainerHeight = 300;
      if (this.props.friendNotifications.length === 0) {
          friendNotifications = (
              <div className="textStyle">No New Friend Notifications</div>
          );
          friendContainerHeight = "100%";
      } else {
          friendNotifications = this.state.friendNotifications.map(
              (notification, index) => {
                  return <Notification notification={notification} index={index} key={index}/>;
              }
      );
    }
    if (this.props.paymentNotifications.length === 0) {
        paymentNotifications = (
            <div className="textStyle">No New Payment Notifications</div>
        );
        paymentContainerHeight = "100%";
    } else {
      paymentNotifications = this.state.paymentNotifications.map(
          (notification, index) => {
            return <Notification notification={notification} index={index} key={index}/>;
          }
      );
    }

    return (
        <OverlayTrigger
            trigger="click"
            key={"bottom"}
            placement={"bottom"}
            popoverConfig={{
              height: "250px",
              overflow: "scroll",
            }}
            overlay={
              <Popover onExited={this.onClose}>
                <Popover.Title as="h3">{"Payments"}</Popover.Title>
                <Popover.Content>
                  <div
                      className="outerDiv"
                      style={{
                          height: paymentContainerHeight,
                          width: "100%",

                      }}
                  >
                      <div className="innerDiv notificationContainer">{paymentNotifications}</div>
                  </div>
                </Popover.Content>
                <Popover.Title as="h1">{"Friends"}</Popover.Title>
                <Popover.Content>
                  <div
                      className="outerDiv"
                      style={{
                          height: friendContainerHeight,
                          width: "100%",
                      }}
                  >
                    <div className="innerDiv notificationContainer">{friendNotifications}</div>
                  </div>
                </Popover.Content>
              </Popover>
            }
        >
          <img src={bellImg} alt="notification bell" className="notificationPic" onClick={this.updateNotificationList}/>
        </OverlayTrigger>
    );
  }
}

function mapStateToProps(state) {
  return {
    friendNotifications: state.friendNotifications,
    paymentNotifications: state.paymentNotifications,
    newNotifications: state.newNotifications,
  };
}

export default connect(mapStateToProps, {changeNewNotifications})(NotificationBell);
